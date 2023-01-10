package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Post;
import com.example.demo.entities.User;

public interface PostRepository extends JpaRepository<Post, String> {
	
	public Optional<Post> findById ( String id );

}
