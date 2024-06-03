package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.RecruiterDto;
import com.lpdecastro.jobmanager.dto.RecruiterRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.entity.Recruiter;
import com.lpdecastro.jobmanager.entity.User;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.RecruiterRepository;
import com.lpdecastro.jobmanager.repository.UserRepository;
import com.lpdecastro.jobmanager.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static com.lpdecastro.jobmanager.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    @Value("${app.data.recruiters-directory}")
    private String recruiterUploadDir;

    private final UserRepository userRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper modelMapper;

    @Override
    public RecruiterDto getCurrentRecruiter() {
        return convertToDto(getCurrentRecruiterEntity());
    }

    @Override
    public RecruiterDto updateCurrentRecruiter(RecruiterRequestDto recruiterRequestDto) {
        Recruiter currentRecruiter = getCurrentRecruiterEntity();
        User currentUser = currentRecruiter.getUser();

        validateUpdateRequest(currentUser.getUserId(), recruiterRequestDto.getUsername(),
                recruiterRequestDto.getEmail());

        currentUser.setUsername(recruiterRequestDto.getUsername());
        currentUser.setEmail(recruiterRequestDto.getEmail());
        User savedUser = userRepository.save(currentUser);

        Recruiter recruiter = convertToEntity(recruiterRequestDto);
        recruiter.setRecruiterId(currentRecruiter.getRecruiterId());
        recruiter.setPhoto(currentRecruiter.getPhoto());
        recruiter.setUser(savedUser);

        return convertToDto(recruiterRepository.save(recruiter));
    }

    @Override
    public RecruiterDto uploadPhoto(MultipartFile photo) {
        Recruiter currentRecruiter = getCurrentRecruiterEntity();

        String uploadDir = recruiterUploadDir + currentRecruiter.getRecruiterId();
        String photoFilename = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));

        try {
            FileUploadUtil.saveFile(uploadDir, photoFilename, photo);
        } catch (IOException e) {
            throw new AppException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ONLY_JPEG_OR_PNG_IS_ALLOWED);
        }

        currentRecruiter.setPhoto(photoFilename);

        return convertToDto(recruiterRepository.save(currentRecruiter));
    }

    @Override
    public ResponseDto deleteRecruiterById(long recruiterId) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, RECRUITER_NOT_FOUND));
        recruiterRepository.delete(recruiter);
        return new ResponseDto(true);
    }

    private Recruiter getCurrentRecruiterEntity() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return recruiterRepository.findByUser_Username(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, RECRUITER_NOT_FOUND));
    }

    private void validateUpdateRequest(long userId, String username, String email) {
        if (userRepository.existsByUsernameAndUserIdNot(username, userId)) {
            throw new AppException(HttpStatus.CONFLICT, USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmailAndUserIdNot(email, userId)) {
            throw new AppException(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS);
        }
    }

    private RecruiterDto convertToDto(Recruiter recruiter) {
        return modelMapper.map(recruiter, RecruiterDto.class);
    }

    private Recruiter convertToEntity(RecruiterRequestDto recruiterRequestDto) {
        return modelMapper.map(recruiterRequestDto, Recruiter.class);
    }
}
