package com.kripi.reservationbackend.controller;

import com.kripi.reservationbackend.model.Apartment;
import com.kripi.reservationbackend.repository.ApartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/apartments")
public class ApartmentsController {

	private ApartmentRepository apartmentRepository;

    /*@Autowired
    public ApartmentsController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }*/

    @GetMapping
    public @ResponseBody Iterable<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

	@GetMapping("/{id}")
	public @ResponseBody Optional<Apartment> getApartmentById(@PathVariable Integer id) {
        return apartmentRepository.findById(id);
	}

	// POST /apartments
	@PostMapping
	public @ResponseBody String createNewApartment (
		@RequestParam Integer ownerId,
		@RequestParam Float rent,
		@RequestParam Float area,
		@RequestParam String type,
		@RequestParam(required = false) String street,
		@RequestParam(required = false) String city,
		@RequestParam(required = false) String postalCode,
		@RequestParam(required = false) String apartmentNumber,
		@RequestParam(required = false) Integer roomNormalCount,
		@RequestParam(required = false) Integer roomKitchenCount,
		@RequestParam(required = false) Integer roomBalconyCount,
		@RequestParam(required = false) Integer roomBathroomCount
	) {
		// @ResponseBody dictates that the response is of certain type and not a view name
		// @RequestParam defines accepted request parameters

		Apartment apartment = new Apartment();
		apartment.setOwnerId(ownerId);
		apartment.setRentAmount(rent);
		apartment.setArea(area);
		apartment.setType(type);
		// location
		apartment.setStreetName(street);
		apartment.setCityName(city);
		apartment.setPostalCode(postalCode);
		apartment.setApartmentNumber(apartmentNumber);
		// rooms
		apartment.setRoomBathroomCount(roomNormalCount);
		apartment.setRoomKitchenCount(roomKitchenCount);
		apartment.setRoomBalconyCount(roomBalconyCount);
		apartment.setRoomBathroomCount(roomBathroomCount);

		apartmentRepository.save(apartment);
		return "Saved";
	}
}