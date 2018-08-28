package com.application.word.model;

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
@Table(name="picture")
public class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "file_id")
	private BlobFile file;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name= "word_id")
	private Word word;

	@OneToMany(mappedBy = "picture")
	private List<PictureToUser> pictureToUser = new ArrayList<PictureToUser>();

	public Picture() {
		this(null, null);
	}

	public Picture(BlobFile file, Word word) {
		this.file = file;
		this.word = word;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BlobFile getFile() {
		return file;
	}

	public void setFile(BlobFile file) {
		this.file = file;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public List<PictureToUser> getPictureToUser() {
		return pictureToUser;
	}

	public void setPictureToUser(List<PictureToUser> pictureToUser) {
		this.pictureToUser = pictureToUser;
	}

}
