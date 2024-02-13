package com.equipsafelog.core.service;

import java.util.List;

import com.equipsafelog.core.domain.User;

public interface UserService {

	List<User> getAllUsers();

	User getUser(Long id);

	User saveUser(User user);

}
