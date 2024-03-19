package com.example.Booking.Movie.Tickets.Repository;

import com.example.Booking.Movie.Tickets.Entities.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepo extends JpaRepository<TheatreEntity,Integer> {
    TheatreEntity findByTheatreNameAndLocation(String name, String location);
}
