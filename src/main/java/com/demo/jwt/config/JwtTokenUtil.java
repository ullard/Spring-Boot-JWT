package com.demo.jwt.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.demo.jwt.model.User;
import com.demo.jwt.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/** Define the utilities method for generating and validating JWT token. */
@Component
public class JwtTokenUtil implements Serializable
{

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 30 * 60; // valid for half an hour

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private UserRepository userRepository;

	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token)
	{
		// parseClaimJws validates the token (UnsupportedJwtException, MalformedJwtException, SignatureException, ExpiredJwtException, IllegalArgumentException)
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public String generateToken(User user)
	{
		Map<String, Object> claims = new HashMap<>();

		return doGenerateToken(claims, user);
	}

	private String doGenerateToken(Map<String, Object> claims, User user)
	{
		String token = Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();

		saveTokenForUser(token, user);

		return token;
	}

	public Boolean validateToken(String token, UserDetails userDetails)
	{
		if (!(userRepository.findByUsername(userDetails.getUsername()).getToken() == null) && (userRepository.findByUsername(userDetails.getUsername()).getToken().equals(token)))
		{
			return true;
		}

		return false;
	}

	public void saveTokenForUser(String token, User user)
	{
		user.setToken(token);

		userRepository.save(user);
	}

}
