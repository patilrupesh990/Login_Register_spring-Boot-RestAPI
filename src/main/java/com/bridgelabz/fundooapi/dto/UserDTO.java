package com.bridgelabz.fundooapi.dto;

import lombok.Data;

@Data
public class UserDTO {
	private int id;

	
	private String email;

	
	private String firstname;

	private String lastname;

	private String password;

	private String gender;

	private String phNo;

	private String dateOfBirth;

	private String userName;

	private String creationTime;
	
	private String updateTime;

	private boolean activate;
}
