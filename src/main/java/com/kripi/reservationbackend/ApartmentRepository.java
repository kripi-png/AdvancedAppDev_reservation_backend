package com.kripi.reservationbackend;

import org.springframework.data.repository.CrudRepository;

import com.kripi.reservationbackend.entities.Apartment;

public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {
	
}