package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.CandidateDto;
import com.lpdecastro.jobmanager.dto.CandidateRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {

    CandidateDto getCurrentCandidate();
    CandidateDto getCandidateById(long candidateId);
    CandidateDto updateCurrentCandidate(CandidateRequestDto candidateRequestDto);
    CandidateDto uploadResume(MultipartFile resume);
    CandidateDto uploadPhoto(MultipartFile photo);
    Resource downloadResume(long candidateId, String resumeFilename);
    ResponseDto deleteCandidateById(long candidateId);
}
