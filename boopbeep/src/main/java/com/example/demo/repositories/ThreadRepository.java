package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Thread;

public interface ThreadRepository extends JpaRepository<Thread, String> {
	
	public Optional<Thread> findById ( String id );

}
