package com.project.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainCtrl {

	@RequestMapping("/")
	public String home() {
		return hello();
	}

	@RequestMapping(value = "/hello")
	public String hello() {
		return "Hello. This is our EECS 4413 project.\nProject members are:\n1. Amir\n2. Bader\n3. Rachel\n4. Priyank";
	}

}
