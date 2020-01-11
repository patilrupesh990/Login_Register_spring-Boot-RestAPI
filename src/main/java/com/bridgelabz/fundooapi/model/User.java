package com.bridgelabz.fundooapi.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Entity
@Table(name = "user")
@Data
@ToString
@EqualsAndHashCode

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "password")
	private String password;

	@Column(name = "gender")
	private String gender;

	@Column(name = "phNo")
	private String phNo;

	@Column(name = "dob")
	private String dateOfBirth;

	@Column(name = "userName")
	private String userName;

	@Column(name = "Registration_date")
	private LocalDate registration_date;

	@Column(name = "active")
	private String activate;
	public User(int id, String email, String firstname, String lastname, String password, String gender, String phNo,
			String dob, String activate, String userName,LocalDate registration_date) {
		super();
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.gender = gender;
		this.phNo = phNo;
		this.dateOfBirth = dob;
		this.activate = activate;
		this.userName = userName;
		this.registration_date=registration_date;
	}
	User()
	{}

}