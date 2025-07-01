package com.asset.mgmt.util;

public class LoginResponse {
	
    private String token;
    private Integer userId;
    private String email;
    
	public LoginResponse(String token, Integer userId, String email) {
		super();
		this.token = token;
		this.userId = userId;
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    
}

