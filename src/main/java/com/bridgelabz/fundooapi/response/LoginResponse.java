package com.bridgelabz.fundooapi.response;

import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class LoginResponse {
	
	private int status;
	private String response;
	private String token;
	
	public LoginResponse() {
	}
	public LoginResponse(int status , String token,String response) {
		super();
		this.status = status;
		this.response = response;
		this.token = token;
		
	}
	
	
}
