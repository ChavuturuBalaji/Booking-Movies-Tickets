package com.example.Booking.Movie.Tickets.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {
    private int movieId;
    private String movieName;
    private String duration;
    private String genre;
    @JsonIgnore
    private List<TheaterModel> theatres;
    @JsonIgnore
    private List<ShowsModel> shows;
}
