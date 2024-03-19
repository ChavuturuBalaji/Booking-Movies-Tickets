package com.example.Booking.Movie.Tickets.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminModel {
    private String userName;
    private String password;
    private String name;
    private String mobile;
    private String role;
}
