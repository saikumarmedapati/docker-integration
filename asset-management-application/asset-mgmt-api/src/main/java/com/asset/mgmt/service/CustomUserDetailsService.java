package com.asset.mgmt.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asset.mgmt.entity.Users;
import com.asset.mgmt.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		
		return org.springframework.security.core.userdetails.User
		        .withUsername(user.getEmail())
		        .password(user.getPassword())
		        .authorities(new ArrayList<>())
		        .build();
	}

}
