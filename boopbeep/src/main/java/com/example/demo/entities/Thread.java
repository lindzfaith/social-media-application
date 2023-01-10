package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Thread extends Model {
	
	@Id
	@GeneratedValue
	Long id;
	
	@OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Post original;
	
	@ElementCollection 
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	List<Post> replies;
	
	public Thread() {
		
	}
	
	public Thread(Post post) {
		original = post;
		replies = new ArrayList<Post>();
	}
	
	public void addReply(Post post) {
		replies.add(post);
	}

}
