package app.scene;

import app.AppModel;
import app.model.GameState;
import app.model.Statistics;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Created by Max Griffith on 10/10/2016 Responsible for displaying the scores
 * and statistics to the user in a tab layout each tab corresponding to each
 * level, for the selected game mode
 */
public class ScoreScene {
	private static GameState _gameState;

	/**
	 * Builds scene to be displayed
	 */
	private static Scene build() {
		// set title
		AppModel.getWindow().setTitle("High Scores");

		// Create root and app.scene to be built
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		root.getStylesheets().add("app/scene/myStyle.css");
		// Create scene with css style already
		Scene scene = new Scene(root, AppModel.getWidth(), AppModel.getHeight());

		TabPane tabPane = new TabPane();

		BorderPane borderPane = new BorderPane();

		// Create table layout for each level, in each of their own separate
		// tabs
		for (int i = 1; i <= AppModel.getNumLevels(); i++) {
			// Create tab for level based on the current iteration number of for
			// loop
			Tab tab = new Tab();
			tab.setText("Level " + i);

			// Create new instance of app.model.Statistics class passing level
			// number into the object
			Statistics statsObject = new Statistics(i, _gameState);

			// Construct the table of words for current level and add to this
			// level's tab pane
			tab.setContent(statsObject.constructTableLayout());
			tab.setClosable(false);
			tabPane.getTabs().add(tab);
		}

		// bind to take available space
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());

		borderPane.setCenter(tabPane);

		// Create button to return user to main menu when finished looking at
		// stats
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		// add components to root layouts
		root.getChildren().addAll(tabPane, returnBtn);
		root.setBackground(AppModel.getBackground());
		return scene;
	}

	/**
	 * Sets scene to be displayed using primary stage belonging to app model
	 * class
	 */
	public static void setScene() {
		Scene statsScene = build();
		AppModel.setScene(statsScene);
	}

	/**
	 * Sets game state
	 */
	public static void setGameState(GameState gameState) {
		_gameState = gameState;

	}
}
