package com.kripi.reservationbackend.repository;

import com.kripi.reservationbackend.model.Apartment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {}