package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.dto.SavedJobDto;
import com.lpdecastro.jobmanager.dto.SavedJobRequestDto;
import com.lpdecastro.jobmanager.dto.SavedJobsResponseDto;
import com.lpdecastro.jobmanager.service.SavedJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/.rest/saved-jobs")
@RequiredArgsConstructor
public class SavedJobApi {

    private final SavedJobService savedJobService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public SavedJobsResponseDto getSavedJobs() {
        return savedJobService.getSavedJobs();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public SavedJobDto addToSavedJobs(@Valid @RequestBody SavedJobRequestDto savedJobRequestDto) {
        return savedJobService.addToSavedJobs(savedJobRequestDto);
    }

    @DeleteMapping("/{savedJobId}")
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public ResponseDto removeFromSavedJobs(@PathVariable long savedJobId) {
        return savedJobService.removeFromSavedJobs(savedJobId);
    }
}
