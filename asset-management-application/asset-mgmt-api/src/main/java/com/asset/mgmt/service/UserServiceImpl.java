package com.asset.mgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.mgmt.entity.Users;
import com.asset.mgmt.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passowrdEncoder;

	@Override
	public boolean updateUser(Users user) {
	    if (user.getId() == null) return false;

	    return userRepo.findById(user.getId()).map(existingUser -> {
	        existingUser.setUserName(user.getUserName());
	        existingUser.setEmail(user.getEmail());
	        existingUser.setPassword(passowrdEncoder.encode(user.getPassword()));
	        userRepo.save(existingUser);
	        return true;
	    }).orElse(false);
	}
	
}
