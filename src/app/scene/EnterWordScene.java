package app.scene;

import java.io.File;
import java.io.IOException;

import app.AppModel;
import app.model.GameState;
import app.model.QuizModel;
import app.process.Festival;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Fraser McIntosh on 14/09/2016. Responsible for the scene that
 * prompts user to enter a word after being prompted with a festival call
 */
public class EnterWordScene {
	private QuizModel _quizModel;
	private GameState _gameState;

	/**
	 * Constructor to intialise quiz model and gamestate for the scene
	 */
	public EnterWordScene() {
		_quizModel = AppModel.getQuizModel();
		_gameState = _quizModel.getGameState();
	}

	/**
	 * Build the components of the Enter Word app.scene, and return a app.scene
	 * object
	 */
	private Scene build() {

		AppModel.getWindow().setTitle("Enter Word");
		// Determines and sets the heading label corresponding to the game mode
		Label headingLbl = new Label();
		switch (_gameState) {
		case QUIZ:
			headingLbl.setText("Standard Quiz");
			break;
		case REVIEW:
			headingLbl.setText("Review Quiz");
			break;
		case ONELIFE:
			headingLbl.setText("One Life");
			break;
		case THREELIVES:
			headingLbl.setText("Three Lives");
			break;
		}
		// set label font
		headingLbl.setTranslateY(-100);
		headingLbl.setId("subheadingtext");

		// Label displaying the current score to user, eg 3 out of 3
		Label currentScoreLabel = new Label(
				"Current score: " + _quizModel.getNumCorrectWords() + " out of " + _quizModel.getCurruntWordIndex());
		currentScoreLabel.setTranslateY(-50);
		currentScoreLabel.setId("captiontext");

		// Label that displays what number word it is, eg Word 5 of 10
		Label wordCountLabel = new Label(
				"Enter Word " + (_quizModel.getCurruntWordIndex() + 1) + " of " + _quizModel.getNumWordsInQuiz());
		wordCountLabel.setId("captiontext");

		// Text input where user will enter word
		final TextField input = new TextField();
		input.setPromptText("Spell word here");

		// Event to handle when user submits a word
		EventHandler<ActionEvent> submitEvent = (new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// submit answer which returns false if word is invalid
				boolean validWord = _quizModel.submitAnswer(input.getText());
				// Build appropriate app.scene depending on app.model state
				if (!validWord) {
					// Would like it to be a pop up, so might need a new method
					// for this in app.AppModel
					InvalidInputScene.setScene("Whoops! Only spell with letters please!");
				} else {
					if (_quizModel.outOfLives()) {
						new OutOfLivesScene().setScene();
					} else {
						// Either display app.scene.WordResultScene or
						// QuizResultScene
						new WordResultScene().setScene();
					}
				}
			}
		});

		// Event to make a festival call to say aloud the current quiz word
		EventHandler<ActionEvent> sayWordEvent = (new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				// Say word
				try {
					Festival.sayWord(_quizModel.getCurrentWord());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		/*
		 * Button that is responsible for submitting a word. This involves
		 * checking whether the word is spelt correctly or not and asking the
		 * app.model to update itself based on this result
		 */
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(submitEvent);

		input.setOnAction(submitEvent);

		/*
		 * Button that causes festival to say the current word
		 */
		Button sayButton = new Button("Say Word");
		sayButton.setOnAction(sayWordEvent);

		// Button to pre-emptively end quiz
		Button mainMenuButton = new Button("Finish Quiz");
		mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new QuizFinishedScene().setScene();
			}
		});

		// Layout
		HBox innerLayout = new HBox();

		// add components to inner layout
		innerLayout.getChildren().addAll(sayButton, input, submitButton);

		innerLayout.setAlignment(Pos.CENTER);

		HBox livesLayout = new HBox(10);
		livesLayout.setAlignment(Pos.CENTER);

		// If we have lives, display current number of lives
		if (_gameState.equals(GameState.ONELIFE) || _gameState.equals(GameState.THREELIVES)) {
			File file = new File(".media/heart.png");
			Image heart = new Image(file.toURI().toString());

			// Displays a heart for each life
			for (int i = 0; i < _quizModel.getNumberOfLives(); i++) {
				ImageView imgView = new ImageView(heart);
				imgView.setFitHeight(50);
				imgView.setFitWidth(50);
				livesLayout.getChildren().add(imgView);
			}
		}

		VBox outerLayout = new VBox(10);
		outerLayout.setPadding(new Insets(30, 0, 0, 0));

		// add the inner components to the outer layout and customise layout to
		// the GUI style
		outerLayout.getChildren().addAll(headingLbl, currentScoreLabel, wordCountLabel, innerLayout, livesLayout,
				mainMenuButton);
		outerLayout.setAlignment(Pos.CENTER);
		outerLayout.setBackground(AppModel.getBackground());
		outerLayout.getStylesheets().add("app/scene/myStyle.css");

		// allows spacebar shortcut to speak word aloud
		Scene scene = new Scene(outerLayout, AppModel.getWidth(), AppModel.getHeight());
		sayButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.SPACE, KeyCombination.SHORTCUT_DOWN),
				new Runnable() {
					@Override
					public void run() {
						sayButton.fire();
					}
				});

		// allows input to be entered into text field as soon as scene is built
		input.requestFocus();
		// create new app.scene using outerLayour
		return scene;
	}

	/**
	 * Sets the scene by displaying the scene in the primary stage held by the
	 * AppModel class
	 */
	public void setScene() {
		// Build app.scene
		Scene EnterWordScene = build();
		try {
			Festival.sayWord(_quizModel.getCurrentWord());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		// Set app.scene in app.AppModel
		AppModel.setScene(EnterWordScene);
	}

}
