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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Create by Max Griffith on 10/10/2016 Responsible for allowing user to select
 * the game mode score/stats to be displayed
 */
public class ScoreSelectScene {
	private final static int BTN_WIDTH = 200;
	private final static int BTN_HEIGHT = 40;

	/**
	 * Builds scene to be displayed
	 */
	private static Scene build() {
		// Sets title and labels for the window
		AppModel.getWindow().setTitle("Select High Score");

		// Creates overarching vertical layout for all components
		VBox topLayout = new VBox();
		topLayout.setAlignment(Pos.CENTER);

		// Grid layout with the four buttons corresponding to each game mode
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);

		// Set informational text label
		Label title = new Label("Select the Mode to display statistics for:");
		title.setId("captiontext");

		// Creates new quiz button, that will show standard quiz stats when
		// clicked
		Button quizBtn = new Button("Standard Quiz");
		quizBtn.setMinWidth(BTN_WIDTH);
		quizBtn.setMinHeight(BTN_HEIGHT);
		quizBtn.getStyleClass().add("btn");
		quizBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.QUIZ);
				ScoreScene.setScene();
			}
		});

		// Creates review button, that will also show standard quiz stats when
		// clicked(as these are the same stats)
		Button reviewBtn = new Button("Review Quiz");
		reviewBtn.setMinWidth(BTN_WIDTH);
		reviewBtn.setMinHeight(BTN_HEIGHT);
		reviewBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.REVIEW);
				ScoreScene.setScene();
			}
		});

		// Creates one life button, that will display one life scores when
		// clicked
		Button oneLifeBtn = new Button("One Life");
		oneLifeBtn.setMinWidth(BTN_WIDTH);
		oneLifeBtn.setMinHeight(BTN_HEIGHT);
		oneLifeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.ONELIFE);

				ScoreScene.setScene();
			}
		});

		// Creates three lives button that will display three lives scores when
		// clicked
		Button threeLivesBtn = new Button("Three Lives");
		threeLivesBtn.setMinWidth(BTN_WIDTH);
		threeLivesBtn.setMinHeight(BTN_HEIGHT);
		threeLivesBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.THREELIVES);

				ScoreScene.setScene();
			}
		});

		// Main menu button to return user to main menu when clicked
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		// Add components to grid layout
		gridPane.add(quizBtn, 0, 0);
		gridPane.add(reviewBtn, 1, 0);
		gridPane.add(oneLifeBtn, 0, 1);
		gridPane.add(threeLivesBtn, 1, 1);

		// Stylise layout with css style and align everything
		topLayout.getStylesheets().add("app/scene/myStyle.css");
		gridPane.setAlignment(Pos.CENTER);
		topLayout.getChildren().addAll(title, gridPane, returnBtn);
		topLayout.setBackground(AppModel.getBackground());

		return (new Scene(topLayout, AppModel.getWidth(), AppModel.getHeight()));
	}

	/**
	 * Sets scene to be displayed using app model primary stage
	 */
	public static void setScene() {
		Scene ScoreSelectScene = build();
		AppModel.setScene(ScoreSelectScene);
	}
}
