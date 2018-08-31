package com.application.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.application.word.WordLearnApp;
import com.application.word.model.Picture;
import com.application.word.model.PictureToUser;
import com.application.word.model.User_word;
import com.application.word.model.Word;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class PicturesDBUtil {

	public static Picture getQuestionPicture() {

		Picture questionPicture;

		Session session = CommonDBUtil.beginTransactionLocal();

		User_word user = WordLearnApp.wordLernApp.getUser_word();

		List<Picture> listFromPictures = getAllPictires();

		@SuppressWarnings("deprecation")
		List<Picture> listFromPictureUser = (List<Picture>) session.createCriteria(Picture.class, "pic")
				.createAlias("pic.pictureToUser", "pictureToUser", Criteria.INNER_JOIN, Restrictions.gt("countRepeat", 3))
				.add(Restrictions.eq("pictureToUser.user", user))
				.list();

		session.close();

		List<Picture> tempList = new ArrayList<Picture>();

        for (int i = 0; i < listFromPictures.size(); i ++ ) {
	        for(int j = 0; j < listFromPictureUser.size(); j++) {
	        	long first = listFromPictureUser.get(j).getId();
	        	long second = listFromPictures.get(i).getId();
	            if(first == second){
	            	tempList.add(listFromPictures.get(i));
	            }
	        }
        }

        listFromPictures.removeAll(tempList);

        try {
        	Collections.shuffle(listFromPictures);
        	questionPicture = listFromPictures.get(0);
        }catch(Exception e) {
        	questionPicture = new Picture(null, new Word("No sentences in DB.", "No sentences in DB."));
        }

		return questionPicture;
	}

	public static List<Picture> getAnswers(Picture randomPicture) {
		Session session = CommonDBUtil.beginTransactionLocal();

		List<Picture> allPosAnswers = session.createCriteria(Picture.class)
				.add(Restrictions.ne("id", randomPicture.getId())).list();

		Collections.shuffle(allPosAnswers);

		List<Picture> result = new ArrayList<Picture>();
		try {
			result.add(allPosAnswers.get(0));
			result.add(allPosAnswers.get(1));
			result.add(allPosAnswers.get(2));
		}catch(Exception e) {
			result.add(new Picture(null, new Word("No sentences in DB.", "No sentences in DB.")));
			result.add(new Picture(null, new Word("No sentences in DB.", "No sentences in DB.")));
			result.add(new Picture(null, new Word("No sentences in DB.", "No sentences in DB.")));
		}

		session.close();

		return result;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static List getAllPictires() {

		Session session = CommonDBUtil.beginTransactionLocal();
		Criteria criteria = null;

		if(session != null) {
			criteria = session.createCriteria(Picture.class);
			List list =  criteria.list();
			session.close();
			return list;
		}else {
			System.out.println("Problem with connection Picture.getAllSentence");
			return null;
		}
	}

	public static Image getImageFromByte(byte[] content) {
		ByteArrayInputStream bis = new ByteArrayInputStream(content);
        BufferedImage read = null;
		try {
			read = ImageIO.read(bis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SwingFXUtils.toFXImage(read, null);
	}

	public static void incrementCountSentence(Picture randomPicture) {

		Session session = CommonDBUtil.beginTransactionLocal();

		User_word user = WordLearnApp.wordLernApp.getUser_word();

		List<PictureToUser> pictureToUser =  session.createCriteria(PictureToUser.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("picture", randomPicture))
				.list();

		try{
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<PictureToUser> criteria = builder.createCriteriaUpdate(PictureToUser.class);
			Root<PictureToUser> root = criteria.from(PictureToUser.class);
			criteria.set(root.get("countRepeat"), pictureToUser.get(0).getCountRepeat() + 1);
			criteria.where(builder.equal(root.get("user"), user));
			criteria.where(builder.equal(root.get("picture"), randomPicture));
			session.createQuery(criteria).executeUpdate();
		}catch(Exception e){
			WordLearnApp.wordLernApp.getConnection().savePictureToUser(new PictureToUser(user, randomPicture));
		}

	}

}
