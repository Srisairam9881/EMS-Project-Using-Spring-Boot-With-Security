package com.example.EMSWithLoginSystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.EMSWithLoginSystem.model.User;
import com.example.EMSWithLoginSystem.web.dto.UserRegistrationDto;


public interface UserService extends UserDetailsService {
	User save(UserRegistrationDto registrationDto);

}
