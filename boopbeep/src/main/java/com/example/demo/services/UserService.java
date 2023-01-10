package com.example.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Transactional
@Primary
@Component
public class UserService<T extends User> extends Service<T, String> {
	
	@Autowired
	private UserRepository repo;
	
	public UserRepository getRepository() {
		return repo;
	}
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	public void save(User user) {
		repo.saveAndFlush(user);
	}

}
