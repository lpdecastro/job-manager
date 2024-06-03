package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.RecruiterDto;
import com.lpdecastro.jobmanager.dto.RecruiterRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface RecruiterService {

    RecruiterDto getCurrentRecruiter();
    RecruiterDto updateCurrentRecruiter(RecruiterRequestDto recruiterRequestDto);
    RecruiterDto uploadPhoto(MultipartFile photo);
    ResponseDto deleteRecruiterById(long recruiterId);
}
