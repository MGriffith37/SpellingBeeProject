package app.scene;

import java.io.IOException;

import app.AppModel;
import app.model.QuizModel;
import app.model.QuizState;
import app.model.WordState;
import app.process.Festival;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * Created by Fraser McIntosh on 14/09/2016. Responsible for displaying result
 * for tested quiz word
 */
public class WordResultScene {
	private QuizModel _quizModel;
	@SuppressWarnings("unused")
	private boolean _isReview;
	private WordState _currentWordState;

	/**
	 * Constructor to initialise variables for quizModel and word state
	 */
	WordResultScene() {
		_quizModel = AppModel.getQuizModel();
		_currentWordState = _quizModel.getWordState();
	}

	/**
	 * Builds scene to be displayed
	 */
	private Scene build() {

		// Label informing the user if the answered correctly or not
		Label label1 = new Label();
		if (_currentWordState.equals(WordState.MASTERED)) {
			label1.setText("Correct");
			try {
				Festival.sayWord("Correct");
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			label1.setText("Incorrect");
			try {
				Festival.sayWord("Incorrect");
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		label1.setId("captiontext");

		// Button that says next word or finish quiz
		Button actionButton = new Button();
		/*
		 * If quiz is finished take us to the finished quiz app.scene
		 */
		if (_quizModel.getQuizState() == QuizState.FINISHED) {

			actionButton.setText("Finish Quiz");
			actionButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Update number of levels unlocked
					new QuizFinishedScene().setScene();
				}
			});

		} else {

			actionButton.setText("Next Word");

			// This button will take the user back to the 'Enter Word' Scene
			actionButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Refresh word state if word is finished (if word was
					// incorrect don't referesh
					// as need to use it again
					_quizModel.nextWord();

					new EnterWordScene().setScene();
				}
			});

		}

		// Top Layout styling and component adding
		VBox topLayout = new VBox(10);
		topLayout.getChildren().addAll(label1, actionButton);
		topLayout.setAlignment(Pos.CENTER);
		topLayout.setBackground(AppModel.getBackground());
		topLayout.getStylesheets().add("app/scene/myStyle.css");

		Scene scene = new Scene(topLayout, AppModel.getWidth(), AppModel.getHeight());
		// Allows the enter button to fire the action button event
		actionButton.getScene().getAccelerators()
				.put(new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_ANY), new Runnable() {
					@Override
					public void run() {
						actionButton.fire();
					}
				});
		return scene;
	}

	/**
	 * Displays the scene using app model primary stage
	 */
	public void setScene() {
		Scene WordResultScene = build();
		AppModel.setScene(WordResultScene);
	}

}
