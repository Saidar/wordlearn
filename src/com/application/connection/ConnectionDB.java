package com.application.connection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.application.util.ConnectionUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.Sentence;
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
					.addAnnotatedClass(WordToUser.class)
					.addAnnotatedClass(Sentence.class);
*/
					.configure(hibConfig);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //hibernate.cfg.xml
		this.sf = cfg.buildSessionFactory();
		//this.session = sf.openSession();

	}



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

	public long getUserId(String login) {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from User_word where login like :paramLogin");
		query.setParameter("paramLogin", login);
		User_word user = (User_word) query.getSingleResult();

		this.session.close();

		return user.getId();
	}

	public boolean isLoginExists(String login) {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from User_word where login like :paramLogin");
		query.setParameter("paramLogin", login);
		List list = query.getResultList();
		this.session.close();
		return !list.isEmpty();
	}

	public void saveUser(User_word user) {
		this.session = sf.openSession();
		this.session.beginTransaction();
			this.session.save(user);
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

	public boolean isPassEqualsToUser(String login, String pass) {
		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from User_word where login like :paramLogin");
		query.setParameter("paramLogin", login);
		List<User_word> list = (List<User_word>) query.getResultList();

		if(!list.isEmpty() && list.get(0).getPassword().equals(pass) ) {
			return true;
		}
		return false;
	}

	public List selectWords() {
		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from Word");
		List<Word> listWords = (List<Word>) query.getResultList();

		return listWords;
	}

	public List selectQuestionWord() {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from Word");
		List<Word> listWords = (List<Word>) query.getResultList();

		query = this.session.createQuery("Select a From Word as a INNER JOIN WordToUser as b "
													+ "on a.id = b.word "
													+ "where b.countRepeat > 3 "
													+ "and b.user = :paramUserId");
		User_word user = WordLearnApp.wordLernApp.getUser_word();
		query.setParameter("paramUserId", user);
		List<Word> listUsersWords = (List<Word>) query.getResultList();

		List<Word> list = new ArrayList<Word>();

        for (int i = 0; i < listWords.size(); i ++ ) {
	        for(int j = 0; j < listUsersWords.size(); j++) {
	        	String first = listUsersWords.get(j).getWord();
	        	String second = listWords.get(i).getWord();
	            if(first.equals(second)){
	            	list.add(listWords.get(i));
	            }
	        }
        }

        listWords.removeAll(list);


		return listWords;
	}

	public List selectAnswerWord(long id) {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from Word where id <> :paramId");
		query.setParameter("paramId", id);
		List<Word> list = (List<Word>) query.getResultList();
		return list;
	}

	public Word selectOneWord(long word) {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from Word where id = :paramId");
		query.setParameter("paramId", word);
		Word list = (Word) query.getSingleResult();
		return list;
	}

	public List<WordToUser> selectWordToUserList(long userId){

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from WordToUser where userid = :paramId");
		query.setParameter("paramId", userId);
		List<WordToUser> list = (List<WordToUser>) query.getResultList();
		return list;
	}

	public List<WordToUser> selectWordToUserList(Word word, User_word user){

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("from WordToUser where user = :paramUserId and word = :paramWordId");
		query.setParameter("paramUserId", user);
		query.setParameter("paramWordId", word);
		List<WordToUser> list = (List<WordToUser>) query.getResultList();
		return list;
	}

	public boolean isWordToUserExistUser(User_word user, Word word) {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("Select b.login, b.password from WordToUser as a "
												+ "INNER JOIN User_word as b "
												+ "on a.user = b.id "
												+ "where a.user = :paramUserId and "
												+ "a.word = :paramWordId");
		query.setParameter("paramUserId", user);
		query.setParameter("paramWordId", word);

		List<User_word> user_word = (List<User_word>) query.getResultList();

		return !user_word.isEmpty();
	}

	public boolean isWordToUserExistWord(long wordId) {

		this.session = sf.openSession();
		this.session.beginTransaction();

		Query query = this.session.createQuery("Select b.word, b.translate from WordToUser as a "
												+ "INNER JOIN Word as b "
												+ "on a.id = b.wordId "
												+ "where a.wordId = :paramId");
		query.setParameter("paramId", wordId);

		List<Word> word = (List<Word>) query.getResultList();

		this.session.close();

		return !word.isEmpty();


	}


	public void incrementCountWord(Word word) {

		User_word user = WordLearnApp.wordLernApp.getUser_word();

		word = selectOneWord(word.getId());
		if(isWordToUserExistUser(user, word)) {
			updateCountWord(word, user);
		}else {
			WordToUser wordToUser = new WordToUser(user, word);
			this.saveWordToUser(wordToUser);
		}

	}

	private void updateCountWord(Word word, User_word user) {
		this.session = sf.openSession();
		this.session.beginTransaction();

		List<WordToUser> wordToUser = selectWordToUserList(word, user);
		int count = (int) wordToUser.get(0).getCountRepeat();
		count++;
		Query query = session.createQuery("update WordToUser set count_repeat = :newCount "
												   + "where word = :wordId and "
												   		 + "user = :userId");
		query.setParameter("newCount", count);
		query.setParameter("wordId", word);
		query.setParameter("userId", user);
		int result = query.executeUpdate();
		this.session.getTransaction().commit();

		this.session.close();
	}


}
