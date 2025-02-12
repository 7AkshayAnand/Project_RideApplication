package com.codingshuttle.project.uber.uberApp.controllers;

import com.codingshuttle.project.uber.uberApp.dto.*;

import com.codingshuttle.project.uber.uberApp.entities.DriverLocation;
import com.codingshuttle.project.uber.uberApp.repositories.DriverLocationRepository;
import com.codingshuttle.project.uber.uberApp.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/drivers")
@Secured("ROLE_DRIVER")
//below api can be called by drivers only
public class DriverController {
    @Autowired
    public DriverController( DriverService driverService, DriverLocationRepository locationRepository) {

        this.driverService = driverService;
        this.locationRepository = locationRepository;
    }


    private final DriverService driverService;


    private final DriverLocationRepository locationRepository;



    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                              @RequestBody RideStartDto rideStartDto) {
        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDto.getOtp()));
    }
    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(driverService.endRide(rideId));
    }


    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(driverService.rateRider(ratingDto.getRideId(), ratingDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile() {
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize,
                Sort.by(Sort.Direction.DESC, "createdTime", "id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }


    @GetMapping("/getLocation")
    public Optional<DriverLocation> getDriverLocation(@RequestParam Long driverId) {
        return locationRepository.findByDriverId(driverId);
    }
//    @PostMapping("/rateRider/{rideId}/{rating}")
//    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId, @PathVariable Integer rating) {
//        return ResponseEntity.ok(driverService.rateRider(rideId, rating));
//    }



    @PostMapping("{did}/latitude/{l1}/longitude/{l2}")
    public DriverLocDto updatePosition(@PathVariable Long did, @PathVariable Double l1, @PathVariable Double l2){

      System.out.println("location updated with l1"+l1 +"  and the"  +" and "+l2);
        System.out.println("i am called buddy with updated location details ");
       DriverLocDto d1= driverService.updateLocation(did, l1, l2);
      return d1;



    }


//    below is not working thats why i commented it.
//    @PostMapping("/uploc")
//    public String updateLocation(@RequestParam Long driverId,
//                                 @RequestParam double latitude,
//                                 @RequestParam double longitude) {
//        System.out.println("i am called buddy with updated location details ");
//        driverService.updateLocation(driverId, latitude, longitude);
//        return "Location updated!";
    }
}
