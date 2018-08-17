package com.application.word.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.application.util.SentenceDBUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.Sentence;
import com.application.word.model.Word;

public class SentenceController implements Serializable{

	@FXML
	private Label sentence;

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

	Sentence randomSentence;

	@FXML
	private void initialize() {
		//System.out.println(SentenceDBUtil.getAllSentence().toString());
		getNewSentence();

		corAnswer.setText("");
	}

	@FXML
	public void onHandleBackButton() {
		WordLearnApp.wordLernApp.showWelcomeLayout();
	}

	@FXML
	public void onFirstAnswerButton() {
		String potAnswer = answer_1.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	public void onSecondAnswerButton() {
		String potAnswer = answer_2.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	public void onThirdAnswerButton() {
		String potAnswer = answer_3.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	public void onQuardAnswerButton() {
		String potAnswer = answer_4.getText();
		checkAnswer(potAnswer);
	}

	public void getNewSentence() {
		//Question
		List<Sentence> showSentence = new ArrayList<Sentence>();
		randomSentence = SentenceDBUtil.getQuestionSentenceString();
		showSentence.add(randomSentence);

		//Answers
		showSentence.addAll(SentenceDBUtil.getAnswers(randomSentence));

		//Suffle

		Collections.shuffle(showSentence);

		if(!randomSentence.getSentence().equals("No sentences in DB.")) {
			String newRandomSentence = replaceAnswer(randomSentence.getSentence(), randomSentence.getWord().getWord());
			sentence.setText(newRandomSentence);
			answer_1.setText(showSentence.get(0).getWord().getWord());
			answer_2.setText(showSentence.get(1).getWord().getWord());
			answer_3.setText(showSentence.get(2).getWord().getWord());
			answer_4.setText(showSentence.get(3).getWord().getWord());
		}else {
			String temp = randomSentence.getSentence();
			sentence.setText(temp);
			answer_1.setText(temp);
			answer_2.setText(temp);
			answer_3.setText(temp);
			answer_4.setText(temp);
		}




	}

	private String replaceAnswer(String newRandomSentence, String answer) {
		return newRandomSentence.replaceFirst(answer, "_______");
	}

	private void checkAnswer(String potAnswer) {
		if(!potAnswer.equals("No sentences in DB.")){
			if(potAnswer.equals(randomSentence.getWord().getWord())) {
				corAnswer.setText("Correct!");
				SentenceDBUtil.incrementCountSentence(randomSentence);
				getNewSentence();
			}else {
				corAnswer.setText("Incorrect...");
			}
		}else {
			corAnswer.setText("Restart the App!");
		}
	}

}
