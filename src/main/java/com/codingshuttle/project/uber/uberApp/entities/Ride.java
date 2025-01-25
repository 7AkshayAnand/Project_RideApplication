package com.codingshuttle.project.uber.uberApp.entities;

import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.project.uber.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickupLocation;
//    the pickup point is selected by front end like by using google map

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;
//    it is the time when driver accepts the riderequest

    @ManyToOne(fetch=FetchType.LAZY)
    private Rider rider;
//    as one rider can have mulitple ride so many ride is mapped to a single rider in this case

    @ManyToOne(fetch=FetchType.EAGER)
    private Driver driver;
//    also one driver can have mulitple ride

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;
    private String otp;

    private Double fare;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;


//    startedAt is the time when driver started the ride and endedAt when driver ended the ride
}
