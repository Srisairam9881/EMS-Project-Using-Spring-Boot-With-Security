package com.example.EMSWithLoginSystem.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EMSWithLoginSystem.model.Role;
import com.example.EMSWithLoginSystem.model.User;
import com.example.EMSWithLoginSystem.repository.UserRepository;
import com.example.EMSWithLoginSystem.web.dto.UserRegistrationDto;



@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	//@Autowired
	private BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
	
	

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user=new User(
				           registrationDto.getFirstName(),registrationDto.getLastName(),registrationDto.getGender(),
				           registrationDto.getDob(),registrationDto.getPhoneNo(),registrationDto.getEmail(),
				           passwordEncoder.encode(registrationDto.getPassword()),Arrays.asList(new Role("ROLE_USER")));      
				         
		return userRepository.save(user);
	}

}
