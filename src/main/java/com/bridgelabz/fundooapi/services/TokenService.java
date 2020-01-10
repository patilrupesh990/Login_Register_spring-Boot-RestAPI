package com.bridgelabz.fundooapi.services;

import com.bridgelabz.fundooapi.dto.Token;

public interface TokenService 
{
	public Token generateTokenAndPushIntoRedis(Integer userId, String tokenType);

	public Integer verifyUserToken(String userTokenId);
	
	public void destroyUserToken(Token accessToken, Token refreshToken);
}
