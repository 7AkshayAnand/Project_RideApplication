package com.codingshuttle.project.uber.uberApp.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
//    a unique conatraints is created  for user_id column in this rider instance in database
    private Double rating;
//    this will be aggregate rating of this rider like average of last 50 rides rating


}
