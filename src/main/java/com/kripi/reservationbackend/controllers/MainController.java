package com.kripi.reservationbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kripi.reservationbackend.ApartmentRepository;
import com.kripi.reservationbackend.entities.Apartment;

@Controller
@RequestMapping(path="/demo")
public class MainController {
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewUser (@RequestParam String name) {
		//	@ResponseBody dictates that the response is a string and not a view name
		// @RequestParam defines accepted request parameters
		
		Apartment apartment = new Apartment();
		apartment.setName(name);
		apartmentRepository.save(apartment);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Apartment> getAllUsers() {
		return apartmentRepository.findAll();
	}
}