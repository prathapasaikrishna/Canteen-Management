package com.canteen.management.config;

import com.canteen.management.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(

                                "/",
                                "/student/login",
                                "/student/register",
                                "/student/forgot-password",
                                "/student/reset-password",
                                "/student/verify-otp",
                                "/student/google-login",
                                "/food/**",

                                "/notification/test",
                                "/notification/broadcast",

                                "/wallet/**",
                                "/wallet/create-order",
                                "/coupon/**",
                                "/organization/**",
                                "/branch/**",
                                "/inventory/**",
                                "/employee/**",
                                "/superadmin/**",
                                "/error",
                                "/uploads/**"


                        ).permitAll()

                        // Everything else requires JWT
                        .anyRequest().authenticated()

                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

}