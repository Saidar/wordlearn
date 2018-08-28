package com.application.word.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "picture_user")
public class PictureToUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User_word user;

	@ManyToOne
	@JoinColumn(name = "picture_id")
	private Picture picture;

	@Column(name = "count_repeat")
	private int countRepeat;

	public PictureToUser() {
		this(null, null);
	}

	public PictureToUser(User_word user, Picture picture) {
		this.user = user;
		this.picture = picture;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User_word getUser() {
		return user;
	}

	public void setUser(User_word user) {
		this.user = user;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public int getCountRepeat() {
		return countRepeat;
	}

	public void setCountRepeat(int countRepeat) {
		this.countRepeat = countRepeat;
	}
}
