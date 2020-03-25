package com.demo.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
	@RequestMapping(
	{ "/greeting" })
	public String welcomePage()
	{
		return "Welcome!";
	}
}
