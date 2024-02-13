package com.equipsafelog.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.domain.User;
import com.equipsafelog.core.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllCompanies() {
		return userService.getAllUsers();
	}

	@GetMapping(path = "/{id}")
	public User getCompany(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PostMapping
	public User saveCompany(@RequestBody User inputCompany) {
		return userService.saveUser(inputCompany);
	}
}
