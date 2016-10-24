package app.scene;

import app.AppModel;
import app.model.FileModel;
import app.model.GameState;
import app.model.QuizModel;
import app.model.WordFile;
import app.model.WordState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Max Griffith on 10/10/2016. Responsible for informing user that
 * they are out of lives and giving option to submit their score with their
 * name.
 */
public class OutOfLivesScene {
	private QuizModel _quizModel;
	private GameState _gameState;
	@SuppressWarnings("unused")
	private WordState _currentWordState;

	/**
	 * Constructor to set corresponding quizmodel, game state and word state
	 */
	OutOfLivesScene() {
		_quizModel = AppModel.getQuizModel();
		_gameState = _quizModel.getGameState();
		_currentWordState = _quizModel.getWordState();
	}

	/**
	 * Builds scene to be displayed to user
	 */
	private Scene build() {

		// Label informing the user they are out of lives
		Label outcomeLabel = new Label("Out Of Lives");
		outcomeLabel.setId("subheadingtext");

		// Let user know their score
		Label scoreLabel = new Label("You got a score of " + _quizModel.getNumCorrectWords() + "!");
		scoreLabel.setId("captiontext");

		// Prompt user to enter their name to submit their score
		Label enterUserLbl = new Label("Enter your name for the Scoreboard");
		enterUserLbl.setId("captiontext");

		// horiontal layout for score submission
		HBox userLayout = new HBox();
		userLayout.setAlignment(Pos.CENTER);

		// Text field where user submits their name
		final TextField userTxt = new TextField();
		userTxt.setPromptText("Your name here");
		// Button to confirm user name to be submitted
		Button submitBtn = new Button("Submit Name");

		// Label that displays done when they submit their score
		Label doneLbl = new Label();
		doneLbl.setId("buttontext");
		userLayout.getChildren().addAll(userTxt, submitBtn, doneLbl);

		// Event to submit score with username to be written to a score file
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String name = userTxt.getText();
				// Checks name is only alphabetical characters, otherwise
				// displays warning window
				if (!name.matches("[a-zA-Z]+")) {
					InvalidInputScene.setScene("Alphabetical Characters Only");
				} else {
					if (_quizModel.getGameState().equals(GameState.ONELIFE)) {
						// If finished one life quiz, writes their score and
						// name to one life score file
						FileModel.addWordToLevel(WordFile.ONELIFESCORE, name + ":" + _quizModel.getNumCorrectWords(),
								_quizModel.getLevelSelected());
					} else {
						// If finished three lives quiz, writes their score and
						// name to three lives score file
						FileModel.addWordToLevel(WordFile.THREELIVESSCORE, name + ":" + _quizModel.getNumCorrectWords(),
								_quizModel.getLevelSelected());
					}
					// Disables the submission components once done and displays
					// the done label to user
					submitBtn.setDisable(true);
					userTxt.setDisable(true);
					doneLbl.setText("Score Submitted!");
				}
			}
		});

		// Button prompting user to go back to level select scene for the same
		// game mode
		Button levelSelectButton = new Button("Level Select");
		levelSelectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setScene();
			}
		});

		// Button prompting user to retry the same level for same game mode
		Button retryLevelButton = new Button("Retry Level");
		retryLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AppModel.startQuiz(_gameState, _quizModel.getLevelSelected());
			}
		});

		// Layout
		HBox innerLayout = new HBox();
		// add components to inner layout

		innerLayout.getChildren().addAll(levelSelectButton, retryLevelButton);

		innerLayout.setAlignment(Pos.CENTER);
		VBox outerLayout = new VBox(10);
		outerLayout.setPadding(new Insets(30, 0, 0, 0));

		// Button to return user to main menu
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		// OVerarching layout containing all components
		VBox topLayout = new VBox(10);
		topLayout.getChildren().addAll(outcomeLabel, scoreLabel, enterUserLbl, userLayout, innerLayout, returnBtn);

		topLayout.setAlignment(Pos.CENTER);

		topLayout.setBackground(AppModel.getBackground());
		topLayout.getStylesheets().add("app/scene/myStyle.css");

		return new Scene(topLayout, AppModel.getWidth(), AppModel.getHeight());
	}

	/**
	 * Sets scene using app model primary stage
	 */
	public void setScene() {
		Scene OutOfLivesScene = build();
		AppModel.setScene(OutOfLivesScene);
	}

}
