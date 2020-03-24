package com.demo.jwt.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Used to search the username, password and GrantedAuthorities for given user. In this example we using hard coded credentials.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService
{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		if ("test_username".equals(username))
		{
			return new User("test_username", "$2y$12$m2Kg5X1V2wEB35pzsywbzOzWDLYl01CjBP0acdi21Vntf8b/EqYLK", new ArrayList<>());
		} else
		{
			throw new UsernameNotFoundException("User not found with the given username: " + username);
		}
	}

}
