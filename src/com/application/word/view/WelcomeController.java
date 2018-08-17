package com.application.word.view;

import com.application.word.WordLearnApp;

import javafx.fxml.FXML;

public class WelcomeController {

	private void initialize() {
    }

	@FXML
	public void onHandleCrammingButton() {
		WordLearnApp.wordLernApp.showWordLearnLauout();
	}

	@FXML
	public void onHandleSentenceButton() {
		WordLearnApp.wordLernApp.showSentenceLauout();
	}

	@FXML
	public void onHandlePicturesButton() {

	}

	@FXML
	public void onHandleLogOutButton() {
		WordLearnApp.wordLernApp.setUser_word(null);
		WordLearnApp.wordLernApp.showLoginLayout();
	}

}
