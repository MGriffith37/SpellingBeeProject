package app.scene;

import java.io.File;

import app.AppModel;
import app.model.GameState;
import app.model.QuizModel;
import app.model.WordState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Fraser McIntosh on 19/09/2016. Responsible for displaying scene
 * when quiz is finished and user can go to different places throughout the GUI
 */
public class QuizFinishedScene {
	private QuizModel _quizModel;
	private GameState _gameState;
	@SuppressWarnings("unused")
	private WordState _currentWordState;

	/**
	 * Constructor to set quiz model, game state and word state
	 */
	QuizFinishedScene() {
		_quizModel = AppModel.getQuizModel();
		_gameState = _quizModel.getGameState();
		_currentWordState = _quizModel.getWordState();
	}

	/**
	 * Builds scene to be displayed
	 */
	private Scene build() {

		// Label informing the user if the answered correctly or not
		Label outcomeLabel = new Label();
		outcomeLabel.setId("subheadingtext");
		// determine the label text
		if (_quizModel.getSuccessfulQuiz() || _gameState.equals(GameState.REVIEW)
				|| _gameState.equals(GameState.ONELIFE) || _gameState.equals(GameState.THREELIVES)) {
			outcomeLabel.setText("Well Done!");
		} else {
			outcomeLabel.setText("Hard Luck!");
		}

		// If successful quiz, allow user to play reward video
		Button playVideoButton = new Button("Play Reward Video");
		playVideoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MediaPlayerScene.setScene();
			}
		});

		// Let user know their score
		Label scoreLabel = new Label(
				"You got " + _quizModel.getNumCorrectWords() + " out of " + _quizModel.getNumWordsInQuiz());
		scoreLabel.setId("captiontext");
		// Button that takes user back to level select scene for the same game
		// mode
		Button levelSelectButton = new Button("Level Select");
		levelSelectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setScene();
			}
		});

		// Button that lets user retry the same level and the same game mode
		Button retryLevelButton = new Button("Retry Level");
		retryLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AppModel.startQuiz(_gameState, _quizModel.getLevelSelected());
			}
		});

		// If a level was unlocked by the user's quiz performance, allows them
		// to go to a quiz of next level
		Button nextLevelButton = new Button("Next Level");
		nextLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AppModel.startQuiz(_gameState, _quizModel.getLevelSelected() + 1);
			}
		});

		// Button that allows user to play a song as reward for completing a
		// quiz
		Button musicBtn = new Button("Play Audio Reward");
		String musicfile = ".media/song.mp3";

		// Event that plays the music
		musicBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Media song = new Media(new File(musicfile).toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(song);
				mediaPlayer.play();
			}
		});

		// Layout
		HBox innerLayout = new HBox();

		// add components to inner layout

		// If final level, or didn't pass we don't want a next level button
		if (_quizModel.getLevelSelected() == AppModel.getNumLevels() || !_quizModel.getSuccessfulQuiz()) {
			innerLayout.getChildren().addAll(levelSelectButton, retryLevelButton);
		} else {
			innerLayout.getChildren().addAll(levelSelectButton, retryLevelButton, nextLevelButton);
		}
		innerLayout.setAlignment(Pos.CENTER);
		VBox outerLayout = new VBox(10);
		outerLayout.setPadding(new Insets(30, 0, 0, 0));

		// Button that returns user to main menu
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		// Layout
		VBox topLayout = new VBox(10);
		topLayout.getChildren().addAll(outcomeLabel, scoreLabel);
		// If they just unlocked a level
		if (_quizModel.getSuccessfulQuiz()) {

			// If unlocked level
			if (_quizModel.getIsHardestLevel()) {
				Label levelUnlockedLabel = new Label();
				levelUnlockedLabel.setId("captiontext");
				levelUnlockedLabel.setText("You have unlocked level: " + AppModel.getLevelsUnlocked());
				// If highest level change text
				if (_quizModel.getLevelSelected() == AppModel.getNumLevels()) {
					levelUnlockedLabel.setText("All levels unlocked!");
				}

				topLayout.getChildren().addAll(levelUnlockedLabel);
			}
			topLayout.getChildren().addAll(playVideoButton);
		}
		// Add components and configure styling and alignment of layout
		topLayout.getChildren().addAll(innerLayout, musicBtn, returnBtn);
		topLayout.setAlignment(Pos.CENTER);

		topLayout.setBackground(AppModel.getBackground());
		topLayout.getStylesheets().add("app/scene/myStyle.css");

		return new Scene(topLayout, AppModel.getWidth(), AppModel.getHeight());
	}

	/**
	 * Sets the scene to be displayed on app model primary stage
	 */
	public void setScene() {
		Scene WordResultScene = build();
		AppModel.setScene(WordResultScene);
	}

}
