package com.codingshuttle.project.uber.uberApp.controllers;

import com.codingshuttle.project.uber.uberApp.dto.DriverDto;
import com.codingshuttle.project.uber.uberApp.dto.OnboardDriverDto;
import com.codingshuttle.project.uber.uberApp.dto.SignUpDto;
import com.codingshuttle.project.uber.uberApp.dto.UserDto;
import com.codingshuttle.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    UserDto signUp(@RequestBody SignUpDto signupDto) {
        return authService.signUp(signupDto);
    }


    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnboardDriverDto onboardDriverDto) {
        return new ResponseEntity<>(authService.onboardNewDriver(userId,
                onboardDriverDto.getVehicleId()), HttpStatus.CREATED);
    }

}
