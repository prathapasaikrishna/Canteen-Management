package com.canteen.management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtil jwtUtil;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("Request URI="+request.getRequestURI());


        String authHeader = request.getHeader("Authorization");


        if(authHeader != null && authHeader.startsWith("Bearer ")) {


            String token = authHeader.substring(7);


            try {

                String email = jwtUtil.extractEmail(token);


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                null
                        );


                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);


            } catch(Exception e) {

                System.out.println("Invalid JWT Token");

            }

        }


        filterChain.doFilter(request,response);

    }
}