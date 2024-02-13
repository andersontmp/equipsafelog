package com.equipsafelog.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.User;
import com.equipsafelog.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userResository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User doLogin(String username, String password) throws Exception {
    	User user = userResository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid User or Password");
        }
        return user;
    }
}
