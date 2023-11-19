package com.kripi.reservationbackend.apartment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {}