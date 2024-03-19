package com.example.Booking.Movie.Tickets.Service.Interface;

import com.example.Booking.Movie.Tickets.Models.TheaterModel;
import org.springframework.http.ResponseEntity;

public interface TheaterSerInter {
     ResponseEntity<?> addTheater(TheaterModel theaterModel);
     ResponseEntity<?> getAllTheaters();
     ResponseEntity<?> deleteTheater(int theaterId);
}
