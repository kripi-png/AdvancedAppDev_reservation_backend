package com.kripi.reservationbackend;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kripi.reservationbackend.entities.Apartment;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {
}