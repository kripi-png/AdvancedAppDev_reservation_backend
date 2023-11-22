package com.kripi.reservationbackend.model;

import com.kripi.reservationbackend.utils.ApartmentType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="apartments")
@Data
public class Apartment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "apartmentId", nullable=false)
    private Integer apartmentId;
	
	@Column(name="ownerId", nullable=false)
	private Integer ownerId;
	
	@Column(name="rentAmount", nullable=false)
	private Float rentAmount; // in euros
	
	@Column(name="area", nullable=false)
	private Float area; // m2

	@Enumerated(EnumType.STRING)
	@Column(name="apartmentType", nullable=false)
	private ApartmentType apartmentType;
	
	/* ADDRESS COLUMNS */
	@Column(name="streetName", length=45)
	private String streetName;
	
	@Column(name="cityName", length=45)
	private String cityName;
	
	@Column(name="postalCode", length=10)
	private String postalCode;
	
	@Column(name="apartmentNumber", length=10)
	private String apartmentNumber;
	
	/* ROOMS */
	@Column(name="roomNormalCount")
	private Integer roomNormalCount;
	
	@Column(name="roomKitchenCount")
	private Integer roomKitchenCount;
	
	@Column(name="roomBalconyCount")
	private Integer roomBalconyCount;
	
	@Column(name="roomBathroomCount")
	private Integer roomBathroomCount;

	@Column(name="description")
	private String description;
}