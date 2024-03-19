package com.example.Booking.Movie.Tickets.Entities;

import com.example.Booking.Movie.Tickets.CustomAnnotations.Mobile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminEntity {
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

    @NotBlank(message = "role is mandatory")
    private String role;

    @OneToOne(mappedBy = "admin")
    private TheatreEntity theatre;
}
