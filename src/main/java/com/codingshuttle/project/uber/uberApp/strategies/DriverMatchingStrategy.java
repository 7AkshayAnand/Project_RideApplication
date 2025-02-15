package com.codingshuttle.project.uber.uberApp.strategies;

import com.codingshuttle.project.uber.uberApp.dto.RideRequestDto;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
     List<Driver> findMatchingDriver(RideRequest rideRequest);
}
