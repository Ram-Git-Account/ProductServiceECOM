package com.ecom.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProductSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Every request must be authenticated
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )

                // Enable OAuth2 Login (Authorization Code Flow)
                .oauth2Login();

        return http.build();
    }
}
