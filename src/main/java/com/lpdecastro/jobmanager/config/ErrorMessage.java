package com.lpdecastro.jobmanager.config;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    ROLE_NOT_FOUND("error.role.role-not-found"),
    USERNAME_ALREADY_EXISTS("error.user.username-already-exists"),
    EMAIL_ALREADY_EXISTS("error.user.email-already-exists"),
    CANDIDATE_NOT_FOUND("error.candidate.candidate-not-found"),
    ONLY_PDF_FILES_ARE_ALLOWED("error.candidate.only-pdf-files-allowed"),
    ONLY_JPEG_OR_PNG_IS_ALLOWED("error.candidate.only-jpeg-or-png-is-allowed"),
    FAILED_TO_UPLOAD_PDF("error.candidate.failed-to-upload-pdf"),
    FAILED_TO_UPLOAD_IMAGE("error.candidate.failed-to-upload-image"),
    FAILED_TO_DOWNLOAD_PDF("error.candidate.failed-to-download-pdf"),
    RECRUITER_NOT_FOUND("error.recruiter.recruiter-not-found"),
    JOB_LISTING_NOT_FOUND("error.job.listing.job-not-found"),
    JOB_COMPANY_NOT_FOUND("error.job.company.job-not-found"),
    JOB_LOCATION_NOT_FOUND("error.job.location.job-not-found"),
    JOB_APPLICATION_NOT_FOUND("error.job-application.job-application-not-found"),
    FAILED_TO_UPLOAD_COVER_LETTER("error.job-application.failed-to-upload-cover-letter"),
    JOB_APPLICATION_ALREADY_EXISTS("error.job-application.job-application-already-exists"),
    JOB_ALREADY_SAVED("error.job-application.job-already-saved"),
    SAVED_JOB_NOT_FOUND("error.job.saved.job-not-found"),
    COMPANY_NOT_FOUND("error.company.company-not-found"),;

    private final String code;

    ErrorMessage(String code) {
        this.code = code;
    }
}
