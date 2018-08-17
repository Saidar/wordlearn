package com.application.word.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.application.connection.ConnectionDB;
import com.application.word.WordLearnApp;
import com.application.word.model.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WordController {

	@FXML
	private Label wordLabel;

	@FXML
	private Label answerLabel_1;

	@FXML
	private Label answerLabel_2;

	@FXML
	private Label answerLabel_3;

	@FXML
	private Label answerLabel_4;

	@FXML
	private Label corAnswer;

	//private List<Word> showWord;
	private Word randomWord;


	@FXML
	private void initialize() {
		getNewWords();
		corAnswer.setText("");
	}

	private void getNewWords() {
		List<Word> showWord = new ArrayList(); //words which are going to show on Scene
		randomWord = getRandomWord();

		showWord = getAnswersWord(randomWord.getId());
		showWord.add(randomWord);

		Collections.shuffle(showWord);

		wordLabel.setText(randomWord.getWord());
		answerLabel_1.setText(showWord.get(0).getTranslate());
		answerLabel_2.setText(showWord.get(1).getTranslate());
		answerLabel_3.setText(showWord.get(2).getTranslate());
		answerLabel_4.setText(showWord.get(3).getTranslate());

	}

	public Word getRandomWord() {
		ConnectionDB connection = WordLearnApp.wordLernApp.getConnection();
		List<Word> wordList = (List<Word>) connection.selectQuestionWord();
		Collections.shuffle(wordList);
		try {
			return wordList.get(0);
		}catch (Exception e) {
			corAnswer.setText("There is no any new words for you!");
			return new Word("NoWords", "NoWords");
		}

	}

	public List getAnswersWord(long id) {
		List<Word> answerList = WordLearnApp.wordLernApp.getConnection().selectAnswerWord(id);
		Collections.shuffle(answerList);
		List<Word> returnList = new ArrayList();
		try {
			returnList.add(answerList.get(0));
			returnList.add(answerList.get(1));
			returnList.add(answerList.get(2));
			return returnList;
		}catch (IndexOutOfBoundsException e) {
			System.out.println("There is no any words in DB!");
			returnList.add(new Word("NoWords", "NoWords"));
			returnList.add(new Word("NoWords", "NoWords"));
			return returnList;
		}

	}

	@FXML
	public void onHandleBackButton() {
		WordLearnApp.wordLernApp.showWelcomeLayout();
	}

	@FXML
	private void onFirstButton() {
		String potAnswer = answerLabel_1.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	private void onSecondtButton() {
		String potAnswer = answerLabel_2.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	private void onThirdButton() {
		String potAnswer = answerLabel_3.getText();
		checkAnswer(potAnswer);
	}

	@FXML
	private void onQuardButton() {
		String potAnswer = answerLabel_4.getText();
		checkAnswer(potAnswer);
	}

	private void checkAnswer(String potAnswer) {
		if(!potAnswer.equals("NoWords")){
			if(potAnswer.equals(randomWord.getTranslate())) {
				corAnswer.setText("Correct!");
				WordLearnApp.wordLernApp.getConnection().incrementCountWord(randomWord);
				getNewWords();
			}else {
				corAnswer.setText("Incorrect...");
			}
		}else {
			corAnswer.setText("Restart the App!");
		}
	}




}
