package app.scene;

import java.io.File;
import java.io.FileNotFoundException;

import app.AppModel;
import app.model.FileModel;
import app.model.WordFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * Created by Max Griffith on 13/09/2016 Responsible for managing application
 * settings
 */
public class SettingsScene {
	/**
	 * Builds scene to be displayed
	 */
	private static Scene build() {
		// Set title for window
		AppModel.getWindow().setTitle("Settings");

		// Label informing user of option to select between festival voices
		Label selectVoiceLbl = new Label("Select Voice to read out Quiz Words");
		selectVoiceLbl.setId("captiontext");

		// Create two radio buttons for switching between app.process.Festival
		// voices
		RadioButton defaultBtn = new RadioButton("Default Voice");
		RadioButton nzBtn = new RadioButton("New Zealand Voice");

		defaultBtn.setId("buttontext");
		nzBtn.setId("buttontext");
		// Create group for the two radio buttons, to make them toggleable
		final ToggleGroup group = new ToggleGroup();
		defaultBtn.setToggleGroup(group);
		nzBtn.setToggleGroup(group);

		// Decide based on the current selected voice which btn to appear
		// selected
		if (AppModel.getVoice().equals("default")) {
			defaultBtn.setSelected(true);
		} else {
			nzBtn.setSelected(true);
		}

		// Set voice as default festival voice
		defaultBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setVoice("default");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		// Set voice as New Zealand festival voice
		nzBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setVoice("new_zealand");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		// Button to clear all data from application, as if starting from new
		Button resetBtn = new Button("Reset Data");

		// Button resets appModel data, resets word statistics and builds the
		// welcome app.scene again
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setToDefault();
					FileModel.clearFiles();
					WelcomeScene.setScene();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Main menu button to return user to main menu screen
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		// List view showing all available word lists
		ListView<String> fileList = new ListView<String>();
		ObservableList<String> customList = FXCollections.observableArrayList(FileModel.getCustomList());
		fileList.setItems(customList);
		fileList.setPrefHeight(100);
		fileList.setPrefWidth(200);

		// File choosing button to select wordlist to read for spelling quiz
		Button addFileBtn = new Button("Add New Wordlist");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		// Event that adds the selected file to the list of files
		addFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = fileChooser.showOpenDialog(AppModel.getWindow());
				customList.add(file.toString());
			}
		});

		// Button that allows user to use selected word list
		Button selectFileBtn = new Button("Use Selected Wordlist");
		// Event that reads selected file and then uses those words as spelling
		// words now
		selectFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String filename = fileList.getSelectionModel().getSelectedItem();
				WordFile.SPELLING_LIST.setFileName(filename);
				try {
					FileModel.initialise();
				} catch (Exception e) {
					InvalidInputScene.setScene("File incompatible with VoxSpell");
				}
			}
		});
		// vertical layout for buttons
		VBox btnLayout = new VBox(20);
		btnLayout.getChildren().addAll(addFileBtn, selectFileBtn);
		btnLayout.setAlignment(Pos.CENTER);

		// horizontal layout for file selection
		HBox fileSelectLayout = new HBox(20);
		fileSelectLayout.setAlignment(Pos.CENTER);
		fileSelectLayout.getChildren().addAll(btnLayout, fileList);

		// Sets vertical overarching layout
		VBox topLayout = new VBox(20);
		topLayout.setAlignment(Pos.CENTER);
		topLayout.getChildren().addAll(selectVoiceLbl, defaultBtn, nzBtn, resetBtn, fileSelectLayout, returnBtn);

		topLayout.getStylesheets().add("app/scene/myStyle.css");
		topLayout.setBackground(AppModel.getBackground());
		return (new Scene(topLayout, AppModel.getWidth(), AppModel.getHeight()));

	}

	/**
	 * Sets scene to display using app model primary stage
	 */
	public static void setScene() {
		Scene settingsScene = build();
		AppModel.setScene(settingsScene);
	}
}
