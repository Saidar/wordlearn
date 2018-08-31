package com.application.util;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.application.word.WordLearnApp;
import com.application.word.model.User_word;

public class CommonDBUtil {

	public static Session beginTransactionLocal() {
		try {
			SessionFactory sessionFactory = WordLearnApp.wordLernApp.getConnection().getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			return session;
		}catch(HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long getUserId(String login) {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from User_word where login like :paramLogin");
		query.setParameter("paramLogin", login);
		User_word user = (User_word) query.getSingleResult();

		session.close();

		return user.getId();
	}

	public static boolean isPassEqualsToUser(String login, String pass) {

		Session session = beginTransactionLocal();

		Query query = session.createQuery("from User_word where login like :paramLogin");
		query.setParameter("paramLogin", login);
		List<User_word> list = (List<User_word>) query.getResultList();

		if(!list.isEmpty() && list.get(0).getPassword().equals(pass) ) {
			return true;
		}

		session.close();
		return false;
	}

	public static boolean isLoginExists(String login) {

		Session session = CommonDBUtil.beginTransactionLocal();

		Query query = session.createQuery("from User_word where login like :paramLogin");
		query.setParameter("paramLogin", login);
		List list = query.getResultList();
		session.close();

		return !list.isEmpty();
	}

}
