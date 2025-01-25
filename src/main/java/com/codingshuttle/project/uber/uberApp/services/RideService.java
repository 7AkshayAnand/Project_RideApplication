package com.codingshuttle.project.uber.uberApp.services;

import com.codingshuttle.project.uber.uberApp.dto.RideRequestDto;
import com.codingshuttle.project.uber.uberApp.entities.*;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import org.springframework.data.domain.*;

public interface RideService {
    Ride getRideById(Long rideId);



    Ride createNewRide(RideRequest rideRequest,Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride>  getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride>  getAllRidesOfDriver(Driver driver,PageRequest pageRequest);



}
