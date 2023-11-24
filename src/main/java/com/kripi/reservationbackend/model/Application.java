package com.kripi.reservationbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kripi.reservationbackend.utils.ApplicationStatus;
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

    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "userId", insertable = false, updatable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private UserInfo user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "apartmentId", insertable = false, updatable = false)
    private Integer apartmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartmentId", nullable = false)
    private Apartment apartment;
}
