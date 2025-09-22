package com.example.cadettsplitterssushi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/api/wigellsushi/dishes",
                                        "/api/wigellsushi/rooms"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/wigellsushi/bookroom",
                                        "/api/wigellsushi/cancelbooking",
                                        "/api/wigellsushi/mybookings"
                                ).hasRole("USER")
                                .requestMatchers(
                                        "/api/wigellsushi/listcancelled",
                                        "/api/wigellsushi/listupcoming",
                                        "/api/wigellsushi/listpast",
                                        "/api/wigellsushi/add-dish",
                                        "/api/wigellsushi/remdish/{id}",
                                        "/api/wigellsushi/addroom",
                                        "/api/wigellsushi/updateroom"
                                ).hasRole("ADMIN")
                                .anyRequest().permitAll() //TODO: Only use while developing, remove later
                );
        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager userDetailsManager(){
        UserDetails lyndseyfox = User
                .withUsername("lynseyfox")
                .password("Lf123!")
                .roles("ADMIN")
                .build();
        UserDetails christofferfrisk = User
                .withUsername("christofferfrisk")
                .password("Cf123!")
                .roles("USER")
                .build();
        UserDetails niklaseinarsson = User
                .withUsername("niklaseinarsson")
                .password("Ne123!")
                .roles("USER")
                .build();
        UserDetails benjaminportsmouth = User
                .withUsername("benjaminportsmouth")
                .password("Bp123!")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(lyndseyfox, christofferfrisk, niklaseinarsson, benjaminportsmouth);
    }
}
