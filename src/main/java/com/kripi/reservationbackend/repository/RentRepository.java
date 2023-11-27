package com.kripi.reservationbackend.repository;

import com.kripi.reservationbackend.model.RentEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends CrudRepository<RentEntry, Integer> {}
