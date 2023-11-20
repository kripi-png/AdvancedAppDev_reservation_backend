package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.config.AuthRequest;
import com.kripi.reservationbackend.service.JwtService;
import com.kripi.reservationbackend.model.UserInfo;
import com.kripi.reservationbackend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Hello, this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        try {
            /*
                service.loadUserByUsername throws UsernameNotFoundException if user does not exist
                therefore, we can cancel if it succeeds and create a new user if it throws
            */
            UserDetails userDetails = service.loadUserByUsername(userInfo.getUsername());
            System.out.println("Username already exists " + userDetails.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        } catch (UsernameNotFoundException e) {
            /* If loadUserByUsername threw UsernameNotFoundException, create new error*/
            System.out.println("New user submission: " + userInfo.getUsername());
            return service.addUser(userInfo);
        }
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("User not found with credentials");
        }
    }
}
