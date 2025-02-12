package com.codingshuttle.project.uber.uberApp.controllers;

import com.codingshuttle.project.uber.uberApp.dto.DriverLocDto;
import com.codingshuttle.project.uber.uberApp.dto.PointDto;
import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.DriverLocation;
import com.codingshuttle.project.uber.uberApp.repositories.DriverLocationRepository;
import com.codingshuttle.project.uber.uberApp.repositories.DriverRepository;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LocationWebSocketController {
    private final DriverRepository repository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ModelMapper modelMapper;

    public LocationWebSocketController(DriverRepository repository, SimpMessagingTemplate messagingTemplate, ModelMapper modelMapper) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
        this.modelMapper = modelMapper;
    }

    @Scheduled(fixedRate = 5000) // Every 5 seconds
    public void sendDriverLocationUpdates() {
        List<Driver> drivers = repository.findAll();
        List<DriverLocDto> driverLocDtos=new ArrayList<>();
        for(Driver driver:drivers){
            DriverLocDto driverLocDto=new DriverLocDto();
            Point p1=driver.getCurrentLocation();
            PointDto pt=modelMapper.map(p1, PointDto.class);
            driverLocDto.setCurrentLocation(pt);
            driverLocDtos.add(driverLocDto);
        }
        for (DriverLocDto driver : driverLocDtos) {
            System.out.println(" sending location to all subscriber at each 5 seconds "+driver.getCurrentLocation());
            messagingTemplate.convertAndSend("/topic/driverLocation", driver.getCurrentLocation());
        }
    }
}

//A WebSocket topic is like a broadcast channel where multiple clients can subscribe and receive messages.
//Topics usually start with /topic/ in Spring WebSockets.
//All subscribed clients get the same message.
