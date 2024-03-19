package com.example.Booking.Movie.Tickets.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryBookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tempId;
    @NotBlank(message = "user Name is mandatory")
    private String userName;
    @NotBlank(message = "Movie Name is mandatory")
    private String movieName;
    @NotBlank(message = "Theater Name is mandatory")
    private String theaterName;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @NotBlank(message = "ShowTime is mandatory")
    private String showTime;
    private double totalPrice;
    @NotBlank(message = "Date is mandatory")
    private String date;
    @NotEmpty(message = "seats will not empty")
    private List<Integer> seats;
}
