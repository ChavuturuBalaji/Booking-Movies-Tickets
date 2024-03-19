package com.example.Booking.Movie.Tickets.Conversion.Entity_Model;

import com.example.Booking.Movie.Tickets.Entities.*;
import com.example.Booking.Movie.Tickets.Models.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Entity_Models {

    public UserModel userEntity_Model(UserEntity userEntity){
        UserModel userModel = new UserModel();
        userModel.setUserName(userEntity.getUserName());
        userModel.setPassword(userEntity.getPassword());
        userModel.setName(userEntity.getName());
        userModel.setMobile(userEntity.getMobile());
        userModel.setRole(userEntity.getRole());
        return userModel;
    }

    public AdminModel adminEntity_Model(AdminEntity adminEntity){
        AdminModel adminModel = new AdminModel();
        adminModel.setUserName(adminEntity.getUserName());
        adminModel.setPassword(adminEntity.getPassword());
        adminModel.setName(adminEntity.getName());
        adminModel.setMobile(adminEntity.getMobile());
        adminModel.setRole(adminEntity.getRole());
        return adminModel;
    }
    public TheaterModel theatreEntity_Model(TheatreEntity theatreEntity){
        TheaterModel theaterModel = new TheaterModel();
        theaterModel.setTheatreId(theatreEntity.getTheatreId());
        theaterModel.setTheatreName(theatreEntity.getTheatreName());
        theaterModel.setLocation(theatreEntity.getLocation());
        theaterModel.setAdmin(adminEntity_Model(theatreEntity.getAdmin()));
        return theaterModel;
    }
    public MovieModel movieEntity_model(MovieEntity movieEntity){
        MovieModel movieModel = new MovieModel();
        movieModel.setMovieId(movieEntity.getMovieId());
        movieModel.setMovieName(movieEntity.getMovieName());
        movieModel.setDuration(movieEntity.getDuration());
        movieModel.setGenre(movieEntity.getGenre());
        return movieModel;
    }

    public TheaterModel theaterModel(TheatreEntity theatre){
        TheaterModel theaterModel = new TheaterModel();
        theaterModel.setTheatreId(theatre.getTheatreId());
        theaterModel.setTheatreName(theatre.getTheatreName());
        theaterModel.setLocation(theatre.getLocation());
        theaterModel.setAdmin(adminEntity_Model(theatre.getAdmin()));
        List<MovieModel> movies = new ArrayList<>();
        for (MovieEntity movie : theatre.getMovies()){
            movies.add(movieEntity_model(movie));
        }
        theaterModel.setMovies(movies);
        return theaterModel;
    }

    public ShowsModel showEntity_Model(ShowsEntity showsEntity){
        ShowsModel showsModel = new ShowsModel();
        showsModel.setShowId(showsEntity.getShowId());
        showsModel.setShowTime(showsEntity.getShowTime());
        showsModel.setDate(showsEntity.getDate());
        showsModel.setTotalSeats(showsEntity.getTotalSeats());
        showsModel.setTicket_Price(showsEntity.getTicket_Price());
        showsModel.setMovie(movieEntity_model(showsEntity.getMovie()));
        showsModel.setTheatre(theatreEntity_Model(showsEntity.getTheatre()));
        return showsModel;
    }

    public TheaterModel setShows(TheatreEntity theatre){
        TheaterModel theaterModel = new TheaterModel();
        theaterModel.setTheatreId(theatre.getTheatreId());
        theaterModel.setTheatreName(theatre.getTheatreName());
        theaterModel.setLocation(theatre.getLocation());
        theaterModel.setAdmin(adminEntity_Model(theatre.getAdmin()));
        List<MovieModel> movies = new ArrayList<>();
        for (MovieEntity movie : theatre.getMovies()){
            MovieModel movieModel = movieEntity_model(movie);
            List<ShowsModel> shows = new ArrayList<>();
            for (ShowsEntity show: movie.getShows()){
                ShowsModel showsModel = showEntity_Model(show);
                Set<SeatsModel> seats = new HashSet<>();
                for (SeatsEntity seat : show.getSeats()){
                    seats.add(seatEntity_Model(seat));
                }
                showsModel.setSeats(seats);
                shows.add(showsModel);
            }
            movieModel.setShows(shows);
            movies.add(movieModel);
        }
        theaterModel.setMovies(movies);
        return theaterModel;
    }

    public SeatsModel seatEntity_Model(SeatsEntity seatsEntity){
        SeatsModel seatsModel = new SeatsModel();
//        seatsModel.setSeatId(seatsEntity.getSeatId());
        seatsModel.setSeatNumber(seatsEntity.getSeatNumber());
        seatsModel.setBooked(seatsEntity.isBooked());
        return seatsModel;
    }

    public BookingModel bookingEntity_Model(BookingEntity booking){
        BookingModel bookingModel = new BookingModel();
        bookingModel.setMovieName(booking.getMovieName());
        bookingModel.setTheaterName(booking.getTheaterName());
        bookingModel.setLocation(booking.getLocation());
        bookingModel.setShowTime(booking.getShowTime());
        bookingModel.setDate(booking.getDate());
        bookingModel.setTotalPrice(booking.getTotalPrice());
        bookingModel.setSeats(booking.getSeats());
        return bookingModel;
    }
}
