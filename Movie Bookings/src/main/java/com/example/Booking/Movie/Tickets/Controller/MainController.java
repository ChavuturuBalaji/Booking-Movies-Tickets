package com.example.Booking.Movie.Tickets.Controller;

import com.example.Booking.Movie.Tickets.Models.TheaterModel;
import com.example.Booking.Movie.Tickets.Service.Interface.TheaterSerInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainController {
    @Autowired
    TheaterSerInter theaterSer;

    @PostMapping("/theaterRegister")
    public ResponseEntity<?> addTheater(@RequestBody TheaterModel theaterModel){
        return theaterSer.addTheater(theaterModel);
    }

    @GetMapping("/allTheaters")
    public ResponseEntity<?> getAllTheaters(){
        return theaterSer.getAllTheaters();
    }

    @DeleteMapping("/deleteTheater")
    public ResponseEntity<?> deleteTheater(@RequestParam int theaterId){
        return theaterSer.deleteTheater(theaterId);
    }
}
