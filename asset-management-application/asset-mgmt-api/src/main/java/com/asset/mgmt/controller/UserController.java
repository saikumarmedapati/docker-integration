package com.asset.mgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.mgmt.entity.Users;
import com.asset.mgmt.service.UserService;

@RestController
@RequestMapping("/edit-user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(
	        @PathVariable Integer id,
	        @RequestBody Users user) {

	    // Set the ID from path to the incoming user object
	    user.setId(id);

	    boolean updated = userService.updateUser(user);
	    if (updated) {
	        return ResponseEntity.ok("User updated successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or ID missing");
	    }
	}

}
