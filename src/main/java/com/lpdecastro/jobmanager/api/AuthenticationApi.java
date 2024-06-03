package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.AccessTokenResponseDto;
import com.lpdecastro.jobmanager.dto.LoginRequestDto;
import com.lpdecastro.jobmanager.dto.RegisterRequestDto;
import com.lpdecastro.jobmanager.dto.UserDto;
import com.lpdecastro.jobmanager.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.rest/auth")
@RequiredArgsConstructor
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return authenticationService.register(registerRequestDto);
    }

    @PostMapping("/login")
    public AccessTokenResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authenticationService.login(loginRequestDto);
    }
}
