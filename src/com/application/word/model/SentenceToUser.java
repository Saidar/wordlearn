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
@Table(name = "sentence_user")
public class SentenceToUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User_word user;

	@ManyToOne
	@JoinColumn(name = "sentence_id")
	private Sentence sentence;

	@Column(name = "count_repeat")
	private int countRepeat;

	public SentenceToUser() {
		this(null, null);
	}

	public SentenceToUser(User_word user, Sentence sentence) {
		this.user = user;
		this.sentence = sentence;
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

	public Sentence getSentence() {
		return sentence;
	}

	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}

	public int getCountRepeat() {
		return countRepeat;
	}

	public void setCountRepeat(int countRepeat) {
		this.countRepeat = countRepeat;
	}

}
