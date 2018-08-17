package com.application.word.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sentence")
public class Sentence implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "sentence", length = 200)
	private String sentence;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name= "word_id")
	private Word word;

	@OneToMany(mappedBy = "sentence")
	private List<SentenceToUser> sentenceToUser = new ArrayList<SentenceToUser>();

	public Sentence() {
		this(null, null);
	}

	public Sentence(String sentence, Word word) {
		this.sentence = sentence;
		this.word = word;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

}
