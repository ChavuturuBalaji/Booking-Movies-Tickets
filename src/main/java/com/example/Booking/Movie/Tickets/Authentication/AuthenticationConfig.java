package com.example.Booking.Movie.Tickets.Authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthenticationConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.POST,"/user/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/user/movies").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/user/theaters").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/user/shows").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/user/seats").hasRole("USER")
                .requestMatchers(HttpMethod.POST,"/user/book").hasRole("USER")
                .requestMatchers(HttpMethod.POST,"/user/payment").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/user/myBookings").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/user/cancellation").hasRole("USER")
                .requestMatchers("/user/cancelConfirm").hasRole("USER")
                .requestMatchers(HttpMethod.POST,"/main/theaterRegister").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/main/allTheaters").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/main/deleteTheater").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/movie").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/addSeats").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/addMovies").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/update").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/getTheatre").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/addShows").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/addExistMovie").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.DELETE,"/admin/deleteMovieInTheater").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/getMovies").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.DELETE,"/admin/deleteShow").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.DELETE,"/admin/deleteSeats").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/balaji").hasRole("THEATERADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/getTheater/{theaterName}/{location}").hasRole("THEATERADMIN"));
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

