package com.example.Booking.Movie.Tickets.Repository;

import com.example.Booking.Movie.Tickets.Entities.TemporaryBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempBookingRepo extends JpaRepository<TemporaryBookingEntity,Integer> {
    Optional<TemporaryBookingEntity> findFirstByUserName(String userName);
}
