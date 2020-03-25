package com.demo.jwt;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.jwt.dto.in.UserDtoIn;
import com.demo.jwt.service.UserServiceImpl;

@SpringBootApplication
public class JwtExampleApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(JwtExampleApplication.class, args);
	}

	// // // USER INIT \\ \\ \\

	@Autowired
	private UserServiceImpl userService;

	@Bean
	InitializingBean sendDatabase()
	{		
		UserDtoIn userDto = new UserDtoIn("username1", "password1");

		return () -> {
			userService.registerNewUserAccount(userDto);
		};
	}

}
