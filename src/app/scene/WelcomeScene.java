package app.scene;

import java.io.FileNotFoundException;

import app.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Max Griffith on 15/09/2016 Responsible for allowing user to unlock
 * levels on the first runthrough of the app
 */
public class WelcomeScene {
	/**
	 * Builds scene to be displayed
	 */
	private static Scene build() throws Exception {
		// set title
		AppModel.getWindow().setTitle("Welcome");

		// Create app.scene labels
		Label welcomeLbl = new Label("Welcome to VoxSpell!");
		welcomeLbl.setId("subheadingtext");
		Label selectLbl = new Label("Select the starting level!");
		selectLbl.setId("captiontext");
		// create drop down box containing all available levels
		final ComboBox<String> comboBox = new ComboBox<String>();
		for (int i = 1; i <= AppModel.getNumLevels(); i++) {
			comboBox.getItems().add("Level " + i);
		}

		// Is modifiable to fit the window appropriately
		comboBox.setVisibleRowCount(4);

		// Create button to select the level
		Button selectBtn = new Button("Select");

		// when selectbutton is clicked, read selected value from drop down box
		selectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (comboBox.getValue() == null) {
					try {
						setLevelNo(1 + "");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					try {
						setLevelNo(comboBox.getValue());
						AppModel.setNotFirstTime();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				LevelUnlockScene.setScene();
			}
		});

		// Set vertical layout with all components aligned to center
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(welcomeLbl, selectLbl, comboBox, selectBtn);

		layout1.setBackground(AppModel.getBackground());
		layout1.getStylesheets().add("app/scene/myStyle.css");
		return (new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));
	}

	/**
	 * Sets scene using primary stage belonging to app model class
	 */
	public static void setScene() throws Exception {
		Scene welcomeScene = build();
		AppModel.setScene(welcomeScene);
	}

	/**
	 * Helper method to obtain int value corresponding to level
	 */
	private static void setLevelNo(String levelString) throws FileNotFoundException {
		String str = levelString.replaceAll("\\D+", "");
		int selectedLevel = Integer.parseInt(str);

		// Update app.AppModel with new level-unlocked value
		AppModel.setLevelsUnlocked(selectedLevel);
	}

}
