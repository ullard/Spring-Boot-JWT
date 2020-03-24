package com.demo.jwt.model;

import java.io.Serializable;

/** Used to create the response object send to the client */
public class JwtResponse implements Serializable
{

	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;

	public JwtResponse(String jwttoken)
	{
		this.jwttoken = jwttoken;
	}

	public String getToken()
	{
		return this.jwttoken;
	}
}
