package com.application.word.view;

import java.io.Serializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.application.word.WordLearnApp;

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

	@FXML
	private void initialize() {
	}

	@FXML
	public void onHandleBackButton() {
		WordLearnApp.wordLernApp.showWelcomeLayout();
	}

	@FXML
	public void onFirstAnswerButton() {

	}

	@FXML
	public void onSecondAnswerButton() {

	}

	@FXML
	public void onThirdAnswerButton() {

	}

	@FXML
	public void onQuardAnswerButton() {

	}



}
