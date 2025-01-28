package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.*;
import com.codingshuttle.project.uber.uberApp.entities.*;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.RideRequestRepository;
import com.codingshuttle.project.uber.uberApp.repositories.RiderRepository;
import com.codingshuttle.project.uber.uberApp.services.DriverService;
import com.codingshuttle.project.uber.uberApp.services.RatingService;
import com.codingshuttle.project.uber.uberApp.services.RideService;
import com.codingshuttle.project.uber.uberApp.services.RiderService;
import com.codingshuttle.project.uber.uberApp.configs.*;
import com.codingshuttle.project.uber.uberApp.strategies.DriverMatchingStrategy;
import com.codingshuttle.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import com.codingshuttle.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;

   private final RideStrategyManager rideStrategyManager;

    private final RideRequestRepository rideRequestRepository;

    private final RiderRepository riderRepository;

     private  final RideService rideService;
     private final RatingService ratingService;

     private DriverService driverService;



    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        Rider rider=getCurrentRider();

        RideRequest rideRequest=modelMapper.map(rideRequestDto,RideRequest.class);

        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare=rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);

        rideRequest.setFare(fare);

        RideRequest savedRideRequest= rideRequestRepository.save(rideRequest);


        List<Driver> drivers =  rideStrategyManager. driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);




      System.out.println("detailS about savedRiderrequest "+savedRideRequest.getRideRequestStatus());

        RideRequestDto rideRequestDto1=modelMapper.map(savedRideRequest,RideRequestDto.class);
//        System.out.println("details after converison  : "+rideRequestDto1.getRider().getUserDto().getName());
        System.out.println("detailS about savedRiderrequest after map "+savedRideRequest.getRideRequestStatus());
        return rideRequestDto1;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException(("Rider does not own this ride with id: "+rideId));
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

//        return modelMapper.map(savedRide, RideDto.class);
        RideDto rideDto1=mappingImpl(ride);
        return rideDto1;

    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);

    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
//                ride -> modelMapper.map(ride, RideDto.class)
                ride -> mappingImpl(ride)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider=Rider.builder().user(user).rating(0.0).build();


        return riderRepository.save(rider);
    }

    @Override
    public  Rider getCurrentRider(){
        //TODO implement spring secutiry
        return riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException("rider not found with id: "+1));
    }

    public RideDto mappingImpl(Ride ride){

        RideDto rideDto1= modelMapper.map(ride, RideDto.class);
//some fields are not set by  modelmapper we are setting it below


        User user=ride.getDriver().getUser();
        UserDto user1= modelMapper.map(user,UserDto.class);
        Double rating1=ride.getDriver().getRating();

        DriverDto driverDto=new DriverDto(user1,rating1);

        rideDto1.setDriverDto(driverDto);
        return rideDto1;
    }
}
