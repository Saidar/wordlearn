package com.application.word;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.application.connection.ConnectionDB;
import com.application.util.PassEncrypt;
import com.application.word.model.User_word;
import com.application.word.model.Word;
import com.application.word.view.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WordLearnApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ConnectionDB connection;
	public static WordLearnApp wordLernApp;
	private static User_word user;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Word Lern App");
		this.primaryStage.getIcons().add(new Image("file:resources/images/login_guest.png"));

		WordLearnApp.wordLernApp = this;

		//connection to DB

		connectDB("hibernate.cfg.xml");

		try {
			connection.saveUser(new User_word("admin", PassEncrypt.getStringFromSHA256("admin")));
			connection.saveWord(new Word("hello", "привет"));
			connection.saveWord(new Word("mama", "мама"));
			connection.saveWord(new Word("dad", "папа"));
			connection.saveWord(new Word("sunny", "солнце"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		initRootLayout();

		showLoginLayout();

		LoginController loginController = new LoginController();

		//showWordLearnLauout();
	}

	public void initRootLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(WordLearnApp.class.getResource("view/RootLayout.fxml"));



			rootLayout = (BorderPane) loader.load();

			//Stage
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void showLoginLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(WordLearnApp.class.getResource("view/LoginLayout.fxml"));

			AnchorPane loginLayout = (AnchorPane) loader.load();

			rootLayout.setCenter(loginLayout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showWordLearnLauout() {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/WordLernLayout.fxml"));
			//loader.setLocation(WordLernApp.class.getResource("view/WordLernLayout.fxml"));

			//loader.setController(this);

			AnchorPane wordLernLayout = (AnchorPane) loader.load();

			rootLayout.setCenter(wordLernLayout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showWelcomeLayout() {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/WelcomeLayout.fxml"));
			//loader.setLocation(WordLernApp.class.getResource("view/WordLernLayout.fxml"));

			//loader.setController(this);

			AnchorPane wordLernLayout = (AnchorPane) loader.load();

			rootLayout.setCenter(wordLernLayout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stop(){
	    this.connection.closeTansaction();
	    Platform.exit();
	    System.exit(0);
	}

    public void showNewUserLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WordLearnApp.class.getResource("view/NewUserLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            NewUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.setWordLernApp(this);

            dialogStage.showAndWait();

            //return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            //return false;
        }
    }

	public void connectDB(String config){
		connection = new ConnectionDB(config);
	}


	public Stage getPrimaryStage() {
        return primaryStage;
    }

	public WordLearnApp getWordLernApp() {
		return this;
	}

	public ConnectionDB getConnection() {
        return this.connection;
    }

	public void setConnection(ConnectionDB connection) {
		this.connection = connection;
	}

	public User_word getUser_word() {
		return this.user;
	}

	public void setUser_word(User_word user) {
		this.user = user;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
