package com.kripi.reservationbackend.repository;

import com.kripi.reservationbackend.model.Application;
import com.kripi.reservationbackend.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByEmail(String email);

    @Query("SELECT a FROM Application a WHERE a.userId = :userId")
    List<Application> findUserSentApplications(@Param("userId") Integer userId);

    @Query("SELECT a FROM Application a " +
            "JOIN fetch a.apartment ap " +
            "WHERE ap.owner.userId = :userId")
    List<Application> findApplicationsForUserApartments(@Param("userId") Integer userId);
}
