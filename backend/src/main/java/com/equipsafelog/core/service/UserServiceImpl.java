package com.equipsafelog.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.User;
import com.equipsafelog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		if (user != null) {
			if (user.getId() != null) {
				User userPersist = getUser(user.getId());
				if (userPersist != null) {
					userPersist.setContact(user.getContact());
					userPersist.setName(user.getName());
					userPersist.setPassword(user.getPassword());
					userPersist.setUsername(user.getUsername());
					return userRepository.save(userPersist);
				}
			} else {
				return userRepository.save(user);
			}
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}
}
