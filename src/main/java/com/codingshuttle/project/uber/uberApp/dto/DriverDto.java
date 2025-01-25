package com.codingshuttle.project.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private  Long id;

    private UserDto userDto;
    private Double rating;
    private Boolean available;
    private String vehicleId;

    public DriverDto(UserDto userDto, Double rating) {
        this.userDto = userDto;
        this.rating = rating;
    }
}

