package com.application.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.application.word.WordLearnApp;
import com.application.word.model.User_word;
import com.application.word.model.Word;
import com.application.word.model.WordToUser;

public class WordDBUtil {


	public static List selectWords() {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from Word");
		List<Word> listWords = (List<Word>) query.getResultList();

		session.close();

		return listWords;
	}

	public static Word selectOneWord(long word) {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from Word where id = :paramId");
		query.setParameter("paramId", word);
		Word list = (Word) query.getSingleResult();

		session.close();

		return list;
	}

	public static void incrementCountWord(Word word) {

		User_word user = WordLearnApp.wordLernApp.getUser_word();

		word = selectOneWord(word.getId());
		if(isWordToUserExistUser(user, word)) {
			updateCountWord(word, user);
		}else {
			WordToUser wordToUser = new WordToUser(user, word);
			WordLearnApp.wordLernApp.getConnection().saveWordToUser(wordToUser);
		}

	}

	public static List<WordToUser> selectWordToUserList(Word word, User_word user){

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from WordToUser where user = :paramUserId and word = :paramWordId");
		query.setParameter("paramUserId", user);
		query.setParameter("paramWordId", word);
		List<WordToUser> list = (List<WordToUser>) query.getResultList();

		session.close();

		return list;
	}

	public static List selectAnswerWord(long id) {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from Word where id <> :paramId");
		query.setParameter("paramId", id);
		List<Word> list = (List<Word>) query.getResultList();
		session.close();
		return list;
	}

	public static List<WordToUser> selectWordToUserList(long userId){

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from WordToUser where userid = :paramId");
		query.setParameter("paramId", userId);
		List<WordToUser> list = (List<WordToUser>) query.getResultList();

		session.close();
		return list;
	}

	public static boolean isWordToUserExistUser(User_word user, Word word) {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("Select b.login, b.password from WordToUser as a "
												+ "INNER JOIN User_word as b "
												+ "on a.user = b.id "
												+ "where a.user = :paramUserId and "
												+ "a.word = :paramWordId");
		query.setParameter("paramUserId", user);
		query.setParameter("paramWordId", word);

		List<User_word> user_word = (List<User_word>) query.getResultList();

		session.close();

		return !user_word.isEmpty();
	}

	public static boolean isWordToUserExistWord(long wordId) {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("Select b.word, b.translate from WordToUser as a "
												+ "INNER JOIN Word as b "
												+ "on a.id = b.wordId "
												+ "where a.wordId = :paramId");
		query.setParameter("paramId", wordId);

		List<Word> word = (List<Word>) query.getResultList();

		session.close();

		return !word.isEmpty();
	}

	private static void updateCountWord(Word word, User_word user) {

		Session session = CommonDBUtil.beginTransactionLocal();

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
		session.getTransaction().commit();

		session.close();
	}

	public static List selectQuestionWord() {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from Word");
		List<Word> listWords = (List<Word>) query.getResultList();

		query = session.createQuery("Select a From Word as a INNER JOIN WordToUser as b "
													+ "on a.id = b.word "
													+ "where b.countRepeat > 2 "
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

        session.close();

		return listWords;
	}



}
