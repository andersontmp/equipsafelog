package com.equipsafelog.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.config.TokenService;
import com.equipsafelog.core.domain.User;
import com.equipsafelog.core.to.LoginCreate;
import com.equipsafelog.core.to.LoginForm;
import com.equipsafelog.core.to.LoginResponse;
import com.equipsafelog.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginForm loginForm) throws Exception {
		var usernamePassword = new UsernamePasswordAuthenticationToken(loginForm.login(), loginForm.password());
		var authenticate = authManager.authenticate(usernamePassword);

		String token = tokenService.generateToken((User) authenticate.getPrincipal());
		return ResponseEntity.ok(new LoginResponse(token, ((User) authenticate.getPrincipal()).getRole()));
	}

	@PostMapping("/create")
	public ResponseEntity create(@RequestBody LoginCreate loginCreate) {
		if (userRepository.findByLogin(loginCreate.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		String encryptedPass = new BCryptPasswordEncoder().encode(loginCreate.password());

		User user = new User();
		user.setLogin(loginCreate.login());
		user.setPassword(encryptedPass);
		user.setRole(loginCreate.role());

		userRepository.save(user);
		return ResponseEntity.ok().build();

	}
}
