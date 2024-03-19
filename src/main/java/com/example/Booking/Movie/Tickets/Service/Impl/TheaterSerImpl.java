package com.example.Booking.Movie.Tickets.Service.Impl;

import com.example.Booking.Movie.Tickets.Conversion.Entity_Model.Entity_Models;
import com.example.Booking.Movie.Tickets.Conversion.Model_Entity.Models_Entity_Conversion;
import com.example.Booking.Movie.Tickets.Entities.MovieEntity;
import com.example.Booking.Movie.Tickets.Entities.ShowsEntity;
import com.example.Booking.Movie.Tickets.Entities.TheatreEntity;
import com.example.Booking.Movie.Tickets.Models.TheaterModel;
import com.example.Booking.Movie.Tickets.Repository.AdminRepo;
import com.example.Booking.Movie.Tickets.Repository.ShowsRepo;
import com.example.Booking.Movie.Tickets.Repository.TheaterRepo;
import com.example.Booking.Movie.Tickets.Service.Interface.AdminSerInter;
import com.example.Booking.Movie.Tickets.Service.Interface.TheaterSerInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheaterSerImpl implements TheaterSerInter {
    @Autowired
    TheaterRepo theaterRepo;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    AdminSerInter adminSer;
    @Autowired
    Models_Entity_Conversion modelsEntity;

    @Autowired
    ShowsRepo showsRepo;

    @Autowired
    Entity_Models entityModels;

    @Override
    public ResponseEntity<?> addTheater(TheaterModel theaterModel){
        if(adminRepo.existsById(theaterModel.getAdmin().getUserName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("admin name already used");
        }

        if (theaterRepo.findByTheatreNameAndLocation(theaterModel.getTheatreName(),theaterModel.getLocation())!= null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Theater is already present in that location");
        }
        TheatreEntity theatre = modelsEntity.theatreModel_Entity(theaterModel);
        theatre.setMovies(new ArrayList<>());
        theatre.setShows(new ArrayList<>());
        return new ResponseEntity<>(entityModels.theatreEntity_Model(theaterRepo.save(theatre)),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllTheaters(){
        return new ResponseEntity<>(theaterRepo.findAll().stream()
                .map(theater -> entityModels.theatreEntity_Model(theater)).toList(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteTheater(int theaterId){
        if (theaterRepo.existsById(theaterId)){
            Optional<TheatreEntity> theatre = theaterRepo.findById(theaterId);
            if (theatre.isPresent()){
                if (CollectionUtils.isEmpty(theatre.get().getMovies())){
                    theaterRepo.deleteById(theaterId);
                    return ResponseEntity.status(HttpStatus.OK).body("Theater is deleted");
                }
                List<MovieEntity> movies = theatre.get().getMovies();
                List<ShowsEntity> shows = new ArrayList<>();
                movies.stream().map(movie -> shows.addAll(showsRepo.findByTheatreAndMovie(theatre.get(),movie))).forEach(System.out::println);

//                shows.stream()
//                        .map(ShowsEntity::getShowId)
//                        .forEachOrdered(showId -> showsRepo.deleteById(showId));

                shows.forEach(show ->{
                    adminSer.deleteShow(show.getShowId());
                });

                theatre.get().getMovies().clear();
                theaterRepo.deleteById(theatre.get().getTheatreId());
                return ResponseEntity.status(HttpStatus.OK).body("Theater is deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater not found entire valid id");
    }
}
