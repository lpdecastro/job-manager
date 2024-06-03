package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.CandidateDto;
import com.lpdecastro.jobmanager.dto.CandidateRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.entity.Candidate;
import com.lpdecastro.jobmanager.entity.User;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.CandidateRepository;
import com.lpdecastro.jobmanager.repository.UserRepository;
import com.lpdecastro.jobmanager.util.FileDownloadUtil;
import com.lpdecastro.jobmanager.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.lpdecastro.jobmanager.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    @Value("${app.data.candidates-directory}")
    private String candidatesDirectory;

    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;

    @Override
    public CandidateDto getCurrentCandidate() {
        return convertToDto(getCurrentCandidateEntity());
    }

    @Override
    public CandidateDto getCandidateById(long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CANDIDATE_NOT_FOUND));
        return convertToDto(candidate);
    }

    @Transactional
    @Override
    public CandidateDto updateCurrentCandidate(CandidateRequestDto candidateRequestDto) {
        Candidate currentCandidate = getCurrentCandidateEntity();
        User currentUser = currentCandidate.getUser();

        validateUpdateRequest(currentUser.getUserId(), candidateRequestDto.getUsername(),
                candidateRequestDto.getEmail());

        currentUser.setUsername(candidateRequestDto.getUsername());
        currentUser.setEmail(candidateRequestDto.getEmail());
        currentUser.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(currentUser);

        Candidate candidate = convertToEntity(candidateRequestDto);
        candidate.setCandidateId(currentCandidate.getCandidateId());
        candidate.setResume(currentCandidate.getResume());
        candidate.setPhoto(currentCandidate.getPhoto());
        candidate.setUser(savedUser);

        return convertToDto(candidateRepository.save(candidate));
    }

    @Override
    public CandidateDto uploadResume(MultipartFile resume) {
        Candidate currentCandidate = getCurrentCandidateEntity();

        String uploadDir = candidatesDirectory + currentCandidate.getCandidateId();
        String resumeFilename = StringUtils.cleanPath(Objects.requireNonNull(resume.getOriginalFilename()));
        if (!isSupportedPdfFile(resumeFilename)) {
            throw new AppException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ONLY_PDF_FILES_ARE_ALLOWED);
        }

        try {
            FileUploadUtil.saveFile(uploadDir, resumeFilename, resume);
        } catch (IOException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, FAILED_TO_UPLOAD_PDF);
        }

        currentCandidate.setResume(resumeFilename);

        return convertToDto(candidateRepository.save(currentCandidate));
    }

    @Override
    public CandidateDto uploadPhoto(MultipartFile photo) {
        Candidate currentCandidate = getCurrentCandidateEntity();

        String uploadDir = candidatesDirectory + currentCandidate.getCandidateId();
        String photoFilename = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
        if (!isSupportedImageFile(photoFilename)) {
            throw new AppException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ONLY_JPEG_OR_PNG_IS_ALLOWED);
        }

        try {
            FileUploadUtil.saveFile(uploadDir, photoFilename, photo);
        } catch (IOException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, FAILED_TO_UPLOAD_IMAGE);
        }

        currentCandidate.setPhoto(photoFilename);

        return convertToDto(candidateRepository.save(currentCandidate));
    }

    @Override
    public Resource downloadResume(long candidateId, String resumeFilename) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CANDIDATE_NOT_FOUND));

        String downloadDir = candidatesDirectory + candidate.getCandidateId();
        try {
            return FileDownloadUtil.getFileAsResource(downloadDir, resumeFilename);
        } catch (IOException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, FAILED_TO_DOWNLOAD_PDF);
        }
    }

    @Override
    public ResponseDto deleteCandidateById(long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CANDIDATE_NOT_FOUND));
        candidateRepository.delete(candidate);
        return new ResponseDto(true);
    }

    private Candidate getCurrentCandidateEntity() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return candidateRepository.findByUser_Username(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CANDIDATE_NOT_FOUND));
    }

    private void validateUpdateRequest(long userId, String username, String email) {
        if (userRepository.existsByUsernameAndUserIdNot(username, userId)) {
            throw new AppException(HttpStatus.CONFLICT, USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmailAndUserIdNot(email, userId)) {
            throw new AppException(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS);
        }
    }

    private boolean isSupportedPdfFile(String filename) {
        return filename.endsWith(".pdf");
    }

    private boolean isSupportedImageFile(String filename) {
        return filename.endsWith(".png") || filename.endsWith(".jpg");
    }

    private CandidateDto convertToDto(Candidate candidate) {
        return modelMapper.map(candidate, CandidateDto.class);
    }

    private Candidate convertToEntity(CandidateRequestDto candidateRequestDto) {
        return modelMapper.map(candidateRequestDto, Candidate.class);
    }
}
