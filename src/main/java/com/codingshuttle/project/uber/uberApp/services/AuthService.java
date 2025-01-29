package com.codingshuttle.project.uber.uberApp.services;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.SignUpDto;
import com.codingshuttle.project.uber.uberApp.dto.UserDto;

public interface AuthService {

    String[] login(String email,String password);

    UserDto signUp(SignUpDto signUpDto);

    public DriverDto onboardNewDriver(Long userId, String vehicleId);
}
