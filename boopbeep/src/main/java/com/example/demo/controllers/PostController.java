package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Post;
import com.example.demo.entities.Thread;

import com.example.demo.services.ThreadService;

@RestController
public class PostController {

	@Autowired
	ThreadService<Thread> service;
	
	@PostMapping (value = "/posts")
	public ResponseEntity<?> createPost(@RequestBody String content) {
		LocalDateTime date = LocalDateTime.now();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Post post = new Post(username, content, date);
		Thread thread = new Thread(post);
		service.save(thread);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping (value = "/posts")
	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<Post>();
		for (Thread t : service.findAll()) {
			posts.add(t.getPost());
		}
		Collections.sort(posts);
		return posts;
	}
	
	@PutMapping (value = "/posts/like")
	public ResponseEntity<?> likePost(@RequestBody Post post) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		for (Thread t : service.findAll()) {
			if (t.getPost().getId().equals(post.getId())) {
				t.addOrRemoveLike(username);
				service.save(t);
				return new ResponseEntity<String>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping (value = "/threads/{id}")
	public Post getPost(@PathVariable String id) {
		for (Thread t : service.findAll()) {
			if (t.getId().equals(id)) {
				return t.getPost();
			}
		}
		return null;
	}
	
	
	
}
