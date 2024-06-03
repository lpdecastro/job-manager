package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.CandidateDto;
import com.lpdecastro.jobmanager.dto.CandidateRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/.rest/candidates")
@RequiredArgsConstructor
public class CandidateApi {

    private final CandidateService candidateService;

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public CandidateDto getCurrentCandidate() {
        return candidateService.getCurrentCandidate();
    }

    @GetMapping("/{candidateId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public CandidateDto getCandidateById(@PathVariable long candidateId) {
        return candidateService.getCandidateById(candidateId);
    }

    @GetMapping("/{candidateId}/downloads/resume/{resumeFilename}")
    public ResponseEntity<Resource> downloadResume(@PathVariable long candidateId,
                                                   @PathVariable String resumeFilename) {
        Resource fileResource = candidateService.downloadResume(candidateId, resumeFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(fileResource);
    }

    @PutMapping("/current")
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public CandidateDto updateCurrentCandidate(@Valid @RequestBody CandidateRequestDto candidateRequestDto) {
        return candidateService.updateCurrentCandidate(candidateRequestDto);
    }

    @PutMapping("/current/upload/resume")
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public CandidateDto uploadResume(@RequestParam("resume") MultipartFile resume) {
        return candidateService.uploadResume(resume);
    }

    @PutMapping("/current/upload/photo")
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATE')")
    public CandidateDto uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        return candidateService.uploadPhoto(photo);
    }

    @DeleteMapping("/{candidateId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseDto deleteCandidate(@PathVariable long candidateId) {
        return candidateService.deleteCandidateById(candidateId);
    }
}
