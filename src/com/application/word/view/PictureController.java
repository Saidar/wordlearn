package com.application.word.view;


import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import com.application.connection.ConnectionDB;
import com.application.util.PicturesDBUtil;
import com.application.util.SentenceDBUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.Picture;
import com.application.word.model.Sentence;
import com.application.word.model.Word;
import com.mysql.cj.jdbc.Blob;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PictureController {

	@FXML
	private ImageView imageView;

	@FXML
	private Label answer_1;

	@FXML
	private Label answer_2;

	@FXML
	private Label answer_3;

	@FXML
	private Label answer_4;

	@FXML
	private Label corAnswer;

	private Picture randomPicture;


	@FXML
	private void initialize() {
		getNewPicture();
		corAnswer.setText("");
	}

	public void getNewPicture() {
		//Question
		List<Picture> showPicture = new ArrayList<Picture>();
		randomPicture = PicturesDBUtil.getQuestionPicture();
		showPicture.add(randomPicture);

		//Answers
		showPicture.addAll(PicturesDBUtil.getAnswers(randomPicture));

		//Suffle

		Collections.shuffle(showPicture);

		if(!randomPicture.getWord().getWord().equals("No sentences in DB.")){
			try {

					Image image = PicturesDBUtil.getImageFromByte(randomPicture.getFile().getContent());

					imageView.setImage(image);
					answer_1.setText(showPicture.get(0).getWord().getWord());
					answer_2.setText(showPicture.get(1).getWord().getWord());
					answer_3.setText(showPicture.get(2).getWord().getWord());
					answer_4.setText(showPicture.get(3).getWord().getWord());
			}catch(Exception e) {

				String temp = randomPicture.getWord().getWord();
				//randomPicture.setImage(null);
				answer_1.setText(temp);
				answer_2.setText(temp);
				answer_3.setText(temp);
				answer_4.setText(temp);
			}
		}else {
			String temp = randomPicture.getWord().getWord();
			//randomPicture.setImage(null);
			answer_1.setText(temp);
			answer_2.setText(temp);
			answer_3.setText(temp);
			answer_4.setText(temp);
		}
	}

	@FXML
	public void onHandleBackButton() {
		WordLearnApp.wordLernApp.showWelcomeLayout();
	}

	@FXML
	private void onFirstButton() {
		String potAnswer = answer_1.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	private void onSecondtButton() {
		String potAnswer = answer_2.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	private void onThirdButton() {
		String potAnswer = answer_3.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	private void onQuardButton() {
		String potAnswer = answer_4.getText();
		checkAnswer(potAnswer);
	}

	private void checkAnswer(String potAnswer) {
		if(!potAnswer.equals("No sentences in DB.")){
			if(potAnswer.equals(randomPicture.getWord().getWord())) {
				corAnswer.setText("Correct!");
				PicturesDBUtil.incrementCountSentence(randomPicture);
				getNewPicture();
			}else {
				corAnswer.setText("Incorrect...");
			}
		}else {
			corAnswer.setText("Restart the App!");
		}
	}




}
