package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.AccessTokenResponseDto;
import com.lpdecastro.jobmanager.dto.LoginRequestDto;
import com.lpdecastro.jobmanager.dto.RegisterRequestDto;
import com.lpdecastro.jobmanager.dto.UserDto;

public interface AuthenticationService {

    UserDto register(RegisterRequestDto registerRequestDto);
    AccessTokenResponseDto login(LoginRequestDto loginRequestDto);
}
