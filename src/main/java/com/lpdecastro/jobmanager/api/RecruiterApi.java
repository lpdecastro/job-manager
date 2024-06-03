package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.RecruiterDto;
import com.lpdecastro.jobmanager.dto.RecruiterRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.service.RecruiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/.rest/recruiters")
@RequiredArgsConstructor
public class RecruiterApi {

    private final RecruiterService recruiterService;

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('SCOPE_RECRUITER')")
    public RecruiterDto getCurrentRecruiter() {
        return recruiterService.getCurrentRecruiter();
    }

    @PutMapping("/current")
    @PreAuthorize("hasAuthority('SCOPE_RECRUITER')")
    public RecruiterDto updateCurrentRecruiter(@Valid @RequestBody RecruiterRequestDto recruiterRequestDto) {
        return recruiterService.updateCurrentRecruiter(recruiterRequestDto);
    }

    @PutMapping("/current/upload/photo")
    @PreAuthorize("hasAuthority('SCOPE_RECRUITER')")
    public RecruiterDto uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        return recruiterService.uploadPhoto(photo);
    }

    @DeleteMapping("/{recruiterId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseDto deleteRecruiter(@PathVariable long recruiterId) {
        return recruiterService.deleteRecruiterById(recruiterId);
    }
}
