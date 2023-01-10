package com.example.demo.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class User extends Model {
	
	@Id
	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private boolean enabled;
	
	@ElementCollection (targetClass = Role.class, fetch = FetchType.EAGER)
	@Enumerated (EnumType.STRING)
	private Set<Role> roles;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User() {
		enabled = true;
	}
	
	public User(String username, String password, String firstName, String lastName) {
		setUsername(username);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		enabled = true;
		roles = new HashSet<Role>();
	}
	
	public User(User user) {
		setUsername(user.username);
		setPassword(user.password);
		setFirstName(user.firstName);
		setLastName(user.lastName);
		enabled = true;
		roles = new HashSet<Role>();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}
}
