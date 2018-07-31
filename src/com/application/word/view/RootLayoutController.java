package com.application.word.view;

import com.application.word.WordLearnApp;

import javafx.fxml.FXML;

public class RootLayoutController {

	private void initialize() {
    }

	@FXML
	private void changeDB() {
		WordLearnApp.wordLernApp.showDBConnection();
	}

	@FXML
	private void newUser() {
		WordLearnApp.wordLernApp.showNewUserLayout();
	}

	@FXML
    private void handleExit() {
        System.exit(0);
    }

}
