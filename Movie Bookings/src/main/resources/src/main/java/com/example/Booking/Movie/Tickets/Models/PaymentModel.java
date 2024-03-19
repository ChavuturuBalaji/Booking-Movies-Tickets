package com.example.Booking.Movie.Tickets.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private int paymentId;
    private String creditCardNumber;
    @JsonIgnore
    private BookingModel bookingModel;
}
