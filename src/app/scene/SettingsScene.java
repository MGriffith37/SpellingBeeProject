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

public class SettingsScene {
	private static Scene build(){
		AppModel.getWindow().setTitle("Settings");

		Label selectVoiceLbl= new Label("Select Voice to read out Quiz Words");
		selectVoiceLbl.setId("captiontext");

		//Create two radio buttons for switching between app.process.Festival voices
		RadioButton defaultBtn = new RadioButton("Default Voice");
		RadioButton nzBtn = new RadioButton("New Zealand Voice");

		defaultBtn.setId("buttontext");
		nzBtn.setId("buttontext");
		//Create group for the two radio buttons, to make them toggleable
		final ToggleGroup group = new ToggleGroup();
		defaultBtn.setToggleGroup(group);
		nzBtn.setToggleGroup(group);

		//Decide based on the current selected voice which btn to appear selected
		if(AppModel.getVoice().equals("default")){
			defaultBtn.setSelected(true);
		}else{
			nzBtn.setSelected(true);
		}

		//Set voice as default festival voice
		defaultBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setVoice("default");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		//Set voice as New Zealand festival voice
		nzBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setVoice("new_zealand");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		//Button to clear all data from application, as if starting from new
		Button resetBtn = new Button("Reset Data");

		//Button resets appModel data, resets word statistics and builds the welcome app.scene again
		resetBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setToDefault();

					/*
					 * TODO clear history of words and statistics
					 */
					FileModel.clearFiles();

					WelcomeScene.setScene();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//Main menu button to return user to main menu screen
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		//List view showing all available word lists
		ListView<String> fileList = new ListView<String>();
		ObservableList<String> customList = FXCollections.observableArrayList(FileModel.getCustomList());
		fileList.setItems(customList);
		fileList.setPrefHeight(100);
		fileList.setPrefWidth(200);

		//File choosing button to select wordlist to read for spelling quiz
		Button addFileBtn = new Button("Add New Wordlist");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		addFileBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				File file = fileChooser.showOpenDialog(AppModel.getWindow());
				customList.add(file.toString());
			}
		});

		Button selectFileBtn = new Button("Use Selected Wordlist");
		selectFileBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				String filename = fileList.getSelectionModel().getSelectedItem();
				WordFile.SPELLING_LIST.setFileName(filename);
				try{
				FileModel.initialise();
				}catch(Exception e){
					InvalidInputScene.setScene("File incompatible with VoxSpell");
				}
			}
		});
		VBox btnLayout = new VBox(20);
		btnLayout.getChildren().addAll(addFileBtn, selectFileBtn);
		btnLayout.setAlignment(Pos.CENTER);
		
		HBox fileSelectLayout = new HBox(20);
		fileSelectLayout.setAlignment(Pos.CENTER);
		fileSelectLayout.getChildren().addAll(btnLayout, fileList);

		//Sets vertical layout
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(selectVoiceLbl, defaultBtn, nzBtn, resetBtn, fileSelectLayout,returnBtn);

		layout1.getStylesheets().add("app/scene/myStyle.css");
		layout1.setBackground(AppModel.getBackground());
		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

	}
	public static void setScene(){
		Scene settingsScene = build();
		AppModel.setScene(settingsScene);
	}
}
