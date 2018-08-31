package com.application.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.application.word.WordLearnApp;
import com.application.word.model.Sentence;
import com.application.word.model.SentenceToUser;
import com.application.word.model.User_word;
import com.application.word.model.Word;

public class SentenceDBUtil {

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static List getAllSentence() {

		Session session = CommonDBUtil.beginTransactionLocal();
		Criteria criteria = null;

		if(session != null) {
			criteria = session.createCriteria(Sentence.class);
			List list =  criteria.list();
			session.close();
			return list;
		}else {
			System.out.println("Problem with connection SentenceDB.getAllSentence");
			return null;
		}
	}

	public static Sentence getQuestionSentenceString() {

		Sentence questionSentence;

		Session session = CommonDBUtil.beginTransactionLocal();

		User_word user = WordLearnApp.wordLernApp.getUser_word();

		List<Sentence> listFromSentence = getAllSentence();

		@SuppressWarnings("deprecation")
		List<Sentence> listFromSentenceUser = (List<Sentence>) session.createCriteria(Sentence.class, "sen")
				.createAlias("sen.sentenceToUser", "sentenceToUser", Criteria.INNER_JOIN, Restrictions.gt("countRepeat", 3))
				.add(Restrictions.eq("sentenceToUser.user", user))
				.list();

		session.close();

		List<Sentence> tempList = new ArrayList<Sentence>();

        for (int i = 0; i < listFromSentence.size(); i ++ ) {
	        for(int j = 0; j < listFromSentenceUser.size(); j++) {
	        	String first = listFromSentenceUser.get(j).getSentence();
	        	String second = listFromSentence.get(i).getSentence();
	            if(first.equals(second)){
	            	tempList.add(listFromSentence.get(i));
	            }
	        }
        }

        listFromSentence.removeAll(tempList);

        try {
        	Collections.shuffle(listFromSentence);
        	questionSentence = listFromSentence.get(0);
        }catch(Exception e) {
        	questionSentence = new Sentence("No sentences in DB.", new Word("No sentences in DB.", "No sentences in DB."));
        }

		return questionSentence;
	}

	public static List<Sentence> getAnswers(Sentence randomSentence) {

		Session session = CommonDBUtil.beginTransactionLocal();

		List<Sentence> allPosAnswers = session.createCriteria(Sentence.class)
				.add(Restrictions.ne("id", randomSentence.getId())).list();

		Collections.shuffle(allPosAnswers);

		List<Sentence> result = new ArrayList<Sentence>();
		try {
			result.add(allPosAnswers.get(0));
			result.add(allPosAnswers.get(1));
			result.add(allPosAnswers.get(2));
		}catch(Exception e) {
			result.add(new Sentence("No more sentence!", new Word()));
			result.add(new Sentence("No more sentence!", new Word()));
			result.add(new Sentence("No more sentence!", new Word()));
		}

		session.close();

		return result;
	}

	public static void incrementCountSentence(Sentence randomSentence) {
		Session session = CommonDBUtil.beginTransactionLocal();

		User_word user = WordLearnApp.wordLernApp.getUser_word();

		List<SentenceToUser> sentenceToUser =  session.createCriteria(SentenceToUser.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("sentence", randomSentence))
				.list();

		try{
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<SentenceToUser> criteria = builder.createCriteriaUpdate(SentenceToUser.class);
			Root<SentenceToUser> root = criteria.from(SentenceToUser.class);
			criteria.set(root.get("countRepeat"), sentenceToUser.get(0).getCountRepeat() + 1);
			criteria.where(builder.equal(root.get("user"), user));
			criteria.where(builder.equal(root.get("sentence"), randomSentence));
			session.createQuery(criteria).executeUpdate();

		}catch(Exception e){
			WordLearnApp.wordLernApp.getConnection().saveSentenceToUser(new SentenceToUser(user, randomSentence));
		}

		session.close();
	}



}
