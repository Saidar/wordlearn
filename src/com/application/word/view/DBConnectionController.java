package com.application.word.view;

import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;

import javax.security.auth.login.LoginException;

import org.hibernate.HibernateException;

import com.application.connection.ConnectionDB;
import com.application.util.ConnectionUtil;
import com.application.word.WordLearnApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class DBConnectionController {

	@FXML
	private TextField dbName;

	@FXML
	private TextField dbLogin;

	@FXML
	private PasswordField dbPass;

	private Stage dialogStage;

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
    private void initialize() {
		dbName.setText(ConnectionUtil.getDbName());
		dbLogin.setText(ConnectionUtil.getUsername());
		dbPass.setText(ConnectionUtil.getPassword());
    }

	@FXML
	private void reconnect() {

		boolean connected = false;

		String errorMessage = "";

		if(isInputValid()) {
			try {
				//WordLearnApp.wordLernApp.getConnection().getSession().close();
				WordLearnApp.wordLernApp.getConnection().getSessionFactory().close();

				ConnectionUtil.setDbName(dbName.getText());
				ConnectionUtil.setUsername(dbLogin.getText());
				ConnectionUtil.setPassword(dbPass.getText());
				ConnectionDB newConnection = new ConnectionDB("");
				WordLearnApp.wordLernApp.setConnection(newConnection);
				connected = true;
/*			}catch(SQLSyntaxErrorException er) {
				errorMessage += "Name of DB is not correct! \n";
			}catch(SQLNonTransientConnectionException er2) {
				errorMessage += "Login or Password is not correct!";
				*/
			}catch(HibernateException e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
	            alert.setTitle("Error");
	            alert.setHeaderText("Check new properties of Data Base!");
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
			}

			if(connected) {
				dialogStage.close();
				Alert connectedDB = new Alert(AlertType.CONFIRMATION);
				connectedDB.initOwner(dialogStage);
				connectedDB.setTitle("Connected");
				connectedDB.setHeaderText("New connection to Data Base!");
				connectedDB.showAndWait();

			}
		}

	}

	@FXML
	private void cancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (dbName.getText() == null || dbName.getText().length() == 0) {
            errorMessage += "No valid DB Name!\n";
        }

		if (dbLogin.getText() == null || dbLogin.getText().length() == 0) {
            errorMessage += "No valid DB Login!\n";
        }

		if (dbPass.getText() == null || dbPass.getText().length() == 0) {
            errorMessage += "No valid DB Password!\n";
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
