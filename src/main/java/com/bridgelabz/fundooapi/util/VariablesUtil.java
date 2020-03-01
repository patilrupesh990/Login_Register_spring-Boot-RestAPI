package com.bridgelabz.fundooapi.util;

public class VariablesUtil {

	public static final String EMAIL_REGEX_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	public static final String REGISTRATION_EMAIL_SUBJECT = "Fundoo User Registration Activation Link";
	public static final String FORGOT_PASSWORD_SUBJECT = "Fundoo Reset password Verification Link";

	public static final String IP_ADDRESS = "http://192.168.0.1:";
	public static final String USER_ACTIVATE_URL = "/activate";
	public static final String FORGOT_PASSWORD_URL = "/reset-password";
	// environment variable
	public static final String SENDER_EMAIL_ID = System.getenv("username");
	public static final String SENDER_PASSWORD = System.getenv("password");
	public static final String ANGULAR_PORT_NUMBER = "4200";
	
	
	public static String link(String url, String token) {

		return url + "/" + token;
	}
}
