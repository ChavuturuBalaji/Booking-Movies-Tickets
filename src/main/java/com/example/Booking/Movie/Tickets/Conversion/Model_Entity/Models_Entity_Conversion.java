package com.example.Booking.Movie.Tickets.Conversion.Model_Entity;

import com.example.Booking.Movie.Tickets.Entities.*;
import com.example.Booking.Movie.Tickets.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class Models_Entity_Conversion {

    @Autowired
    PasswordEncoder passwordEncoder;


    public UserEntity userModel_Entity(UserModel userModel){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userModel.getUserName());
        userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userEntity.setName(userModel.getName());
        userEntity.setMobile(userModel.getMobile());
        userEntity.setRole("ROLE_USER");
        return userEntity;
    }

    public AdminEntity adminModel_Entity(AdminModel adminModel){
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setUserName(adminModel.getUserName());
        adminEntity.setPassword(passwordEncoder.encode(adminModel.getPassword()));
        adminEntity.setName(adminModel.getName());
        adminEntity.setMobile(adminModel.getMobile());
        adminEntity.setRole(adminModel.getRole());
        return adminEntity;
    }

    public TheatreEntity theatreModel_Entity(TheaterModel theaterModel){
        TheatreEntity theatre = new TheatreEntity();
        theatre.setTheatreName(theaterModel.getTheatreName());
        theatre.setLocation(theaterModel.getLocation());
        theatre.setAdmin(adminModel_Entity(theaterModel.getAdmin()));
        return theatre;
    }

    public MovieEntity movieModel_Entity(MovieModel movieModel){
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieName(movieModel.getMovieName());
        movieEntity.setDuration(movieModel.getDuration());
        movieEntity.setGenre(movieModel.getGenre());
        return movieEntity;
    }

    public ShowsEntity showModel_entity(ShowsModel showsModel){
        ShowsEntity showsEntity = new ShowsEntity();
        showsEntity.setShowTime(showsModel.getShowTime());
        showsEntity.setDate(showsModel.getDate());
        showsEntity.setTotalSeats(showsModel.getTotalSeats());
        showsEntity.setTicket_Price(showsModel.getTicket_Price());
        showsEntity.setSeats(new HashSet<>());
        return showsEntity;
    }
    public SeatsEntity seatModel_Entity(SeatsModel seatsModel){
        SeatsEntity seatsEntity = new SeatsEntity();
        seatsEntity.setSeatNumber(seatsModel.getSeatNumber());
        seatsEntity.setBooked(seatsModel.isBooked());
        return seatsEntity;
    }
    public TemporaryBookingEntity tempModel_Entity(TemporaryBookingModel bookingModel){
        TemporaryBookingEntity temp = new TemporaryBookingEntity();
        temp.setUserName(bookingModel.getUserName());
        temp.setMovieName(bookingModel.getMovieName());
        temp.setTheaterName(bookingModel.getTheaterName());
        temp.setLocation(bookingModel.getLocation());
        temp.setShowTime(bookingModel.getShowTime());
        temp.setTotalPrice(bookingModel.getTotalPrice());
        temp.setDate(bookingModel.getDate());
        temp.setSeats(bookingModel.getSeats());
        return temp;
    }

    public BookingEntity temp_bookingEntity(TemporaryBookingEntity temp){
        BookingEntity bookingEntity= new BookingEntity();
        bookingEntity.setDate(temp.getDate());
        bookingEntity.setMovieName(temp.getMovieName());
        bookingEntity.setTheaterName(temp.getTheaterName());
        bookingEntity.setLocation(temp.getLocation());
        bookingEntity.setShowTime(temp.getShowTime());
        bookingEntity.setTotalPrice(temp.getTotalPrice());
        bookingEntity.setSeats(temp.getSeats());
        return bookingEntity;
    }

}
