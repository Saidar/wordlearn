package com.application.word.model;

import java.awt.Image;
import java.nio.file.Files;
import java.io.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.bytecode.internal.javassist.InstantiationOptimizerAdapter;

@Entity
@Table(name = "file")
public class BlobFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "content")
	@Lob
	private byte[] content;

	@OneToOne(mappedBy="file")
	private Picture picture;

	public BlobFile() {
		this(null, null);
	}

	public BlobFile(Picture picture, byte[] content) {
		this.picture = picture;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public byte[] writeImage(String location) {

		File file = new File(location);

		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}


		//return null;
	}


}
