package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Thread extends Model {
	
	@Id
	String id;
	
	@OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Post original;
	
	@ElementCollection 
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	List<Post> replies;
	
	public Thread() {
		
	}
	
	public Thread(Post post) {
		replies = new ArrayList<Post>();
		id = post.getId() + "1";
		post.setThreadId(id);
		original = post;
	}
	
	public void addReply(Post post) {
		replies.add(post);
	}
	
	public Post getPost() {
		return original;
	}
	
	public void addOrRemoveLike(String username) {
		original.addOrRemoveLike(username);
	}
	
	public void addOrRemoveReplyLike(Post post, String username) {
		for (Post p : replies) {
			if (p.getId().equals(post.getId())) {
				p.addOrRemoveLike(username);
			}
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public List<Post> getReplies() {
		return this.replies;
	}

}
