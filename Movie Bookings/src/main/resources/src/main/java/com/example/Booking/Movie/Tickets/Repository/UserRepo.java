package com.example.Booking.Movie.Tickets.Repository;

import com.example.Booking.Movie.Tickets.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,String > {
}
