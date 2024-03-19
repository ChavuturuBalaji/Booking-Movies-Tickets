package com.example.Booking.Movie.Tickets.Entities;

import com.example.Booking.Movie.Tickets.CustomAnnotations.Credit_Debit_Card;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    @Credit_Debit_Card
    private String creditCardNumber;

    @OneToOne(mappedBy = "payment")
    private BookingEntity booking;
}
