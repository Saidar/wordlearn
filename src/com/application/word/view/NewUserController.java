package com.application.word.view;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.application.util.PassEncrypt;
import com.application.util.UserUtil;
import com.application.word.WordLearnApp;
import com.application.word.model.User_word;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewUserController {
	@FXML
	private TextField loginTF;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField repeat;

	@FXML
	private Label loginLabel;

	@FXML
	private Label loginLabel2;

	@FXML
	private Label passwordLabel;

	private Stage dialogStage;

	public NewUserController() {
	}

	@FXML
	public void initialize() {
		//loginLabel2.setText("");

	}

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	@FXML
    private void handleCancel() {
        dialogStage.close();
    }

	@FXML
	public void onActionLogin() {
		String login = "";
		login = loginTF.getText();

		if(WordLearnApp.wordLernApp.getConnection().isLoginExists(login)) {
			loginLabel.setText("exists");
		}else {
			loginLabel.setText("");
		}
	}

	@FXML
	public void onPasswordCheck() {
		String pass = "";
		String rePass = "";
		pass = password.getText();
		rePass = repeat.getText();

		if(!pass.equals(rePass)) {
			passwordLabel.setText("do not match");
		}else {
			passwordLabel.setText("");
		}
	}

	@FXML
	public void onButtonSave() {
		String login = "";
		String pass = "";
		String rePass = "";
		login = loginTF.getText();
		pass = password.getText();
		rePass = repeat.getText();

		if(isInputValid()) {
			try {
				pass = PassEncrypt.getStringFromSHA256(pass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WordLearnApp.wordLernApp.getConnection().saveUser(new User_word(login, pass));
			loginTF.setText("");
			password.setText("");
			repeat.setText("");
			//loginLabel2.setText("New User created!");
			dialogStage.close();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(dialogStage);
            alert.setTitle("New User Created");
            alert.setHeaderText("New User Created! \nPlease Login!");
            alert.showAndWait();

		}
	}

	private boolean isInputValid() {
		String errorMessage = "";


		if(WordLearnApp.wordLernApp.getConnection().isLoginExists( loginTF.getText())) {
			errorMessage += "This login is exists!\n";
		}

		if(!password.getText().equals(repeat.getText())) {
			errorMessage += "Passwords do not mathes!\n";
		}

		if (loginTF.getText() == null || loginTF.getText().length() == 0) {
            errorMessage += "No valid login!\n";
        }

		if (password.getText() == null || password.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

		if (repeat.getText() == null || repeat.getText().length() == 0) {
            errorMessage += "No valid repeat password!\n";
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




}
