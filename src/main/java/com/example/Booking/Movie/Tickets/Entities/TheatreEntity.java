package com.example.Booking.Movie.Tickets.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class TheatreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int theatreId;
    @NotBlank(message = "Theater Name is mandatory")
    private String theatreName;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movies_theater_mapping", joinColumns = @JoinColumn(name = "theaterId"),
            inverseJoinColumns = @JoinColumn(name = "movieId"))
    private List<MovieEntity> movies;

    @JsonIgnore
    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<ShowsEntity> shows;

}
