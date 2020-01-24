package com.bridgelabz.fundooapi.response;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Component
public class UserData {
	private int id;
	private String email;
	private String firstname;
	private String lastname;
	private String gender;
	private String phNo;
	private String dateOfBirth;
	private boolean activate;
	private int status;
	private String response;

	public UserData(int status,String response)
	{
		this.status=status;
		this.response=response;
	}
	public UserData() {}
}
