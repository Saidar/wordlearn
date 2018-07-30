package com.application.word.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

@Entity
@Table(name = "word_user")
public class WordToUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User_word user;

	@ManyToOne
	@JoinColumn(name = "word_id")
	private Word word;

	@Column(name = "count_repeat")
	private long countRepeat;

	public WordToUser() {
		this(null, null);
	}

	public WordToUser(User_word user, Word word) {
		this.user = user;
		this.word = word;
		this.countRepeat = 1;
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

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public long getCountRepeat() {
		return countRepeat;
	}

	public void setCountRepeat(long countRepeat) {
		this.countRepeat = countRepeat;
	}

}


