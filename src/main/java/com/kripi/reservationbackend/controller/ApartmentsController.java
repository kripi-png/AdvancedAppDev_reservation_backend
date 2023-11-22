package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.config.ApiResponse;
import com.kripi.reservationbackend.config.UserInfoDetails;
import com.kripi.reservationbackend.model.Apartment;
import com.kripi.reservationbackend.model.UserInfo;
import com.kripi.reservationbackend.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/apartments")
public class ApartmentsController {

	private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentsController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping
	// GET /apartments
    public @ResponseBody Iterable<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

	@GetMapping("/{id}")
	// GET /apartments/:id
	public @ResponseBody Optional<Apartment> getApartmentById(@PathVariable Integer id) {
        return apartmentRepository.findById(id);
	}

	@PostMapping
	// POST /apartments
	public ResponseEntity<ApiResponse<Apartment>> createNewApartment (@RequestBody Apartment apartmentData, Authentication authentication) {
		try {
			/* Set current user as the owner for the apartment */
			UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
			UserInfo user = userInfoDetails.getUserInfo();
			apartmentData.setOwner(user);
			Apartment apartment = apartmentRepository.save(apartmentData);
			return ResponseEntity
					.ok(new ApiResponse<>(true, apartment));
		} catch (DataIntegrityViolationException e) {
			String missingFields = "";
			if (apartmentData.getOwner() == null) missingFields = "ownerId";
			else if (apartmentData.getRentAmount() == null) missingFields = "rentAmount";
			else if (apartmentData.getArea() == null) missingFields = "area";
			else if (apartmentData.getApartmentType() == null) missingFields = "apartmentType";

			System.out.println("Missing data when creating an apartment: " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse<>(false, "Failed to create an apartment: one or more required field was missing a value: " + missingFields));
		} catch (Exception e) {
			System.out.println("Unknown exception when creating an apartment: " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, "Failed to create an apartment."));
		}
	}
}