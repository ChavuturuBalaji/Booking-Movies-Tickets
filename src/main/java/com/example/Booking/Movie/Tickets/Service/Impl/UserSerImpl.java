package com.example.Booking.Movie.Tickets.Service.Impl;

import com.example.Booking.Movie.Tickets.Conversion.Entity_Model.Entity_Models;
import com.example.Booking.Movie.Tickets.Conversion.Model_Entity.Models_Entity_Conversion;
import com.example.Booking.Movie.Tickets.Entities.*;
import com.example.Booking.Movie.Tickets.Models.*;
import com.example.Booking.Movie.Tickets.Repository.*;
import com.example.Booking.Movie.Tickets.Service.Interface.UserInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSerImpl implements UserInterface {
    private String userName;
    private int showId;
    private int tempId;
    private int bookingId;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    SeatRepo seatRepo;

    @Autowired
    MovieRepo movieRepo;

    @Autowired
    TheaterRepo theaterRepo;

    @Autowired
    ShowsRepo showsRepo;

    @Autowired
    TempBookingRepo tempBookingRepo;
    @Autowired
    Models_Entity_Conversion models_Entity;

    @Autowired
    Entity_Models entityModels;

    public List<Integer> available=new ArrayList<>();
    public List<Integer> booked=new ArrayList<>();
    @Autowired
    BookingRepo bookingRepo;

    //user register
    @Override
    public UserModel addUser(UserModel userModel){
        if (userRepo.existsById(userModel.getUserName())){
            return null;
        }
        UserEntity userEntity = models_Entity.userModel_Entity(userModel);
        return entityModels.userEntity_Model(userRepo.save(userEntity));
    }

    // get All movies
    @Override
    public List<MovieModel> movies(){
        return movieRepo.findAll().stream().map(i -> entityModels.movieEntity_model(i)).collect(Collectors.toList());
    }


    // get theaters based on movie name
    @Override
    public List<TheaterModel> theaters(String movieName){
        Optional<MovieEntity> movie = movieRepo.findByMovieName(movieName);
        if (movie.isEmpty()){
            return null;
        }
        List<TheatreEntity> theaters = movie.get().getTheatres();
        return theaters.stream().map(i -> entityModels.theatreEntity_Model(i)).collect(Collectors.toList());
    }


    // get shows
    @Override
    public ResponseEntity<?> getShows(String movieName, String theaterName, String location,String date){
        if (StringUtils.isBlank(movieName) || StringUtils.isBlank(theaterName) || StringUtils.isBlank(location)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter details correctly (movie name,theater name,location");
        }
         TheatreEntity theatre = theaterRepo.findByTheatreNameAndLocation(theaterName,location);
        if (null == theatre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater not found enter valid name and location");
        }
        if (theatre.getMovies().stream().noneMatch(movie -> movie.getMovieName().equalsIgnoreCase(movieName))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("movie is not available in theater");
        }


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        try {

            LocalDate date1 = LocalDate.parse(date,dateFormatter);
            if (date1.isBefore(currentDate)){ // past date
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter correct date not past date");
            }

            if (date1.isEqual(currentDate)){ //current date
                List<ShowsEntity> shows = showsRepo.findAll().stream().filter(i -> i.getMovie().getMovieName().equalsIgnoreCase(movieName)
                        &&  i.getTheatre().getTheatreName().equalsIgnoreCase(theaterName) && i.getTheatre().getLocation().equalsIgnoreCase(location) &&
                        i.getDate().equalsIgnoreCase(date) && !LocalTime.parse(i.getShowTime()).isBefore(currentTime)).toList();
                if (CollectionUtils.isEmpty(shows)){
                    return ResponseEntity.status(HttpStatus.OK).body("shows are not available");
                }
                return new ResponseEntity<>(shows.stream().map(i -> entityModels.showEntity_Model(i)).collect(Collectors.toList()),HttpStatus.OK);
            }

            //future date
            List<ShowsEntity> shows = showsRepo.findAll().stream().filter(i -> i.getMovie().getMovieName().equalsIgnoreCase(movieName)
                    &&  i.getTheatre().getTheatreName().equalsIgnoreCase(theaterName) && i.getTheatre().getLocation().equalsIgnoreCase(location) &&
                    i.getDate().equalsIgnoreCase(date)).toList();
            if (CollectionUtils.isEmpty(shows)){
                return ResponseEntity.status(HttpStatus.OK).body("shows are not available");
            }
            return new ResponseEntity<>(shows.stream().map(i -> entityModels.showEntity_Model(i)).collect(Collectors.toList()),HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter valid date (dd/mm/yyyy)");
        }
    }

    //get seats
    @Override
    public ResponseEntity<?> getSeats(String movieName, String theaterName, String location, String date, String time){
        if (StringUtils.isBlank(movieName) || StringUtils.isBlank(theaterName) || StringUtils.isBlank(location)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter details correctly (movie name,theater name,location");
        }
        TheatreEntity theatre = theaterRepo.findByTheatreNameAndLocation(theaterName,location);
        if (null == theatre){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater not found enter valid name and location");
        }
        if (theatre.getMovies().stream().noneMatch(movie -> movie.getMovieName().equalsIgnoreCase(movieName))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("movie is not available in theater");
        }

        System.out.println(date);

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.parse(date,dateFormatter);
            LocalDate currentDate = LocalDate.now();
            if (date1.isBefore(currentDate)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter correct date");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter valid date");
        }


        try {
            LocalTime currentTime = LocalTime.now();
            LocalTime showTime = LocalTime.parse(time);
            System.out.println("show time");
            if (showTime.isBefore(currentTime)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("show is completed");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter valid time (hh:mm)");
        }


        Optional<ShowsEntity> shows = showsRepo.findAll().stream().filter(i -> i.getMovie().getMovieName().equalsIgnoreCase(movieName)
                && i.getTheatre().getTheatreName().equalsIgnoreCase(theaterName) &&i.getTheatre().getLocation().equalsIgnoreCase(location)
                && i.getDate().equalsIgnoreCase(date) && i.getShowTime().equals(time)).findFirst();
        if (shows.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("show not found please enter valid date and time");
        }
        AvailableSeats availableSeats = new AvailableSeats();
        shows.get().getSeats().forEach(i ->{
            if (i.isBooked()){
                availableSeats.getBookedSeats().add(i.getSeatNumber());
                booked.add(i.getSeatNumber());
            }else {
                availableSeats.getAvailableSeats().add(i.getSeatNumber());
                available.add(i.getSeatNumber());
            }
        });
        return new ResponseEntity<>(availableSeats,HttpStatus.OK);
    }


    //confirmation ticket
    @Override
    public ResponseEntity<?> bookTicket(TemporaryBookingModel temporaryBooking){
        if (!userRepo.existsById(temporaryBooking.getUserName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not found with this "+ temporaryBooking.getUserName() +" username");
        }

        Optional<MovieEntity> movie = movieRepo.findByMovieName(temporaryBooking.getMovieName());
        if (movie.isPresent()) {
            Optional<TheatreEntity> theatre = movie.get().getTheatres().stream().filter(i -> i.getTheatreName().equalsIgnoreCase(temporaryBooking.getTheaterName())
                    && i.getLocation().equalsIgnoreCase(temporaryBooking.getLocation())).findFirst();
            if (theatre.isPresent()) {
                List<Integer> blockedSeats= new ArrayList<>();

                // check seats user entered seats are available are booked
                List<Integer> checkAvailable = temporaryBooking.getSeats().stream()
                        .filter(seat -> booked.contains(seat)).toList();


                if (CollectionUtils.isEmpty(checkAvailable)){

                    // checking that seats are blocked by another user or not
                    if (!CollectionUtils.isEmpty(tempBookingRepo.findAll())){
                        List<Integer> userBlockedSeats = tempBookingRepo.findAll().stream().filter(i-> i.getShowTime().equals(temporaryBooking.getShowTime()) &&
                                i.getDate().equals(temporaryBooking.getDate()) && i.getMovieName().equals(temporaryBooking.getMovieName()) &&
                                i.getTheaterName().equals(temporaryBooking.getTheaterName())).findFirst().get().getSeats();

                        blockedSeats.addAll(temporaryBooking.getSeats().stream().filter(userBlockedSeats::contains).toList());
                    }

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("These seats are already booked:- " + checkAvailable.stream()
                            .map(seat -> String.valueOf(seat)).collect(Collectors.joining(",")));
                }
                if (CollectionUtils.isEmpty(blockedSeats)) {
                    Optional<ShowsEntity> showsEntity = showsRepo.findAll().stream().filter(i -> i.getShowTime().equals(temporaryBooking.getShowTime()) &&
                            i.getDate().equals(temporaryBooking.getDate()) && i.getMovie().getMovieName().equalsIgnoreCase(temporaryBooking.getMovieName()) &&
                            i.getTheatre().getTheatreName().equalsIgnoreCase(temporaryBooking.getTheaterName())).findFirst();
                    if(showsEntity.isPresent()){
                        double totalPrice = showsEntity.get().getTicket_Price() * temporaryBooking.getSeats().size();
                        TemporaryBookingEntity temp = models_Entity.tempModel_Entity(temporaryBooking);
                        temp.setTotalPrice(totalPrice);
                        this.userName = temporaryBooking.getUserName();
                        TemporaryBookingEntity temporaryBookingEntity = tempBookingRepo.save(temp);
                        this.tempId = temporaryBookingEntity.getTempId();
                        this.showId = showsEntity.get().getShowId();
                        return new ResponseEntity<>(temporaryBookingEntity,HttpStatus.OK);
                    }
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("show not found");
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("These seats are blocked by other user:- " + blockedSeats.stream()
                        .map(seat -> String.valueOf(seat)).collect(Collectors.joining(",")));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theatre Not available for this movie");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please check movie name(movie not found)");
    }

    //payment
    @Override
    public ResponseEntity<?> payment(PaymentModel paymentModel){
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCreditCardNumber(paymentModel.getCreditCardNumber());
        Optional<TemporaryBookingEntity> temp = tempBookingRepo.findFirstByUserName(this.userName);
        if (temp.isPresent()){
            PaymentEntity payment = paymentRepo.save(paymentEntity);
            if (!paymentRepo.existsById(payment.getPaymentId())){
                tempBookingRepo.deleteById(this.tempId);
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Payment Failed");
            }
            BookingEntity bookingEntity = models_Entity.temp_bookingEntity(temp.get());
            Optional<UserEntity> user = userRepo.findById(temp.get().getUserName());
//            System.out.println(user.get().getMobile());

            bookingEntity.setUsers(user.get());
            List<SeatsEntity> seats = seatRepo.findByShow(showsRepo.findById(this.showId).get());
            seats.forEach(i -> {
                if (temp.get().getSeats().contains(i.getSeatNumber())){
                    i.setBooked(true);
                }
            });
            seatRepo.saveAll(seats);
            bookingEntity.setPayment(payment);
            tempBookingRepo.deleteById(this.tempId);
            return new ResponseEntity<>(entityModels.bookingEntity_Model(bookingRepo.save(bookingEntity)),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @Override
    public ResponseEntity<?> myBookings(String userName){
        List<BookingModel> bookings = bookingRepo.findAll().stream().filter(i-> i.getUsers().getUserName().equals(userName))
                .map(entityModels::bookingEntity_Model).toList();
        if (CollectionUtils.isEmpty(bookings)){
            return ResponseEntity.status(HttpStatus.OK).body("No bookings");
        }
        return new ResponseEntity<>(bookings,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> cancelTicket(int bookingId) {
        if (bookingRepo.existsById(bookingId)) {
            this.bookingId = bookingId;
            return ResponseEntity.status(HttpStatus.OK).body("""
                Cancellation charges
                1. Before 1 hour of show time 50% charges will be applied.
                """);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking not found, please enter the correct booking ID");
    }


//    public ResponseEntity<?> cancelConfirm(){
//        if (bookingRepo.existsById(this.bookingId)){
//            BookingEntity booking = bookingRepo.findById(this.bookingId).get();
//            String sDate = booking.getDate();
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            LocalDate date = LocalDate.parse(sDate, dateFormatter);
//
//            LocalDate currentDate = LocalDate.now();
//
//            Optional<ShowsEntity> showsEntity = showsRepo.findAll().stream().filter(i ->i.getShowTime().equals(booking.getShowTime()) &&
//                    i.getDate().equals(booking.getDate()) && i.getTheatre().getTheatreName().equals(booking.getTheaterName()) &&
//                    i.getMovie().getMovieName().equals(booking.getMovieName())).findFirst();
//            if (showsEntity.isEmpty()){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("show not found");
//            }
//            if (date.isBefore(currentDate)) { // past date
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The booking show is already completed");
//            } else if (date.isEqual(currentDate)) { // current date
//                String sTime = booking.getShowTime();
//                LocalTime time = LocalTime.parse(sTime);
//                LocalTime currentTime = LocalTime.now();
//                System.out.println(currentTime);
//
//                if (time.isBefore(currentTime)) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The show has already started, you can't cancel the booking");
//                } else if (time.equals(currentTime)) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The show has already started, you can't cancel the booking");
//                } else {
//                    long minutesDifference = ChronoUnit.MINUTES.between(currentTime, time);
//
//                    if (minutesDifference<=60){
//                        double price = booking.getTotalPrice()*0.5;
//                        List<SeatsEntity> seats = seatRepo.findByShow(showsEntity.get());
//                        seats.forEach(i -> {
//                            if (booking.getSeats().contains(i.getSeatNumber())) {
//                                i.setBooked(false);
//                            }
//                        });
//                        seatRepo.saveAll(seats);
//                        bookingRepo.deleteById(this.bookingId);
//                        return ResponseEntity.status(HttpStatus.OK).body("Rupees " + price + " has been refund");
//
//                    }
//                    List<SeatsEntity> seats = seatRepo.findByShow(showsEntity.get());
//                    seats.forEach(i -> {
//                        if (booking.getSeats().contains(i.getSeatNumber())) {
//                            i.setBooked(false);
//                        }
//                    });
//                    seatRepo.saveAll(seats);
//                    bookingRepo.deleteById(this.bookingId);
//                    return ResponseEntity.status(HttpStatus.OK).body("Total amount has been refund");
//
//                }
//            } else {
//                List<SeatsEntity> seats = seatRepo.findByShow(showsEntity.get());
//                seats.forEach(i -> {
//                    if (booking.getSeats().contains(i.getSeatNumber())) {
//                        i.setBooked(false);
//                    }
//                });
//                seatRepo.saveAll(seats);
//                bookingRepo.deleteById(this.bookingId);
//                return ResponseEntity.status(HttpStatus.OK).body("Total amount has been refund");
//            }
//
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking not found please enter correct booking id");
//    }

    @Override
    public ResponseEntity<?> cancelConfirm() {
        Optional<BookingEntity> bookingOptional = bookingRepo.findById(this.bookingId);

        if (bookingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking not found. Please enter correct booking id");
        }

        BookingEntity booking = bookingOptional.get();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(booking.getDate(), dateFormatter);

        if (date.isBefore(currentDate)) { // checking Past date
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The booking show is already completed");
        }

        if (date.isEqual(currentDate)) { // Current date
            LocalTime currentTime = LocalTime.now();
            LocalTime showTime = LocalTime.parse(booking.getShowTime());

            if (showTime.isBefore(currentTime) || showTime.equals(currentTime)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The show has already started, you can't cancel the booking");
            } else if (ChronoUnit.MINUTES.between(currentTime, showTime) <= 60) {
                double price = booking.getTotalPrice() * 0.5;
                refundAndReleaseSeats(booking);
                return ResponseEntity.status(HttpStatus.OK).body("Rupees " + price + " has been refunded");
            }
        }


        refundAndReleaseSeats(booking);
        return ResponseEntity.status(HttpStatus.OK).body("Total amount has been refunded");
    }

    private void refundAndReleaseSeats(BookingEntity booking) {
        List<Integer> abc = new ArrayList<>();
        Optional<ShowsEntity> showsEntity = showsRepo.findAll().stream()
                .filter(i -> i.getShowTime().equals(booking.getShowTime()) &&
                        i.getDate().equals(booking.getDate()) &&
                        i.getTheatre().getTheatreName().equals(booking.getTheaterName()) &&
                        i.getMovie().getMovieName().equals(booking.getMovieName()))
                .findFirst();

        if (showsEntity.isEmpty()) {
            throw new IllegalArgumentException("Show not found");
        }

        List<SeatsEntity> seats = seatRepo.findByShow(showsEntity.get());
        seats.forEach(seat -> {
            if (booking.getSeats().contains(seat.getSeatNumber())) {
                seat.setBooked(false);
            }
        });
        seatRepo.saveAll(seats);
        Optional<UserEntity> user = userRepo.findAll().stream().filter(i -> i.getUserName().equals(this.userName)).findFirst();
        if (user.isPresent()){
            user.get().getBookings().remove(booking);
            userRepo.save(user.get());
        }
        bookingRepo.deleteById(booking.getBookingId());
    }

}
