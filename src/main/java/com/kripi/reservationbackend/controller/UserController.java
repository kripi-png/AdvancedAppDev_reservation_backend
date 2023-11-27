package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.config.ApiResponse;
import com.kripi.reservationbackend.config.UserInfoDetails;
import com.kripi.reservationbackend.model.Apartment;
import com.kripi.reservationbackend.model.Application;
import com.kripi.reservationbackend.model.UserInfo;
import com.kripi.reservationbackend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserController(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/me/applications")
    public ResponseEntity<ApiResponse<Map<String, List<Application>>>> getMyApplications(Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();

            List<Application> userSentApplications = userInfoRepository.findUserSentApplications(user.getUserId());
            List<Application> applicationsForUserApartments = userInfoRepository.findApplicationsForUserApartments(user.getUserId());

            Map<String, List<Application>> allApplications = new HashMap<>();
            allApplications.put("userSentApplications", userSentApplications);
            allApplications.put("applicationsForUserApartments", applicationsForUserApartments);
            return ResponseEntity.ok(new ApiResponse<>(true, allApplications));
        } catch (Exception e) {
            System.out.println("Unknown exception when fetching user applications: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch user applications."));
        }
    }

    @GetMapping("/me/apartments")
    // GET /users/me/apartments
    public ResponseEntity<ApiResponse<List<Apartment>>> getMyApartments(Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();
            List<Apartment> apartments = userInfoRepository.findUserOwnedApartments(user.getUserId());

            return ResponseEntity.ok(new ApiResponse<>(true, apartments));
        } catch (Exception e) {
            System.out.println("Unknown exception when fetching user apartments: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch user apartments."));
        }
    }

    @GetMapping("/me/renting_pay")
    // GET /users/me/renting_pay
    public ResponseEntity<ApiResponse<List<Apartment>>> getMyRentedApartments(Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();
            List<Apartment> apartments = userInfoRepository.findApartmentsRentedByUser(user.getUserId());

            return ResponseEntity.ok(new ApiResponse<>(true, apartments));
        } catch (Exception e) {
            System.out.println("Unknown exception when fetching user rented (paying) apartments: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch rented apartments"));
        }
    }

    /*@GetMapping("/me/renting_gain")
    public String getMyEarningApartments(Authentication authentication) {
        try {

        } catch (Exception e) {

        }
        return "";
    }*/
}
