package com.kripi.reservationbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationId", nullable = false)
    private Integer applicationId;

    @Column(name = "message", nullable = false)
    private String message;

    /*@OneToOne(fetch = FetchType.EAGER)*/
    @Column(name = "userId", nullable = false)
    private Integer userId;

    /*@OneToOne(fetch = FetchType.EAGER)*/
    @Column(name = "apartmentId", nullable = false)
    private Integer apartmentId;
}
