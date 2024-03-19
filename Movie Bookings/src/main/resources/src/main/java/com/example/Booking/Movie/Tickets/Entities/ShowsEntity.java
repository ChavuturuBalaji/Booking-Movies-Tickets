package com.example.Booking.Movie.Tickets.Entities;

import com.example.Booking.Movie.Tickets.CustomAnnotations.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showId;
    private String showTime;
    private int totalSeats;
    private double ticket_Price;
    private String date;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movie;


    @ManyToOne
    @JoinColumn(name = "theaterId")
    private TheatreEntity theatre;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private Set<SeatsEntity> seats;
}
