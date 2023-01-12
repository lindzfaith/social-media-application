package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Post extends Model implements Comparable<Post> {
	
	@Id
	String id;
	
	String threadId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public ArrayList<String> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes() {
		this.numberOfLikes = likes.size();
	}

	String author;
	
	String content;
	
	LocalDateTime time;
	
	ArrayList<String> likes;
	
	int numberOfLikes;
	
	public Post() {
		likes = new ArrayList<String>();
	}
	
	public Post(String user, String content, LocalDateTime date) {
		this.content = content;
		likes = new ArrayList<String>();
		this.author = user;
		this.time = date;
		id = this.author + "-" + time.toString();
	}
	
	public void setThreadId(String id) {
		this.threadId = id;
	}
	
	public String getThreadId() {
		return threadId;
	}
	
	public void addOrRemoveLike(String username) {
		if (likes.contains(username)) {
			likes.remove(username);
		} else {
			likes.add(username);
		}
		setNumberOfLikes();
	}

	@Override
	public int compareTo(Post o) {
		return o.getTime().compareTo(time);
	}

}
