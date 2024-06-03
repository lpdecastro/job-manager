package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.SavedJobDto;
import com.lpdecastro.jobmanager.dto.SavedJobRequestDto;
import com.lpdecastro.jobmanager.dto.SavedJobsResponseDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;

public interface SavedJobService {

    SavedJobsResponseDto getSavedJobs();
    SavedJobDto addToSavedJobs(SavedJobRequestDto savedJobRequestDto);
    ResponseDto removeFromSavedJobs(long savedJobId);
}
