package com.bridgelabz.fundooapi.util;

import com.bridgelabz.fundooapi.model.Token;

public interface TokenService 
{
	public Token generateTokenAndPushIntoRedis(Integer userId, String tokenType);

	public Integer verifyUserToken(String userTokenId);

	public void destroyUserToken(Token accessToken, Token refreshToken);
}