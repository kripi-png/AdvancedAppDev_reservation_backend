package com.kripi.reservationbackend.service;

import com.kripi.reservationbackend.config.UserInfoDetails;
import com.kripi.reservationbackend.model.UserInfo;
import com.kripi.reservationbackend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username);
        System.out.println("UserInfoService.java USER " + userDetail.toString());

        // convert userDetail to UserDetails
        UserInfoDetails userInfoDetails = userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        System.out.println("UserInfoService.java USER INFO DETAILS username " + userInfoDetails.getUsername() + " password " + userInfoDetails.getPassword());
        return userInfoDetails;
    }

    public String addUser(UserInfo userInfo) {
        // replace password with encoded password
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully";
    }
}
