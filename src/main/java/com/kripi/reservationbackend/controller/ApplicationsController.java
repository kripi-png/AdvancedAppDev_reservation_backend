package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.config.ApiResponse;
import com.kripi.reservationbackend.config.UserInfoDetails;
import com.kripi.reservationbackend.model.Apartment;
import com.kripi.reservationbackend.model.Application;
import com.kripi.reservationbackend.model.RentEntry;
import com.kripi.reservationbackend.model.UserInfo;
import com.kripi.reservationbackend.repository.ApartmentRepository;
import com.kripi.reservationbackend.repository.ApplicationRepository;
import com.kripi.reservationbackend.repository.RentRepository;
import com.kripi.reservationbackend.utils.ApplicationStatus;
import com.kripi.reservationbackend.utils.ApplicationStatusUpdate;
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
    private final RentRepository rentRepository;

    @Autowired
    public ApplicationsController(ApplicationRepository applicationRepository, ApartmentRepository apartmentRepository, RentRepository rentRepository) {
        this.applicationRepository = applicationRepository;
        this.apartmentRepository = apartmentRepository;
        this.rentRepository = rentRepository;
    }

    @GetMapping("/{id}")
    // GET /applications/:id
    public @ResponseBody Optional<Application> getApartmentById(@PathVariable Integer id) {
        return applicationRepository.findById(id);
    }

    @PutMapping("/{id}")
    // PUT /applications:id
    public ResponseEntity<ApiResponse<Application>> updateApartmentById(@PathVariable Integer id, @RequestBody Application applicationData, Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();
            Optional<Application> possibleApplication = applicationRepository.findById(id);

            if (possibleApplication.isEmpty()) {
                System.out.println("No application exists with id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "No application exists with id"));
            }

            Application applicationInDB = possibleApplication.get();
            /* only allow updates from the user who created the appliction */
            if (!user.getUserId().equals(applicationInDB.getUserId())) {
                System.out.println("Applications: unauthorized update attempt");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Not authorized to update the application"));
            }

            /* cannot edit accepted or denied applications */
            if (applicationInDB.getStatus() != ApplicationStatus.PENDING) {
                System.out.println("Applications: cannot edit non-pending");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ApiResponse<>(false, "Cannot edit accepted or declined application"));
            }

            /* save application with the new message */
            applicationInDB.setMessage(applicationData.getMessage());
            applicationRepository.save(applicationInDB);
            return ResponseEntity.ok(new ApiResponse<>(true, "Application updated successfully"));
        } catch (Exception e) {
            System.out.println("Unknown exception when updating an application: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to update an application."));
        }
    }

    @DeleteMapping("/{id}")
    // DELETE /applications/:id
    public ResponseEntity<ApiResponse<Application>> deleteApplicationById(@PathVariable Integer id, Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();
            Optional<Application> possibleApplication = applicationRepository.findById(id);

            if (possibleApplication.isEmpty()) {
                System.out.println("No application exists with id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "No application exists with id"));
            }

            Application applicationInDB = possibleApplication.get();
            /* only allow deletion by the user who created the appliction */
            if (!user.getUserId().equals(applicationInDB.getUserId())) {
                System.out.println("Applications: unauthorized deletion attempt");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Not authorized to delete the application"));
            }
            applicationRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Application deleted successfully"));
        } catch (Exception e) {
            System.out.println("Unknown exception when deleting an application: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to delete an application."));
        }
    }

    @PostMapping("/{id}")
    // POST /applications/:id?status=<accept | decline>
    public ResponseEntity<ApiResponse<Application>> changeApplicationStatus(@PathVariable Integer id, @RequestParam ApplicationStatusUpdate status, Authentication authentication) {
        try {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
            UserInfo user = userInfoDetails.getUserInfo();
            Optional<Application> possibleApplication = applicationRepository.findById(id);

            if (possibleApplication.isEmpty()) {
                System.out.println("No application exists with id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "No application exists with id"));
            }

            Application applicationInDB = possibleApplication.get();
            if (applicationInDB.getStatus() != ApplicationStatus.PENDING) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ApiResponse<>(false, "Applications: cannot change status: status is not pending."));
            }

            /* only allow change if user is the owner of the apartment the application is for */
            if (!user.getUserId().equals(applicationInDB.getApartment().getOwner().getUserId())) {
                System.out.println("Applications: unauthorized status change attempt");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Not authorized to change the status of the application"));
            }
            if (status == ApplicationStatusUpdate.accept) {
                applicationInDB.setStatus(ApplicationStatus.ACCEPTED);
                /* create a rental entry and save it to database */
                RentEntry rentEntry = new RentEntry();
                /* set entry's user and apartment to those mentioned in the application */
                rentEntry.setApartment(applicationInDB.getApartment());
                rentEntry.setUser(applicationInDB.getUser());
                rentRepository.save(rentEntry);
            } else if (status == ApplicationStatusUpdate.decline) {
                applicationInDB.setStatus(ApplicationStatus.DECLINED);
            } else {
                throw new Exception("Applications: status change failed");
            }

            applicationRepository.save(applicationInDB);
            return ResponseEntity.ok(new ApiResponse<>(true, "Application status changed successfully"));
        } catch (Exception e) {
            System.out.println("Unknown exception when changing status of an application: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to change status of an application."));
        }
    }

    @GetMapping
    // GET /applications
    public @ResponseBody Iterable<Application> getAllApplications() {
        return applicationRepository.findAll();
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
            applicationData.setStatus(ApplicationStatus.PENDING);
            applicationData.setApartment(apartment.get());
            Application application = applicationRepository.save(applicationData);

            return ResponseEntity.ok(new ApiResponse<>(true, application));
        } catch (DataIntegrityViolationException e) {
            System.out.println("Missing data when creating an apartment: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Failed to create an apartment: one or more required field was missing a value."));
        } catch (Exception e) {
            System.out.println("Unknown exception when creating an application: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create an application."));
        }
    }
}
