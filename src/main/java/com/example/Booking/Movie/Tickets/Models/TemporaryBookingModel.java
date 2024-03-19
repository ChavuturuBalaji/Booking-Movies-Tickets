package com.example.Booking.Movie.Tickets.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryBookingModel {
    @JsonIgnore
    private int tempId;
    private String userName;
    private String movieName;
    private String theaterName;
    private String location;
    private String showTime;
    private double totalPrice;
    private String date;
    private List<Integer> seats;
}
