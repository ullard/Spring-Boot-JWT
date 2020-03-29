package com.demo.jwt.controller;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.jwt.config.JwtTokenUtil;
import com.demo.jwt.dto.in.UserDtoIn;
import com.demo.jwt.dto.out.JwtTokenResponse;
import com.demo.jwt.model.User;
import com.demo.jwt.service.UserServiceImpl;

@RestController
@CrossOrigin
public class JwtAuthenticationController
{

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> generateAuthenticationToken(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDtoIn authenticationRequest) throws Exception
	{
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		User user = userService.findByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(user);

		Cookie tokenCookie = new Cookie("token", token);
		tokenCookie.setMaxAge(30 * 60); // expires in half hour
		tokenCookie.setSecure(false);
		tokenCookie.setHttpOnly(true);

		response.addCookie(tokenCookie);

		return ResponseEntity.ok("Now you have the token in a cookie so you can make requests with that. Token: " + token);
	}

	private void authenticate(String username, String password) throws Exception
	{
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e)
		{
			throw new Exception("USER_DISABLED", e); // i18n
		} catch (BadCredentialsException e)
		{
			throw new Exception("INVALID_CREDENTIALS", e); // i18n
		}
	}
}
