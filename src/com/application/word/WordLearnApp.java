package com.application.word;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.hibernate.HibernateException;

import com.application.connection.ConnectionDB;
import com.application.util.PassEncrypt;
import com.application.word.model.Sentence;
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
		this.primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image("file:resources/images/login_guest.png"));

		WordLearnApp.wordLernApp = this;

		//connection to DB

		connectDB("hibernate.cfg.xml");

		try {
			Word checkWord_1 = new Word("hello", "привет");
			Word checkWord_2 = new Word("mama", "мама");
			Word checkWord_3 = new Word("dad", "папа");
			Word checkWord_4 = new Word("sun", "солнце");

			connection.saveUser(new User_word("admin", PassEncrypt.getStringFromSHA256("admin")));

			connection.saveWord(checkWord_1);
			connection.saveWord(checkWord_2);
			connection.saveWord(checkWord_3);
			connection.saveWord(checkWord_4);

			connection.saveSentence(new Sentence("I say hello!", checkWord_1));
			connection.saveSentence(new Sentence("My mama is so beautiful!", checkWord_2));
			connection.saveSentence(new Sentence("My dad is very tall!", checkWord_3));
			connection.saveSentence(new Sentence("It is very warm when the sun is out!", checkWord_4));

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch(Exception t) {
			t.printStackTrace();
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

			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/WordLearnLayout.fxml"));
			//loader.setLocation(WordLernApp.class.getResource("view/WordLernLayout.fxml"));

			//loader.setController(this);

			AnchorPane wordLernLayout = (AnchorPane) loader.load();

			rootLayout.setCenter(wordLernLayout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showSentenceLauout() {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SentenceLayout.fxml"));
			//loader.setLocation(WordLernApp.class.getResource("view/WordLernLayout.fxml"));

			//loader.setController(this);

			AnchorPane sentenceLayout = (AnchorPane) loader.load();

			rootLayout.setCenter(sentenceLayout);

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
	    //this.connection.closeTansaction();
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
            dialogStage.setResizable(false);
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

    public void showAddSingleWord() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WordLearnApp.class.getResource("view/AddSingleWordLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Single Word");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddSingleWordController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            //return false;
        }
    }

    public void showDBConnection() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WordLearnApp.class.getResource("view/DBConnectionLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("DB Connection");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DBConnectionController controller = loader.getController();
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
