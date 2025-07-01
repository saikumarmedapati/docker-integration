package com.asset.mgmt.service;

import com.asset.mgmt.entity.Users;
import com.asset.mgmt.util.LoginResponse;

public interface AuthService {
	
	LoginResponse register(Users user);
	
	LoginResponse login(Users user);
}
