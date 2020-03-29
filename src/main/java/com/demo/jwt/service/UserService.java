package com.demo.jwt.service;

import com.demo.jwt.dto.in.UserDtoIn;
import com.demo.jwt.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService
{
	public User findByUsername(String username) throws UsernameNotFoundException;
	
	public User registerNewUserAccount(UserDtoIn accountDto)  throws Exception;
}
