package com.example.Booking.Movie.Tickets.Controller;

import com.example.Booking.Movie.Tickets.Models.*;
import com.example.Booking.Movie.Tickets.Service.Interface.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
     UserInterface userSer;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserModel userModel){
        UserModel user = userSer.addUser(userModel);
        if (user == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user name is already used:- " + userModel.getUserName());
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieModel>> getAllMovies(){
        return new ResponseEntity<>(userSer.movies(),HttpStatus.OK);
    }

    @GetMapping("/theaters")
    public ResponseEntity<?> getTheatersBasedOnMovieName(@RequestParam String movieName){
        List<TheaterModel> theaters = userSer.theaters(movieName);
        if(theaters.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie not found");
        }
        return new ResponseEntity<>(theaters, HttpStatus.OK);
    }

    @GetMapping("/shows")
    public ResponseEntity<?> getShows(@RequestParam String movieName, @RequestParam String theaterName,
                                     @RequestParam String location, @RequestParam String date){
        return userSer.getShows(movieName,theaterName,location,date);
    }

    @GetMapping("/seats")
    public ResponseEntity<?> getSeats(@RequestParam String movieName, @RequestParam String theaterName, @RequestParam String location,
                                   @RequestParam String date, @RequestParam String time){
        return userSer.getSeats(movieName,theaterName,location,date,time);
    }

    @PostMapping("/book")
    public ResponseEntity<?>  bookTicket(@Valid @RequestBody TemporaryBookingModel temp){
        return userSer.bookTicket(temp);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@Valid @RequestBody PaymentModel paymentModel){
       return userSer.payment(paymentModel);
    }

    @GetMapping("/myBookings")
    public ResponseEntity<?> myBookings(@RequestParam String userName){
        return userSer.myBookings(userName);
    }

    @GetMapping("/cancellation")
    public ResponseEntity<?> cancellation(@RequestParam int bookingId){
        return userSer.cancelTicket(bookingId);
    }

    @RequestMapping("/cancelConfirm")
    public ResponseEntity<?> cancelConfirm(){
        return userSer.cancelConfirm();
    }

    @GetMapping("/getTopMovieAndTheater")
    public void getTopMovieAndTheater(){
        userSer.getTopMovieAndTopTheater();
    }
}
