package com.example.Booking.Movie.Tickets.Models;


import com.example.Booking.Movie.Tickets.CustomAnnotations.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowsModel {
    @JsonIgnore
    private int showId;
    private String showTime;
    private int totalSeats;
    private double ticket_Price;
    private String date;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MovieModel movie;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TheaterModel theatre;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<SeatsModel> seats;
}