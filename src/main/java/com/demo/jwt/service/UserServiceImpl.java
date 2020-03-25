package com.demo.jwt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.jwt.dto.in.UserDtoIn;
import com.demo.jwt.model.Role;
import com.demo.jwt.model.User;
import com.demo.jwt.repository.RoleRepository;
import com.demo.jwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	private final String USER_ROLE = "ROLE_USER";

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public User registerNewUserAccount(UserDtoIn accountDto) throws Exception
	{
		User userCheck = userRepository.findByUsername(accountDto.getUsername());
		
		if (userCheck != null)
		{
			throw new Exception("A user already exists with the given username.");
		}
		
		User user = new User();

		ArrayList<String> roles = new ArrayList<>();

		roles.add(USER_ROLE);

		user.setUsername(accountDto.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));

		user.setEnabled(true);

		for (int i = 0; i < roles.size(); i++)
		{
			Role role = roleRepository.findByName(roles.get(i));

			if (role != null)
			{
				user.addRole(role);
			} else
			{				
				user.addRole(new Role(roles.get(i)));
			}
		}

		return userRepository.save(user);
	}

}
