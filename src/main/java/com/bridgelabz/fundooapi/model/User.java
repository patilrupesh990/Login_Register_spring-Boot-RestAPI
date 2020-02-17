package com.bridgelabz.fundooapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "EMAIL", unique = true)
	@NotNull
	private String email;

	@Column(name = "FIRST_NAME")
	@NotBlank(message = "First Name is mandatory")
	private String firstname;

	@Column(name = "LAST_NAME")
	private String lastname;

	@Column(name = "PASSWORD")
	@NotBlank(message = "Password is mandatory")
	private String password;

	@Column(name = "GENDER")
	@NotBlank(message = "Gender is mandatory")
	private String gender;

	@Column(name = "MO_No")
	@NotBlank(message = "contact is mandatory")
	@Length (min = 10,max = 10)
	private String phNo;

	@Column(name = "DOB")
	private String dateOfBirth;

	@Column(name = "USER_NAME")
	@NotBlank(message = "UserName is mandatory")
	private String userName;

	 @Column(name = "REGISTRATION_DATE")
	 @NotNull
	private String creationTime;
	
    @Column(name = "LAST_UPDATE")
	private String updateTime;

	@Column(name = "Verified")
	@NotNull
	private boolean activate;

	//DTO
}