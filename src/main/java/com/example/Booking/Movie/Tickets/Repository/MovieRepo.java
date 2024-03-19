package com.example.Booking.Movie.Tickets.Repository;

import com.example.Booking.Movie.Tickets.Entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<MovieEntity, Integer> {
    Optional<MovieEntity> findByMovieName(String movieName);
}
