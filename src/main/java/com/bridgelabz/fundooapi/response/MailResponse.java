package com.bridgelabz.fundooapi.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class MailResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;

	private String subject;

	private String message;

	public MailResponse(String email, String subject, String message) {
		this.email = email;
		this.subject = subject;
		this.message = message;
	}
	public MailResponse() {
	
	}
}
