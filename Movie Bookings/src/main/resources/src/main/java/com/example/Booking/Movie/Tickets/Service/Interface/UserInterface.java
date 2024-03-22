package com.example.Booking.Movie.Tickets.Service.Interface;

import com.example.Booking.Movie.Tickets.Models.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInterface {
     UserModel addUser(UserModel userModel);
     List<MovieModel> movies();
     List<TheaterModel> theaters(String movieName);
    ResponseEntity<?> getShows(String movieName, String theaterName, String location, String date);
    ResponseEntity<?> getSeats(String movieName, String theaterName, String location, String date, String time);
     ResponseEntity<?> bookTicket(TemporaryBookingModel temporaryBooking);
     ResponseEntity<?> payment(PaymentModel paymentModel);
     ResponseEntity<?> myBookings(String userName);
     ResponseEntity<?> cancelTicket(int bookingId);
     ResponseEntity<?> cancelConfirm();
     void  getTopMovieAndTopTheater();
}
