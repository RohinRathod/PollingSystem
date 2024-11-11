package com.online.pollingsystem;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User saveUser(User user, Model model) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	public boolean findByUsername(User user) {
		return userRepository.existsByUsername(user.getUsername());
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}