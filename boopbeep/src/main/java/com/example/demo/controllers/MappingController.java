package com.example.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MappingController {
	
	@GetMapping (value = {"/login", "login"})
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@GetMapping (value = {"/thread/{id}"})
	public ModelAndView thread(String id) {
		return new ModelAndView("thread");
	}
	
	@GetMapping (value = {"/home", "home"})
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@GetMapping (value = "/")
	public RedirectView redirect() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return new RedirectView("login");
		} else if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			return new RedirectView("home");
		}
		
		return new RedirectView("login");
	}

}
