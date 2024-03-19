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
public class BookingModel {
    private int bookingId;
    private String movieName;
    private String theaterName;
    private String location;
    private String showTime;
    private String date;
    private double totalPrice;
    private List<Integer> seats;
    @JsonIgnore
    private List<UserModel> users;
}
