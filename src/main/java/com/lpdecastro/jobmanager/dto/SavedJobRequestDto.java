package com.lpdecastro.jobmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SavedJobRequestDto {

    @NotNull
    private Long jobListingId;
}
