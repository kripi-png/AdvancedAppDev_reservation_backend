package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.config.AuthRequest;
import com.kripi.reservationbackend.config.UserInfoDetails;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        /* Accepts username, password, and list of roles
        * if username already exists in database, cancel
        * otherwise save user to database */
        try {
            /* service.loadUserByUsername throws UsernameNotFoundException if user does not exist
            * therefore, we can cancel if it succeeds and create a new user if it throws */
            UserDetails userDetails = service.loadUserByUsername(userInfo.getEmail());
            System.out.println("Username already exists " + userDetails.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        } catch (UsernameNotFoundException e) {
            /* If loadUserByUsername threw UsernameNotFoundException, create new error*/
            System.out.println("New user submission: " + userInfo.getEmail());
            if (userInfo.getRoles() == null) {
                userInfo.setRoles("ROLE_USER");
            }
            return service.addUser(userInfo);
        }
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        /* Returns a JWT token for provided username and password */
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", ((UserInfoDetails) authentication.getPrincipal()).getId());
            return jwtService.generateToken(authRequest.getEmail(), claims);
        } else {
            throw new UsernameNotFoundException("User not found with credentials");
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

}
