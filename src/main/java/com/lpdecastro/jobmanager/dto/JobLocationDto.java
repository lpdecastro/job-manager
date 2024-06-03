package com.lpdecastro.jobmanager.dto;

import lombok.Data;

@Data
public class JobLocationDto {

    private Long jobLocationId;
    private String city;
    private String province;
    private String zipCode;
    private String country;
}
