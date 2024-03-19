package com.example.Booking.Movie.Tickets.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    private int seatNumber;
    private boolean booked;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "showId")
    private ShowsEntity show;
}
