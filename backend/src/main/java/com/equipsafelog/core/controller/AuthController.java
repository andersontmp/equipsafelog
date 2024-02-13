package com.equipsafelog.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.domain.User;
import com.equipsafelog.core.service.AuthService;
import com.equipsafelog.core.to.LoginForm;

@RestController
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public User fazerLogin(@RequestBody LoginForm loginForm) throws Exception {
		return authService.doLogin(loginForm.getUsername(), loginForm.getPassword());
	}
}
