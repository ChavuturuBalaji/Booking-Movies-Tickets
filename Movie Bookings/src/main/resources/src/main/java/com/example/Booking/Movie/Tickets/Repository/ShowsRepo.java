package com.example.Booking.Movie.Tickets.Repository;

import com.example.Booking.Movie.Tickets.Entities.MovieEntity;
import com.example.Booking.Movie.Tickets.Entities.ShowsEntity;
import com.example.Booking.Movie.Tickets.Entities.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowsRepo extends JpaRepository<ShowsEntity, Integer> {
    List<ShowsEntity> findByMovie(MovieEntity movie);

    List<ShowsEntity> findByTheatreAndMovie(TheatreEntity theatre, MovieEntity movie);
}

