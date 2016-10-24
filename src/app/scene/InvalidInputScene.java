package app.scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Fraser McIntosh on 18/09/2016. Responsible for telling user they
 * have typed a non-alphabetic character when trying to enter a quiz word
 */
public class InvalidInputScene {

	static Stage _window;

	// Build scene with message displayed to the user
	public static Scene build(String message) {
		_window = new Stage();

		// Block user interaction with other windows until this window is dealt
		// with
		_window.initModality(Modality.APPLICATION_MODAL);
		_window.setTitle("Invalid Input");
		_window.setMinWidth(300);

		// Text message displayed as a label
		Label textLbl = new Label();
		textLbl.setText(message);
		textLbl.setId("buttontext");

		// Ok button to close pop up window
		Button closeButton = new Button("OK");
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_window.close();
			}
		});

		// Layout
		VBox layout = new VBox(10);

		layout.getStylesheets().add("app/scene/myStyle.css");

		layout.getChildren().addAll(textLbl, closeButton);
		layout.setAlignment(Pos.CENTER);

		// Scene
		return new Scene(layout, 700, 100, Color.AQUAMARINE);

	}

	/**
	 * Sets the scene and displays on the primary stage held by the AppModel
	 * class
	 */
	public static void setScene(String message) {
		Scene scene = build(message);
		_window.setScene(scene);
		// Needs to be closed before returning
		_window.showAndWait();
	}
}
