package com.kripi.reservationbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kripi.reservationbackend.repository")
public class ReservationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationBackendApplication.class, args);
	}
}
