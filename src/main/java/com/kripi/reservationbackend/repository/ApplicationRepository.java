package com.kripi.reservationbackend.repository;

import com.kripi.reservationbackend.model.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer> {}

