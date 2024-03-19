package com.example.Booking.Movie.Tickets.Controller;


import com.example.Booking.Movie.Tickets.Models.*;
import com.example.Booking.Movie.Tickets.Service.Interface.AdminSerInter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Booking.Movie.Tickets.Models.AdminModel;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminSerInter adminSer;



    @PostMapping("/update")
    public ResponseEntity<?> updateAdminDetails(@RequestParam String theaterName, @RequestParam String location, @Valid @RequestBody AdminModel adminModel){
        return adminSer.updateDetails(theaterName,location,adminModel);
    }

    @PostMapping("/addMovies")
    public ResponseEntity<?> addMoviesToTheater(@RequestParam int theaterId, @RequestBody List<MovieModel> movies){
        return adminSer.addMovies(theaterId,movies);
    }

    @PostMapping("/addExistMovie")
    public ResponseEntity<?> addExistMovies(@RequestParam int theaterId, @RequestParam int movieId){
        return adminSer.addExistMovies(theaterId,movieId);
    }

    @GetMapping("/getMovies")
    public ResponseEntity<?> getMovies(@RequestParam int theaterId){
        return adminSer.getMovies(theaterId);
    }

    @PostMapping("/addShows")
    public ResponseEntity<?> addShowsToMoviesInTheatre(@RequestParam int theaterId, @RequestParam int movieId,@Valid @RequestBody ShowsModel showsModel){
        return adminSer.addShowsToMoviesInTheatre(theaterId,movieId,showsModel);
    }
    @PostMapping("/addSeats")
    public ResponseEntity<?> addSeats(@RequestParam int showId,@Valid @RequestBody List<SeatsModel> seats){
        return adminSer.addSeats(showId,seats);
    }

    @DeleteMapping("/deleteMovieInTheater")
    public ResponseEntity<?> deleteMovie(@RequestParam int theaterId, @RequestParam int movieId){
        return adminSer.deleteMovieInTheater(theaterId,movieId);
    }

    @DeleteMapping("/deleteShow")
    public ResponseEntity<?> deleteShow(@RequestParam int showId){
        return adminSer.deleteShow(showId);
    }

    @DeleteMapping("/deleteSeats")
    public ResponseEntity<?> deleteSeats(@RequestParam int showId,@RequestBody List<SeatsModel> seats){
        return adminSer.deleteSeats(showId,seats);
    }

    @GetMapping("/balaji")
    public ResponseEntity<?> rest(@RequestParam int theaterId, @RequestParam int movieId){
        return adminSer.getNumberOfBookings(theaterId,movieId);
    }

    @GetMapping("/allMovies")
    public ResponseEntity<?> getAllMovies(){
        return adminSer.getAllMovies();
    }


}
