package com.bridgelabz.fundooapi.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundooapi.model.User;

import lombok.Data;

@Component
@Data
public class UserResponse {
	
	private int status;
	private String response;
	private Object object;
	private List<User> list;
	
	public UserResponse(int status, String response) {
		super();
		this.status = status;
		this.response = response;
	}
	public UserResponse(int status, String response, Object object) {
		super();
		this.status = status;
		this.response = response;
		this.object = object;
	}
	UserResponse()
	{}
	public String message(String msg)
	{
		return msg;
	}
	public UserResponse(int status, String response, List<User> list) {
		super();
		this.status = status;
		this.response = response;
		this.list=list;	
		}
}
