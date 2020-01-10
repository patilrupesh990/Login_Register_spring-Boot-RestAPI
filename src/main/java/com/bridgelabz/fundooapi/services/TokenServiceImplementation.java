package com.bridgelabz.fundooapi.services;

import com.bridgelabz.fundooapi.dto.Token;

public class TokenServiceImplementation implements TokenService{

	@Override
	public Token generateTokenAndPushIntoRedis(Integer userId, String tokenType) {
		return null;
	}

	@Override
	public Integer verifyUserToken(String userTokenId) {
		return null;
	}

	@Override
	public void destroyUserToken(Token accessToken, Token refreshToken) {
		
	}

}
