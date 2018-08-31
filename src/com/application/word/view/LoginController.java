package com.application.word.view;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import com.application.word.WordLearnApp;
import com.application.word.model.User_word;
import com.application.connection.ConnectionDB;
import com.application.util.*;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class LoginController {
	@FXML
	private Label passwordLabl;

	@FXML
	private TextField passwordTexF;

	@FXML
	private TextField loginTexF;

	@FXML
	private ImageView imageView;



	public LoginController() {
    }

	@FXML
    private void initialize() {

		try {
			FileInputStream file;
			file = new FileInputStream("resources/images/login_guest.png");
			Image image = new Image(file);
			imageView.setImage(image);


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		passwordLabl.setVisible(false);
		passwordTexF.setVisible(false);
    }

	@FXML
	public void actionOnLogin() {
		String login = loginTexF.getText();

		if(CommonDBUtil.isLoginExists(loginTexF.getText())) {
			passwordLabl.setVisible(true);
			passwordTexF.setVisible(true);
		}else {
			passwordLabl.setVisible(false);
			passwordTexF.setVisible(false);
		}
	}

	@FXML
	public void logInButton() {
		String login = "";
		String pass = "";

		WordLearnApp wordLernApp = WordLearnApp.wordLernApp;
		ConnectionDB connection = wordLernApp.getConnection();

		try {
			login = loginTexF.getText();
			pass = PassEncrypt.getStringFromSHA256(passwordTexF.getText());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(CommonDBUtil.isPassEqualsToUser(login, pass)) {
			wordLernApp.setUser_word(new User_word(login, pass));
			long id = CommonDBUtil.getUserId(login);
			wordLernApp.getUser_word().setId(id);
			wordLernApp.showWelcomeLayout();
		}
	}

	@FXML
	public void handleNewUser() {
		WordLearnApp.wordLernApp.showNewUserLayout();
	}
}
