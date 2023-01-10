package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
	
	public Optional<Thread> findById ( Long id );

}
