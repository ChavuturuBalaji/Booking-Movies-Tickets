package com.example.Booking.Movie.Tickets.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    @NotBlank(message = "Movie Name is mandatory")
    private String movieName;
    @NotBlank(message = "Movie Name is mandatory")
    private String duration;
    @NotBlank(message = "Movie Name is mandatory")
    private String genre;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movies_theater_mapping", joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "theaterId"))
    private List<TheatreEntity> theatres;

    @JsonBackReference
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowsEntity> shows;
}
