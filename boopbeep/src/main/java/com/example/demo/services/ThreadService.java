package com.example.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Thread;
import com.example.demo.repositories.ThreadRepository;
import com.example.demo.repositories.UserRepository;

@Transactional
@Component
public class ThreadService<T extends Thread> extends Service<T, Long> {
	
	@Autowired
	private ThreadRepository repo;
	
	public ThreadRepository getRepository() {
		return repo;
	}
	
	public List<Thread> findAll() {
		return repo.findAll();
	}
	
	public Thread findById(String username) {
		if (repo.findById(username) != null) {
			return repo.findById(username).get();
		}
		return null;
	}
	
	public void save(Thread user) {
		repo.saveAndFlush(user);
	}

}
