package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.config.ApiResponse;
import com.kripi.reservationbackend.config.UserInfoDetails;
import com.kripi.reservationbackend.model.Apartment;
import com.kripi.reservationbackend.model.Application;
import com.kripi.reservationbackend.model.UserInfo;
import com.kripi.reservationbackend.repository.ApartmentRepository;
import com.kripi.reservationbackend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/applications")
public class ApplicationsController {

    private final ApplicationRepository applicationRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApplicationsController(ApplicationRepository applicationRepository, ApartmentRepository apartmentRepository) {
        this.applicationRepository = applicationRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping
    // GET /applications
    public @ResponseBody Iterable<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/{id}")
    // GET /applications/:id
    public @ResponseBody Optional<Application> getApartmentById(@PathVariable Integer id) {
        return applicationRepository.findById(id);
    }

    @PostMapping
    // POST /applications
    public ResponseEntity<ApiResponse<Application>> createNewApplication(@RequestBody Application applicationData, Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();

            Optional<Apartment> apartment = apartmentRepository.findById(applicationData.getApartmentId());
            if (apartment.isEmpty()) {
                throw new DataIntegrityViolationException("No apartment exists with the id " + applicationData.getApplicationId());
            }

            applicationData.setUser(user);
            applicationData.setApartment(apartment.get());
            Application application = applicationRepository.save(applicationData);
            return ResponseEntity.ok(new ApiResponse<>(true, application));
        } catch (DataIntegrityViolationException e) {
            System.out.println("Missing data when creating an apartment: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Failed to create an apartment: one or more required field was missing a value: idfk"));
        } catch (Exception e) {
            System.out.println("Unknown exception when creating an application: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create an application."));
        }
    }
}
