package com.example.Booking.Movie.Tickets.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    @NotBlank(message = "Movie Name is mandatory")
    private String movieName;
    @NotBlank(message = "Theater Name is mandatory")
    private String theaterName;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @NotBlank(message = "show time is mandatory")
    private String showTime;
    @NotBlank(message = "date is mandatory")
    private String date;
    private double totalPrice;
    @NotEmpty(message = "seats not empty")
    private List<Integer> seats;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity users;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private PaymentEntity payment;
}
