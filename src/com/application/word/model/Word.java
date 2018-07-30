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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "word")
public class Word implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "word", length = 15)
	private String word;

	@Column(name = "translate")
	private String translate;

	@OneToMany(mappedBy="word")
	private Set<WordToUser> wordToUsers = new HashSet<WordToUser>();

	public Word() {
		this(null, null);
	}

	public Word(String word, String translate) {
		this.word = word;
		this.translate = translate;
	}

	public Word(long id, String word, String translate) {
		this.id = id;
		this.word = word;
		this.translate = translate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	public Set<WordToUser> getWordToUsers() {
		return wordToUsers;
	}

	public void setWordToUsers(Set<WordToUser> wordToUsers) {
		this.wordToUsers = wordToUsers;
	}
}
