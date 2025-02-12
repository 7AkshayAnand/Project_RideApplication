package com.codingshuttle.project.uber.uberApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import org.locationtech.jts.geom.*;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
        @Index(name = "idx_driver_vehicle_id", columnList = "vehicleId")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    //    a unique conatraints is created  for user_id column in this driver instance in database

    private Double rating;

    private Boolean available;
//    if driver is avaibale then only we can assign it a ride

    private String vehicleId;

    @Column(columnDefinition = "Geometry(Point,4326)")

//    4326 refers that we will be dealing with earth geometry
   private Point currentLocation;

}
