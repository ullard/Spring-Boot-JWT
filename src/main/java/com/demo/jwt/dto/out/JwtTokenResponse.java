package com.demo.jwt.dto.out;

import java.io.Serializable;

/** Used to create the response object send to the client */
public class JwtTokenResponse implements Serializable
{

	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;

	public JwtTokenResponse(String jwttoken)
	{
		this.jwttoken = jwttoken;
	}

	public String getToken()
	{
		return this.jwttoken;
	}
}
