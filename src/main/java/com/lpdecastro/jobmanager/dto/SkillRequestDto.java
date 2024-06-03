package com.lpdecastro.jobmanager.dto;

import com.lpdecastro.jobmanager.entity.SkillLevel;
import lombok.Data;

@Data
public class SkillRequestDto {

    private String skillName;
    private int yearsOfExperience;
    private SkillLevel skillLevel;
}
