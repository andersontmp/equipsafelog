package com.equipsafelog.core.config;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.equipsafelog.core.domain.User;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.create().withIssuer("auth-api").withSubject(user.getLogin())
				.withExpiresAt(Instant.now().plusSeconds(86400L)).sign(algorithm);
	}

	public String validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
	}
}
