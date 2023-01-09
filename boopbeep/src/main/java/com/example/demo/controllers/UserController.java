package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;

@RestController
public class UserController {
	
	@PostMapping (value = "/users")
	public ResponseBody createUser(@RequestBody User user) {
		return (ResponseBody) new Response("User created", HttpStatus.OK);
	}
	
	@ResponseBody
	private class Response {
		@SuppressWarnings("unused")
		private String message;
		@SuppressWarnings("unused")
		private HttpStatus status;
		
		Response(String message, HttpStatus state) {
			this.setMessage(message);
			this.setStatus(state);
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}
	}

}
