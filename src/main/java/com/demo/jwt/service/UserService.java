package com.demo.jwt.service;

import com.demo.jwt.dto.in.UserDtoIn;
import com.demo.jwt.model.User;

public interface UserService
{
	public User registerNewUserAccount(UserDtoIn accountDto)  throws Exception;
}
