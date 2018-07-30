package com.application.word.model;



import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User_word implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "login", length = 15)
	private String login;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy="user")
	private Set<WordToUser> wordToUsers = new HashSet<WordToUser>();


	public User_word() {
		this(null, null);
	}

	public User_word(String login, String password) {
		this.login = login;
		this.password = password;

	}

	public User_word(long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<WordToUser> getWordToUsers() {
		return wordToUsers;
	}

	public void setWordToUsers(Set<WordToUser> wordToUsers) {
		this.wordToUsers = wordToUsers;
	}
}
