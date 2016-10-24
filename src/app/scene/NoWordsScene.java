package app.scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Fraser McIntosh on 18/09/2016. Responsible for telling user that
 * there are no words available in review mode This occurs when no words have
 * been failed for a level
 */
public class NoWordsScene {

	static Stage _window;

	/**
	 * Builds scene to be displayed
	 */
	public static Scene build() {
		// Builds new window, separate from app model window
		_window = new Stage();

		// Block user interaction with other windows until this window is
		// dealt with
		_window.initModality(Modality.APPLICATION_MODAL);
		_window.setTitle("No Words");
		_window.setMinWidth(300);

		// Components
		Label label = new Label();
		label.setText("There are no words to test in this level");
		label.setId("buttontext");
		// Button that closes pop up window when clicked
		Button closeButton = new Button("OK");
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_window.close();
			}
		});

		// Layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		layout.getStylesheets().add("app/scene/myStyle.css");
		// Scene
		return new Scene(layout, 600, 100);

	}

	/**
	 * Sets scene as this current one
	 */

	public static void setScene() {
		Scene scene = build();
		_window.setScene(scene);
		// Needs to be closed before returning
		_window.showAndWait();
	}
}
