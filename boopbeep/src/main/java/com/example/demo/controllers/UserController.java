package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService<User> userService;
	
	@SuppressWarnings({ "rawtypes" })
	@PostMapping (value = "/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (userService.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		} 
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<Role>());
		user.addRole(Role.User);
		user.setEnabled(true);
		userService.save(user);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping (value = "/user")
	public User getAuthenticatedUser() {
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return user;
	}

}
