package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.AccessTokenResponseDto;
import com.lpdecastro.jobmanager.dto.LoginRequestDto;
import com.lpdecastro.jobmanager.dto.RegisterRequestDto;
import com.lpdecastro.jobmanager.dto.UserDto;
import com.lpdecastro.jobmanager.entity.*;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.CandidateRepository;
import com.lpdecastro.jobmanager.repository.RecruiterRepository;
import com.lpdecastro.jobmanager.repository.RoleRepository;
import com.lpdecastro.jobmanager.repository.UserRepository;
import com.lpdecastro.jobmanager.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.lpdecastro.jobmanager.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    @Override
    public UserDto register(RegisterRequestDto registerRequestDto) {
        validateRegisterRequest(registerRequestDto.getUsername(), registerRequestDto.getEmail());

        Role role = roleRepository.findByName(registerRequestDto.getRole())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ROLE_NOT_FOUND));

        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        if (RoleName.CANDIDATE == registerRequestDto.getRole()) {
            candidateRepository.save(new Candidate(savedUser));
        }
        if (RoleName.RECRUITER == registerRequestDto.getRole()) {
            recruiterRepository.save(new Recruiter(savedUser));
        }

        return convertToDto(savedUser);
    }

    @Override
    public AccessTokenResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);
        long expiresIn = jwtTokenProvider.getExpiresIn();

        return new AccessTokenResponseDto(token, "Bearer", expiresIn);
    }

    private void validateRegisterRequest(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.CONFLICT, USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(email)) {
            throw new AppException(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS);
        }
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
