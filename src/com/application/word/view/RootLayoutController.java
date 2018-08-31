package com.application.word.view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.application.util.WordDBUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.Word;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RootLayoutController {

	private Stage dialogStage;

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

	@FXML
	private void addWords() {
		if(WordLearnApp.wordLernApp.getUser_word() != null) {
			addWordsLogic();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(dialogStage);
            alert.setTitle("No User!");
            alert.setHeaderText("Error during adding new words!");
            alert.setContentText("Please log in first!");
            alert.showAndWait();
		}
	}

	@FXML
	private void addSingleWord() {
		if(WordLearnApp.wordLernApp.getUser_word() != null) {
			addSingleWordLogic();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(dialogStage);
            alert.setTitle("No User!");
            alert.setHeaderText("Error during adding new words!");
            alert.setContentText("Please log in first!");
            alert.showAndWait();
		}
	}

	private void addSingleWordLogic() {
		WordLearnApp.wordLernApp.showAddSingleWord();
	}

	private void addWordsLogic() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(WordLearnApp.wordLernApp.getPrimaryStage());

        Scanner scanner = null;
        try {
        	scanner = new Scanner(file);
        }catch(Exception e) {
        	e.printStackTrace();
        }



        String[] newWords;
        List<String> exists = new ArrayList<>();
        //exists = null;
        int counter = 0;
        List<Word> words = WordDBUtil.selectWords();

        try {

	        while(scanner.hasNext()) {
	        	newWords = scanner.nextLine().split(" ");
	        	Word newWord = new Word(newWords[1], newWords[0]);
	        	if(!newWord.containsInWords(words)) {
		        	WordLearnApp.wordLernApp.getConnection().saveWord(newWord);
		        	counter++;
	        	}else {
	        		exists.add(newWord.getWord());
	        	}
	        }
        }catch(Exception e) {
        	 Alert alert = new Alert(AlertType.ERROR);
     		 alert.initOwner(dialogStage);
             alert.setTitle("New Words!");
             alert.setHeaderText("Error during adding new words!");
             alert.setContentText("Mask of input file should be: \n\"word\" [space] \"translaton\"");
             alert.showAndWait();
             return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(dialogStage);
        alert.setTitle("New Words!");
        alert.setHeaderText("New words added!");
        if(!exists.isEmpty()) {
        	alert.setContentText("There are words which are not added as they are exist in data base.\nLook log file!");
        	PrintWriter logFile;
			try {
				logFile = new PrintWriter("log_exist_words.txt");
				exists.forEach((s) -> logFile.println(s));
				logFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        alert.showAndWait();
	}



}
