package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.*;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Ride;
import com.codingshuttle.project.uber.uberApp.entities.RideRequest;
import com.codingshuttle.project.uber.uberApp.entities.User;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.DriverRepository;
import com.codingshuttle.project.uber.uberApp.services.*;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {


    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        System.out.println("accept ride  "+rideRequest.getRideRequestStatus());

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("RideRequest cannot be accepted, status is "+ rideRequest.getRideRequestStatus());
        }
//now we have already send the notification to the all the list of driver in riderserviceimpl in request ride method
//        whoever accepts this ride calls this method
        Driver currentDriver = getCurrentDriver();


        if(!currentDriver.getAvailable()) {
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }

        currentDriver.setAvailable(false);
        Driver savedDriver = driverRepository.save(currentDriver);
//After proper validation we are creating a new ride

        Ride ride = rideService.createNewRide(rideRequest, savedDriver);

        System.out.println("details are  : "+ride.getDriver().getUser().getName());
        RideDto rideDto= modelMapper.map(ride, RideDto.class);
//some fields are not set by  modelmapper we are setting it below
        Driver driver=ride.getDriver();
        User user=ride.getDriver().getUser();
       UserDto user1= modelMapper.map(user,UserDto.class);
       Double rating1=ride.getDriver().getRating();

       DriverDto driverDto=new DriverDto(user1,rating1);

       rideDto.setDriverDto(driverDto);


        return rideDto;


    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot cancel a ride as he has not accepted it earlier");
        }
//drivers can only cancel ride only if it is confirmed
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);
        RideDto rideDto1=mappingImpl(ride);
        return rideDto1;
    }




    @Override
    public RideDto startRide(Long rideId,String  otp) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started, status: "+ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())) {
            throw new RuntimeException("Otp is not valid, otp: "+otp);
        }
//
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);
        RideDto rideDto1=mappingImpl(ride);
        return rideDto1;

    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot end a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride status is not ONGOING hence cannot be ended, status: "+ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);

//        return modelMapper.map(savedRide, RideDto.class);
        RideDto rideDto1=mappingImpl(ride);
        return rideDto1;
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateRider(ride, rating);
    }

    @Override
    public DriverDto getMyProfile() {

        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);

    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(
                ride -> mappingImpl(ride)
//                here may be model mapper not work so implement by yourself
        );
    }

    @Override
    public Driver getCurrentDriver() {
//        return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Driver not found with " +
//                "id "+2));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with " +
                        "id "+user.getId()));
    }
    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
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

    private final GeometryFactory geometryFactory = new GeometryFactory();
    @Transactional
    public DriverLocDto updateLocation(Long driverId, double latitude, double longitude) {
        Point point = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude));

        // Find the driver and update their location
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setCurrentLocation(point);

        Point p1=driver.getCurrentLocation();
       PointDto pt1= modelMapper.map(p1, PointDto.class);

        Driver driver1=driverRepository.save(driver);
       System.out.println("updated loc of driver is "+driver1.getCurrentLocation());
       DriverLocDto driverLocDto=new DriverLocDto();
       driverLocDto.setCurrentLocation(pt1);

        return driverLocDto;

    }
}
