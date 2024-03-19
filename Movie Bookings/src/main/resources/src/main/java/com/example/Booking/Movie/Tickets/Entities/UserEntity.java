package com.example.Booking.Movie.Tickets.Entities;

import com.example.Booking.Movie.Tickets.CustomAnnotations.Mobile;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Valid
    @Id
    @NotBlank(message = "Username is mandatory")
    private String userName;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "mobile is mandatory")
    @Mobile
    @Column(unique = true)
    private String mobile;

    private String role;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "users")
    List<BookingEntity> bookings;
}

