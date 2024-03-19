package com.example.Booking.Movie.Tickets.Service.Interface;

import com.example.Booking.Movie.Tickets.Models.AdminModel;
import com.example.Booking.Movie.Tickets.Models.MovieModel;
import com.example.Booking.Movie.Tickets.Models.SeatsModel;
import com.example.Booking.Movie.Tickets.Models.ShowsModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminSerInter {
     ResponseEntity<?> updateDetails(String theaterName, String location, AdminModel adminModel);
     ResponseEntity<?> addMovies(int theaterId, List<MovieModel> movies);
     ResponseEntity<?> addExistMovies(int theaterId, int movieId);
     ResponseEntity<?> addShowsToMoviesInTheatre(int theaterId, int movieId, ShowsModel showsModel);
     ResponseEntity<?> addSeats(int id, List<SeatsModel> seatsModel);
     ResponseEntity<?> getMovies(int theaterId);
     ResponseEntity<?> deleteMovieInTheater(int theaterId, int movieId);
     ResponseEntity<?> deleteShow(int showId);
     ResponseEntity<?> deleteSeats(int showId,List<SeatsModel> seats);
      ResponseEntity<?> getNumberOfBookings(int theaterId, int movieId);
      ResponseEntity<?> getAllMovies();
}
