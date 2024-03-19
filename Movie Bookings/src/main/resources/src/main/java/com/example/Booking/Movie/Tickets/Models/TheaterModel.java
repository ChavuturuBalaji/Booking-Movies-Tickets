package com.example.Booking.Movie.Tickets.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TheaterModel {
    @JsonIgnore
    private int theatreId;
    private String theatreName;
    private String location;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AdminModel admin;
    @JsonIgnore
    private List<MovieModel> movies;
    @JsonIgnore
    private List<ShowsModel> shows;
}
