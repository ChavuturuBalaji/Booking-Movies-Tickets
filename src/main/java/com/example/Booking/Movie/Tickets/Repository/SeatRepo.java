package com.example.Booking.Movie.Tickets.Repository;

import com.example.Booking.Movie.Tickets.Entities.SeatsEntity;
import com.example.Booking.Movie.Tickets.Entities.ShowsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<SeatsEntity,Integer> {
    List<SeatsEntity> findByShow(ShowsEntity show);
}
