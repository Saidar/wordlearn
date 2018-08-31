package com.application.connection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.application.util.ConnectionUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.BlobFile;
import com.application.word.model.Picture;
import com.application.word.model.PictureToUser;
import com.application.word.model.Sentence;
import com.application.word.model.SentenceToUser;
import com.application.word.model.User_word;
import com.application.word.model.Word;
import com.application.word.model.WordToUser;

public class ConnectionDB {

	private Configuration cfg;
	private SessionFactory sf;
	private Session session;

	public ConnectionDB(String hibConfig){
		this.cfg = null;
		try {
			cfg = new Configuration()
					/*.setProperty("hibernate.connection.url", ConnectionUtil.getDbString())
					.setProperty("hibernate.connection.password", ConnectionUtil.getPassword())
					.setProperty("hibernate.connection.username", ConnectionUtil.getUsername())
					.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
					.setProperty("connection.driver_class", "com.mysql.jdbc.Driver")
					.setProperty("hibernate.connection.pool_size", "1000")
					.addAnnotatedClass(User_word.class)
					.addAnnotatedClass(Word.class)
					.addAnnotatedClass(WordToUser.class);
*/
					.configure(hibConfig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //hibernate.cfg.xml
		this.sf = cfg.buildSessionFactory();
		//this.session = sf.openSession();

	}


	//get set
	public Configuration getConfiguration() {
		return this.cfg;
	}

	public void setConfiguration(Configuration cfg) {
		this.cfg = cfg;
	}

	public SessionFactory getSessionFactory() {
		return this.sf;
	}

	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}

	public Session getSession() {
		return this.session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	//db functions

	public void startTransaction() {
		this.session.beginTransaction();
	}

	public void closeTansaction() {
		this.session.close();
	}

	public void commitTansaction() {
		this.session.getTransaction().commit();
	}

	public void commitTansaction(Transaction trans) {
		trans.commit();
	}

	public void saveUser(User_word user) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(user);
			this.session.getTransaction().commit();
		this.session.close();
	}

	public void saveSentence(Sentence sentence) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(sentence);
			this.session.getTransaction().commit();
		this.session.close();
	}

	public void saveSentenceToUser(SentenceToUser sentenceToUser) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(sentenceToUser);
			this.session.getTransaction().commit();
		this.session.close();
	}

	public void saveWord(Word word) {
			this.session = sf.openSession();
			this.session.beginTransaction();
				this.session.save(word);
				this.session.getTransaction().commit();
			this.session.close();
	}

	public void saveWordToUser(WordToUser wordToUser) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(wordToUser);
			this.session.getTransaction().commit();
		this.session.close();

	}

	public void savePicture(Picture picture) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(picture);
			this.session.getTransaction().commit();
		this.session.close();
	}

	public void savePictureToUser(PictureToUser pictureToUser) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(pictureToUser);
			this.session.getTransaction().commit();
		this.session.close();
	}

	public void saveFile(BlobFile file) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(file);
			this.session.getTransaction().commit();
		this.session.close();
	}
}
