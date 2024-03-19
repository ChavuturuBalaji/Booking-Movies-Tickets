package com.example.Booking.Movie.Tickets.Authentication;

import com.example.Booking.Movie.Tickets.Entities.AdminEntity;
import com.example.Booking.Movie.Tickets.Entities.UserEntity;
import com.example.Booking.Movie.Tickets.Repository.AdminRepo;
import com.example.Booking.Movie.Tickets.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AdminRepo adminRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepo.existsById(username)){
            Optional<UserEntity> users = userRepo.findById(username);
            return users.map(UserInfo::new).orElseThrow(() ->  new UsernameNotFoundException("user not found " + username));
        }

        Optional<AdminEntity> admin = adminRepo.findById(username);
        return admin.map(AdminInfo::new).orElseThrow(() ->  new UsernameNotFoundException("admin not found " + username));
    }
}
