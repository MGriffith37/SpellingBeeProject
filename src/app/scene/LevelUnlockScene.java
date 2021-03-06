package app.scene;

import app.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * Created by Max Griffith on 15/09/2016 Responsible for allowing user to
 * confirm the levels unlocked at the very first runthrough of app
 */
public class LevelUnlockScene {
	/**
	 * Builds scene to be displayed
	 */
	private static Scene build() {
		Label confirmLbl = new Label();

		// If only level 1 is selected, grammar is unique so window title and
		// label wording is set accordingly
		if (AppModel.getLevelsUnlocked() == 1) {
			AppModel.getWindow().setTitle("Level Unlocked");
			confirmLbl.setText("You have unlocked Level 1.");
		} else {// If multiple levels are unlocked, window title and labels are
			// set accordingly

			AppModel.getWindow().setTitle("Levels Unlocked");
			confirmLbl.setText("You have unlocked Level " + AppModel.getLevelsUnlocked()
					+ ".\nYou may now access Levels 1 to " + AppModel.getLevelsUnlocked() + ".");
			confirmLbl.setTextAlignment(TextAlignment.CENTER);
		}
		confirmLbl.setId("captiontext");
		Button okBtn = new Button("Okay");
		// Sets action event to build and set new app.scene as the main menu
		// app.scene, once okay button is clicked
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		// Sets vertical layout
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(confirmLbl, okBtn);

		layout1.setBackground(AppModel.getBackground());
		layout1.getStylesheets().add("app/scene/myStyle.css");
		return (new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));
	}

	/**
	 * Sets this scene to be displayed in the primary stage held by AppModel
	 * class
	 */
	public static void setScene() {
		Scene levelUnlockScene = build();
		AppModel.setScene(levelUnlockScene);
	}
}
