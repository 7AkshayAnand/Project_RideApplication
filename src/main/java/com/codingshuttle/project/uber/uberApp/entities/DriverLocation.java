package com.codingshuttle.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "driver_location")
public class DriverLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @Column(columnDefinition = "GEOGRAPHY(POINT,4326)")
    private Point location;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    // Getters & Setters
}
