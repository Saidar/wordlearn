package com.application.word.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.application.util.WordDBUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.Word;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddSingleWordController {

	@FXML
	private TextField word;

	@FXML
	private TextField translation;

	private Stage dialogStage;

	@FXML
	public void initialize() {
	}

	@FXML
    private void handleCancel() {
        dialogStage.close();
    }

	@FXML
	private void handleSave() {

		if(isInputValid()) {
			String inputWord = word.getText();
			String inputTranslation = translation.getText();

			Word newWord = new Word(inputWord, inputTranslation);

			List<Word> words =  WordDBUtil.selectWords();

			if(!newWord.containsInWords(words)) {
	        	WordLearnApp.wordLernApp.getConnection().saveWord(newWord);
	        	dialogStage.close();

	        	Alert alert = new Alert(AlertType.INFORMATION);
        		alert.initOwner(dialogStage);
                alert.setTitle("New Single Word!");
                alert.setHeaderText("New Single Word was added!");
                alert.setContentText("Now this word exists in data base.");
                alert.showAndWait();

        	}else {
        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.initOwner(dialogStage);
                alert.setTitle("New Single Word!");
                alert.setHeaderText("New Single Word was not added!");
                alert.setContentText("This word is already exist in data base.");
                alert.showAndWait();
        	}
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (word.getText() == null || word.getText().length() == 0) {
            errorMessage += "No valid word!\n";
        }

		if (translation.getText() == null || translation.getText().length() == 0) {
            errorMessage += "No valid translation!\n";
        }

		if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
	}

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}
