package com.example.Booking.Movie.Tickets.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AvailableSeats {
    private List<Integer> availableSeats;
    private List<Integer> bookedSeats;

    public AvailableSeats() {
        availableSeats = new ArrayList<>();
        bookedSeats =new ArrayList<>();
    }
}
