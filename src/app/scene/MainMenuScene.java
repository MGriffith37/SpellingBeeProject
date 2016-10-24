package app.scene;

import app.AppModel;
import app.model.GameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Created by Max Griffith on 16/09/2016 Reponsible for building the main menu
 * scene to be displayed to user to navigate the GUI with ease
 */
public class MainMenuScene {
	private final static int BTN_WIDTH = 200;
	private final static int BTN_HEIGHT = 40;

	/**
	 * Builds scene to be displayed
	 */
	private static Scene build() {
		// Sets title and labels for the window
		AppModel.getWindow().setTitle("Main Menu");

		// Creates overarching vertical layout for the labels and buttons
		VBox layout1 = new VBox();
		layout1.setAlignment(Pos.CENTER);

		// Creates grid layout for the six buttons
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);

		// Main title label
		Label title = new Label("VoxSpell");
		title.setId("headingtext");
		title.setTranslateY(-40);

		// Help box that displays useful information to user when hovering over
		// one of six menu buttons
		TextArea description = new TextArea();
		description.setMaxWidth(260);
		description.setMaxHeight(40);
		description.setEditable(false);

		// Creates new quiz button, that will start a new quiz when clicked
		Button quizBtn = new Button("Standard Quiz");
		quizBtn.setMinWidth(BTN_WIDTH);
		quizBtn.setMinHeight(BTN_HEIGHT);
		quizBtn.getStyleClass().add("btn");
		// Builds level select scene for standard quiz and displays it
		quizBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.QUIZ);
				LevelSelectScene.setScene();
			}
		});
		// Event that triggers when mouse hovers over button, to highlight it
		// and change the description text field
		quizBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				quizBtn.setEffect(new DropShadow());
				description.setText("Spell 10 words from a selected level.\nSpell 9 or more to get a video prize!");
			}
		});
		// Event that removes highlighting of button and description
		quizBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				quizBtn.setEffect(null);
				description.clear();
			}
		});

		// Creates review button, that will start a new review quiz when clicked
		Button reviewBtn = new Button("Review Quiz");
		reviewBtn.setMinWidth(BTN_WIDTH);
		reviewBtn.setMinHeight(BTN_HEIGHT);
		// Builds level select scene for review quiz and displays it
		reviewBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.REVIEW);
				LevelSelectScene.setScene();
			}
		});
		// Event that triggers when mouse hovers over button, to highlight it
		// and change the description text field
		reviewBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				reviewBtn.setEffect(new DropShadow());
				description.setText("Spell previously mispelt words.\nMaster those nasty words!");
			}
		});
		// Event that removes highlighting of button and description
		reviewBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				reviewBtn.setEffect(null);
				description.clear();
			}
		});

		// Creates one life button, that will start a new one life quiz when
		// clicked
		Button oneLifeBtn = new Button("One Life");
		oneLifeBtn.setMinWidth(BTN_WIDTH);
		oneLifeBtn.setMinHeight(BTN_HEIGHT);
		// Builds level select scene for one life quiz and displays it
		oneLifeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.ONELIFE);

				LevelSelectScene.setScene();
			}
		});
		// Event that triggers when mouse hovers over button, to highlight it
		// and change the description text field
		oneLifeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				oneLifeBtn.setEffect(new DropShadow());
				description.setText("Spell words until you run out of lives.\nGo for a high score!");
			}
		});
		// Event that removes highlighting of button and description
		oneLifeBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				oneLifeBtn.setEffect(null);
				description.clear();
			}
		});

		// Creates three lives button, that will start a new three lives quiz
		// when clicked
		Button threeLivesBtn = new Button("Three Lives");
		threeLivesBtn.setMinWidth(BTN_WIDTH);
		threeLivesBtn.setMinHeight(BTN_HEIGHT);
		// Builds level select scene for three lives quiz and displays it
		threeLivesBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.THREELIVES);

				LevelSelectScene.setScene();
			}
		});
		// Event that triggers when mouse hovers over button, to highlight it
		// and change the description text field
		threeLivesBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				threeLivesBtn.setEffect(new DropShadow());
				description.setText("Spell words until you run out of lives.\nGo for a high score!");
			}
		});
		// Event that removes highlighting of button and description
		threeLivesBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				threeLivesBtn.setEffect(null);
				description.clear();
			}
		});

		// Creates statistics/score button, that will present word
		// statistics/score when
		// clicked (scores are for one life/three lives mode)
		Button hiscoreBtn = new Button("Scores and Stats");
		hiscoreBtn.setMinWidth(BTN_WIDTH);
		hiscoreBtn.setMinHeight(BTN_HEIGHT);
		// Builds mode select scene for different types of statistics and
		// displays it
		hiscoreBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScoreSelectScene.setScene();
			}

		});
		// Event that triggers when mouse hovers over button, to highlight it
		// and change the description text field
		hiscoreBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				hiscoreBtn.setEffect(new DropShadow());
				description.setText("Look at your High Scores!\nLook at your Word Statistics!");
			}
		});
		// Event that removes highlighting of button and description
		hiscoreBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				hiscoreBtn.setEffect(null);
				description.clear();
			}
		});

		// Creates settings button, that will take user to settings menu when
		// clicked
		Button settingsBtn = new Button("Settings");
		settingsBtn.setMinWidth(BTN_WIDTH);
		settingsBtn.setMinHeight(BTN_HEIGHT);
		// Builds settings scene and displays it
		settingsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SettingsScene.setScene();
			}

		});
		// Event that triggers when mouse hovers over button, to highlight it
		// and change the description text field
		settingsBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				settingsBtn.setEffect(new DropShadow());
				description.setText("Configure the application settings.");
			}
		});
		// Event that removes highlighting of button and description
		settingsBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				settingsBtn.setEffect(null);
				description.clear();
			}
		});

		// Add components to quiz button with their position (col, row)
		gridPane.add(quizBtn, 0, 0);
		gridPane.add(reviewBtn, 1, 0);
		gridPane.add(oneLifeBtn, 0, 1);
		gridPane.add(threeLivesBtn, 1, 1);
		gridPane.add(hiscoreBtn, 0, 2);
		gridPane.add(settingsBtn, 1, 2);

		// Adds style to layout and alignment
		layout1.getStylesheets().add("app/scene/myStyle.css");
		gridPane.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(title, gridPane, description);
		layout1.setBackground(AppModel.getBackground());

		return (new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

	}

	/**
	 * Sets scene to be displayed in window held by app model class
	 */
	public static void setScene() {
		Scene mainMenuScene = build();
		AppModel.setScene(mainMenuScene);
	}
}
