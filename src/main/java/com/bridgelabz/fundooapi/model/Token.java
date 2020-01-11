package com.bridgelabz.fundooapi.dto;

import org.springframework.stereotype.Component;

@Component("token")
public class Token 
{
	String userId;
	String tokenName;
	String tokenValue;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getTokenValue() {
		return tokenValue;
	}
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	@Override
	public String toString() {
		return "Token [userId=" + userId + ", tokenName=" + tokenName + ", tokenValue=" + tokenValue + "]";
	}
	public Token(String userId) {
		this.userId = userId;
	}
	public Token(String userId, String tokenName, String tokenValue) {
		super();
		this.userId = userId;
		this.tokenName = tokenName;
		this.tokenValue = tokenValue;
	}
	public Token() {

	}


}