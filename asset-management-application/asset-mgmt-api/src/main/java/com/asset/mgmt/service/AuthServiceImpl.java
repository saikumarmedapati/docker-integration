package com.asset.mgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.mgmt.entity.Users;
import com.asset.mgmt.exception.DatabaseException;
import com.asset.mgmt.exception.EmailAlreadyExistsException;
import com.asset.mgmt.exception.InternalServerException;
import com.asset.mgmt.exception.InvalidCredentialsException;
import com.asset.mgmt.exception.InvalidRequestException;
import com.asset.mgmt.repo.UserRepository;
import com.asset.mgmt.util.JwtUtil;
import com.asset.mgmt.util.LoginResponse;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsImpl;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Override
	public LoginResponse register(Users user) {
	    try {
	        // Basic validation
	        if (user.getEmail() == null || user.getPassword() == null ||
	            user.getEmail().isBlank() || user.getPassword().isBlank()) {
	            throw new InvalidRequestException("Email and password are required..!");
	        }

	        // Check for existing email
	        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
	            throw new EmailAlreadyExistsException("Email already exists..");
	        }

	        // Encrypt and save user
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        Users savedUser = userRepo.save(user);

	        // Generate JWT
	        String token = jwtUtil.generateToken(userDetailsImpl.loadUserByUsername(user.getEmail()));

	        return new LoginResponse(token, savedUser.getId(), savedUser.getEmail());

	    } catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Database error occurred while saving user..");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InternalServerException("Something went wrong, please try again..");
	    }
	}

	@Override
	public LoginResponse login(Users user) {
	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
	        );

	        Users authentUser = userRepo.findByEmail(user.getEmail())
	                .orElseThrow(() -> new UsernameNotFoundException("User not found..!"));

	        UserDetails userDetails = userDetailsImpl.loadUserByUsername(user.getEmail());

	        String token = jwtUtil.generateToken(userDetails);
	        Integer userId = (userRepo.findByEmail(user.getEmail()).get()).getId();

	        return new LoginResponse(token, userId, authentUser.getEmail());

	    } catch (BadCredentialsException e) {
	        throw new InvalidCredentialsException("Invalid Email or password");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InternalServerException("Something went wrong, please try again");
	    }
	}


}
