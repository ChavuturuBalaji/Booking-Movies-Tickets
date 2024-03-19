package com.example.Booking.Movie.Tickets.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String userName;
    private String password;
    private String name;
    private String mobile;
    private String role;
    @JsonIgnore
    List<BookingModel> bookings;
}

