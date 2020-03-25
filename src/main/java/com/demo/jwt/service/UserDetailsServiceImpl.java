package com.demo.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.jwt.model.User;
import com.demo.jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	private UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findByUsername(username);

		if (user == null)
		{
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(user);
	}

}
