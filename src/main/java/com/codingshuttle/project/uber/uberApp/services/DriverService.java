package com.codingshuttle.project.uber.uberApp.services;

import com.codingshuttle.project.uber.uberApp.dto.*;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DriverService {

  RideDto acceptRide(Long rideRequestId);

  RideDto cancelRide(Long rideId);

  RideDto startRide(Long rideId,String otp);

  RideDto endRide(Long rideId);

  RiderDto rateRider(Long rideId,Integer rating);

  DriverDto getMyProfile();

  Page<RideDto> getAllMyRides(PageRequest pageRequest);
  Driver getCurrentDriver();
  Driver updateDriverAvailability(Driver driver, boolean available);

  Driver createNewDriver(Driver driver);
  public  DriverLocDto updateLocation(Long driverId, double latitude, double longitude);
}

//in above methods somewhere we require the driver id so we are not providing here as it will automatically given by spring security context holedr
