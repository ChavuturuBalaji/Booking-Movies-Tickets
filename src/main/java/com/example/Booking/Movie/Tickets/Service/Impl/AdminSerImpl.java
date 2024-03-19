package com.example.Booking.Movie.Tickets.Service.Impl;

import com.example.Booking.Movie.Tickets.Conversion.Entity_Model.Entity_Models;
import com.example.Booking.Movie.Tickets.Conversion.Model_Entity.Models_Entity_Conversion;
import com.example.Booking.Movie.Tickets.Entities.*;
import com.example.Booking.Movie.Tickets.Models.*;
import com.example.Booking.Movie.Tickets.Repository.*;
import com.example.Booking.Movie.Tickets.Service.Interface.AdminSerInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminSerImpl implements AdminSerInter {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    Models_Entity_Conversion modelsEntity;
    @Autowired
    Entity_Models entityModels;
    @Autowired
    TheaterRepo theaterRepo;
    @Autowired
    MovieRepo movieRepo;

    @Autowired
    ShowsRepo showsRepo;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    SeatRepo seatRepo;

    @Override
    public ResponseEntity<?> updateDetails(String theaterName, String location, AdminModel adminModel){
        if (theaterName.isBlank() || location.isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please enter valid theater name and location");
        }
        else if (adminModel.getPassword().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password is mandatory");
        }
        TheatreEntity theatre = theaterRepo.findByTheatreNameAndLocation(theaterName,location);
        if (null == theatre.getAdmin()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found in that theater");
        }
        else if (theatre.getAdmin().getUserName().isBlank()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin user name not found");
        }
        if (theatre.getAdmin().getUserName().isBlank()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin user not found in database");
        }

        if (theatre.getAdmin().getUserName().equalsIgnoreCase(adminModel.getUserName())){
            AdminEntity adminEntity = adminRepo.findById(theatre.getAdmin().getUserName()).get();
            adminEntity.setPassword(encoder.encode(adminModel.getPassword()));
            adminEntity.setName(adminModel.getName());
            adminEntity.setMobile(adminModel.getMobile());
            return new ResponseEntity<>(entityModels.adminEntity_Model(adminRepo.save(adminEntity)), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please enter provided username");
    }

    @Override
    public ResponseEntity<?> addMovies(int theaterId, List<MovieModel> movies){

        Optional<TheatreEntity> theatre = theaterRepo.findById(theaterId);
        if (theatre.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater not found please enter valid id");
        }

        // checking movies has any null values
        if (movies.stream().anyMatch(movie -> movie.getMovieName().isBlank() || movie.getDuration().isBlank() || movie.getGenre().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please enter movie details correct no null values/empty spaces.");
        }

        // checking any movies has already existed in theater
        List<MovieModel> existMovies = movies.stream().filter(movie -> theatre.get().getMovies().stream().anyMatch(i ->
                i.getMovieName().equals(movie.getMovieName()))).toList();

        if (!existMovies.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("These movies are already present in theater:- " + existMovies.stream()
                    .map(MovieModel::getMovieName)
                    .collect(Collectors.joining(","))+" .");
        }

        List<MovieEntity> movieEntities = new ArrayList<>(theatre.get().getMovies());
        movies.forEach(i-> {
            MovieEntity movieEntity = modelsEntity.movieModel_Entity(i);
            movieEntities.add(movieEntity);
        });

        theatre.get().setMovies(movieEntities);
        return new ResponseEntity<>(entityModels.theaterModel(theaterRepo.save(theatre.get())),HttpStatus.CREATED);
    }

    // add exist movies
    @Override
    public ResponseEntity<?> addExistMovies(int theaterId, int movieId){
        if(theaterRepo.existsById(theaterId) && movieRepo.existsById(movieId)){
            TheatreEntity theatre = theaterRepo.findById(theaterId).get();
            if (theatre.getTheatreName().isBlank() || theatre.getLocation().isBlank()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theater record found but it contain null values");
            }
            MovieEntity movie = movieRepo.findById(movieId).get();

            if (movie.getMovieName().isBlank() || movie.getGenre().isBlank() || movie.getDuration().isBlank()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie record found not it contain null values");
            }

            if (!theatre.getMovies().contains(movie)){  // checking movie is already present or not
                List<MovieEntity> movieEntities = new ArrayList<>(theatre.getMovies());
                movieEntities.add(movie);
                theatre.setMovies(movieEntities);
                theaterRepo.save(theatre);
                return ResponseEntity.status(HttpStatus.CREATED).body("movie is added to theater");
            }else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie is already present in theater");
            }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please enter valid id for theater and movie");
    }


    @Override
    public ResponseEntity<?> addShowsToMoviesInTheatre(int theaterId, int movieId,ShowsModel showsModel) {
        if (showsModel.getDate().isBlank() || showsModel.getShowTime().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter details correct no null values");
        }

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(showsModel.getDate(), dateFormatter);
            LocalDate currentDate = LocalDate.now();
            if (date.isBefore(currentDate) || date.isEqual(currentDate)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date must be future");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter valid date dd/mm/yyyy");
        }


        try {
            LocalTime showTime = LocalTime.parse(showsModel.getShowTime());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter valid time (hh:mm)");
        }


        Optional<TheatreEntity> theatre = theaterRepo.findById(theaterId);
        if (theatre.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater not found enter valid id");
        }

        Optional<MovieEntity> movie = theaterRepo.findById(theatre.get().getTheatreId()).get().getMovies()

                .stream().filter(i-> i.getMovieId() == movieId)
                .findFirst();

        if (movie.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("movie not found enter valid id");
        }
        ShowsEntity showsEntity = modelsEntity.showModel_entity(showsModel);
        if (!theatre.get().getShows().isEmpty()){
            if(theatre.get().getShows().stream().anyMatch(i -> i.getShowTime().equals(showsModel.getShowTime()) &&
                    i.getDate().equals(showsModel.getDate()))){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("show is already present");
            }
        }
        showsEntity.setTheatre(theatre.get());
        showsEntity.setMovie(movie.get());
        theatre.get().getShows().add(showsEntity);
        TheatreEntity savedTheatre = theaterRepo.save(theatre.get());
        return new ResponseEntity<>(entityModels.setShows(savedTheatre),HttpStatus.CREATED);
    }

    public TheaterModel getTheatre(){
        TheatreEntity theatreEntity = theaterRepo.findById(1).get();
        TheaterModel theaterModel = entityModels.setShows(theatreEntity);
        System.out.println("00");
        return theaterModel;
    }

    public MovieEntity getMovie(int id){
        return movieRepo.findById(id).get();
    }


    @Override
    //add seats
    public ResponseEntity<?> addSeats(int id, List<SeatsModel> seatsModel){
        Optional<ShowsEntity> showsEntity = showsRepo.findById(id);  //check show is present or not
        if (showsEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Show not found");
        }

        Set<SeatsModel> existSeats = seatsModel.stream().filter(seat -> showsEntity.get().getSeats().stream().anyMatch(j ->
                j.getSeatNumber() == seat.getSeatNumber())).collect(Collectors.toSet());  // check already seats are present in show or not


        if (!existSeats.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("These seats are already present:- " + existSeats.stream()
                    .map(seat -> String.valueOf(seat.getSeatNumber()))
                    .collect(Collectors.joining(",")));
        }

        //checking seats are not exceed to total seats
        if (showsEntity.get().getSeats().size() + seatsModel.size() > showsEntity.get().getTotalSeats()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Total seats for this show is " + showsEntity.get().getTotalSeats() +" but already show contain "+
                    showsEntity.get().getSeats().size()+" seats and your adding now " + seatsModel.size()+" please enter correctly");
        }

        Set<SeatsEntity> seats = seatsModel.stream().map(modelsEntity::seatModel_Entity).map(seatEntity -> {
                    seatEntity.setShow(showsEntity.get());
                    return seatEntity;
                })
                .collect(Collectors.toSet());


        showsEntity.get().setSeats(seats);
        ShowsEntity shows= showsRepo.save(showsEntity.get());
        ShowsModel showsModel = entityModels.showEntity_Model(shows);
        return new ResponseEntity<>(showsModel,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getMovies(int theaterId){
        if(!theaterRepo.existsById(theaterId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("theater not found");
        }
        if (theaterRepo.findById(theaterId).get().getMovies().isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No movies in theater");
        }
        List<MovieEntity> movieEntities = theaterRepo.findById(theaterId).get().getMovies();
        List<MovieModel> movies = new ArrayList<>();
        movieEntities.forEach(i ->{
            movies.add(entityModels.movieEntity_model(i));
        });
        return new ResponseEntity<>(movies,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> deleteMovieInTheater(int theaterId, int movieId){
        if(theaterRepo.existsById(theaterId) && movieRepo.existsById(movieId)){
            TheatreEntity theatre = theaterRepo.findById(theaterId).get();
            MovieEntity movie = movieRepo.findById(movieId).get();
            if(theatre.getMovies().contains(movie)){  // theater contain that movie or not
                List<ShowsEntity> show = showsRepo.findByTheatreAndMovie(theatre,movie);

                show.forEach(i -> {
                    deleteShow(i.getShowId());
                });
                theatre.getMovies().remove(movie);
                theaterRepo.save(theatre);
                return ResponseEntity.status(HttpStatus.OK).body("Movies is removed from theater");

//                if (movie.getShows().isEmpty()){  // check movie has any shows
//                    theatre.getMovies().remove(movie);
//                    theaterRepo.save(theatre);
//                    return ResponseEntity.status(HttpStatus.OK).body("Movies is removed from theater");
//                }
//
//                List<ShowsEntity> shows = showsRepo.findByTheatreAndMovie(theatre,movie);
//
//                boolean bool = shows.stream()
//                        .anyMatch(show -> show.getSeats().stream().anyMatch(SeatsEntity::isBooked));
//                if(bool){
//                    return ResponseEntity.status(HttpStatus.CONFLICT).body("seats are booked by user so removing movie will not possible");
//                }
//                shows.forEach(show ->{
//                    LocalDate currentDate = LocalDate.now();
//                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                    LocalDate date = LocalDate.parse(show.getDate(), dateFormatter);
//                    if (date.isBefore(currentDate)){
//                        show.getSeats().forEach(seat->{
//                            seat.setShow(null);
//                            seatRepo.deleteById(seat.getSeatId());
//                        });
//                    }
//                    show.getSeats().forEach(seat->{
//                        seat.setShow(null);
//                        seatRepo.deleteById(seat.getSeatId());
//                    });
//                });
//                shows.clear();
//                movie.setShows(shows);

            }
            return ResponseEntity.status(HttpStatus.OK).body("Movies is not present in theater");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please enter valid id for theater and movie");
    }

    @Override
    public ResponseEntity<?> deleteShow(int showId){
        if (showsRepo.existsById(showId)){
            Optional<ShowsEntity> shows = showsRepo.findById(showId);
            if (shows.isPresent()){
                try{
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = LocalDate.parse(shows.get().getDate(),dateFormat);
                    LocalDate currentDate = LocalDate.now();
                    if (date.isBefore(currentDate)){
                        showsRepo.deleteById(showId);
                        return ResponseEntity.status(HttpStatus.OK).body("Show is deleted");
                    }

                    LocalTime time = LocalTime.parse(shows.get().getShowTime());
                    LocalTime currentTime = LocalTime.now();
                    if (time.isBefore(currentTime)){
                        showsRepo.deleteById(showId);
                        return ResponseEntity.status(HttpStatus.OK).body("Show is deleted");
                    }

                    if (!CollectionUtils.isEmpty(shows.get().getSeats())){
                        Set<SeatsEntity> seats  = shows.get().getSeats();
                        Set<SeatsModel> bookedSeats = seats.stream().filter(SeatsEntity::isBooked)
                                .map(seat -> entityModels.seatEntity_Model(seat)).collect(Collectors.toSet());
                        if (!CollectionUtils.isEmpty(bookedSeats)){
                            return ResponseEntity.status(HttpStatus.CONFLICT).body("These seats are booked by users:- " +bookedSeats.stream()
                                    .map(seat -> String.valueOf(seat.getSeatNumber())).collect(Collectors.joining(",")) + " show will not delete");
                        }
                    }
                    showsRepo.deleteById(showId);
                    return ResponseEntity.status(HttpStatus.OK).body("Show is deleted");
                }catch (Exception e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter valid date dd/mm/yyyy");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Seat record not found please enter valid id");
    }

    @Override
    public ResponseEntity<?> deleteSeats(int showId,List<SeatsModel> seats){

        if (seats.stream().anyMatch(seat -> seat.getSeatNumber()==0 || seat.isBooked())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter details correct no null values.");
        }

        if (showsRepo.existsById(showId)){
            Optional<ShowsEntity> show = showsRepo.findById(showId);
            if(show.isPresent()){
                Set<SeatsModel> noSeats = seats.stream()
                        .filter(seat -> show.get().getSeats().stream().anyMatch(i -> i.getSeatNumber() != seat.getSeatNumber()))
                        .collect(Collectors.toSet());


                if (!noSeats.isEmpty()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("These seats are not present in show:- " + noSeats
                            .stream().map(seat -> String.valueOf(seat.getSeatNumber())).collect(Collectors.joining(","))+
                            " . please enter valid seat numbers which are present in show");
                }
                seats.forEach(seat->{
                    SeatsEntity seatsEntity = modelsEntity.seatModel_Entity(seat);
                    seatRepo.deleteById(seatsEntity.getSeatId());
                    show.get().getSeats().stream().filter(i -> i.getSeatNumber() == seat.getSeatNumber())
                            .map(i -> show.get().getSeats().remove(i)).forEach(System.out::println);
                });
                showsRepo.save(show.get());
                return ResponseEntity.status(HttpStatus.OK).body("Seats are deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Show not found");
    }


    public ResponseEntity<?> getNumberOfBookings(int theaterId, int movieId){

        if (theaterRepo.existsById(theaterId)){
            if (movieRepo.existsById(movieId)){
                Optional<TheatreEntity> theatre = theaterRepo.findById(theaterId);
                Optional<MovieEntity> movie = movieRepo.findById(movieId);
                if (theatre.isPresent() && movie.isPresent()){
                    if (theatre.get().getMovies().contains(movie.get())){
                        List<ShowsEntity> shows = showsRepo.findByTheatreAndMovie(theatre.get(),movie.get());

                        long bookedSeats = shows.stream()
                                .flatMap(show -> show.getSeats().stream())
                                .filter(SeatsEntity::isBooked)
                                .count();

                        long availableSeats = shows.stream()
                                .flatMap(show -> show.getSeats().stream())
                                .filter(seat -> !seat.isBooked())
                                .count();

                        return ResponseEntity.status(HttpStatus.OK).body("Total shows of this movie is:- " + shows.size()
                                +"\n" +"Total Booked seats:- " + bookedSeats +"\n" + "Available Seats:- " + availableSeats);

                    }
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie is not present in this theater");
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found enter valid id");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater not found enter valid id");
    }

    @Override
    public ResponseEntity<?> getAllMovies(){
        return new ResponseEntity<>(movieRepo.findAll().stream().map(movie -> entityModels.movieEntity_model(movie)).toList(),HttpStatus.OK);
    }
}
