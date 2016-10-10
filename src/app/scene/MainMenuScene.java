package app.scene;

import java.io.File;

import app.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenuScene {
	private final static int BTN_WIDTH=200;
	private final static int BTN_HEIGHT=40;

	private static Scene build(){
		//Sets title and labels for the window
		AppModel.getWindow().setTitle("Main Menu");

		//Creates vertical layout with the four buttons
		VBox layout1 = new VBox();
		layout1.setAlignment(Pos.CENTER);

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);
		
		Label title = new Label("VoxSpell");
		title.setId("headingtext");
		title.setTranslateY(-40);

		//Creates new quiz button, that will start a new quiz when clicked
		Button quizBtn = new Button("Standard Quiz");
		quizBtn.setMinWidth(BTN_WIDTH);
		quizBtn.setMinHeight(BTN_HEIGHT);
		quizBtn.getStyleClass().add("btn");
		quizBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setIsReview(false);
				LevelSelectScene.setScene();
			}
		});

		//Creates review button, that will start a new review quiz when clicked
		Button timeBtn = new Button("Time Attack");
		timeBtn.setMinWidth(BTN_WIDTH);
		timeBtn.setMinHeight(BTN_HEIGHT);
		timeBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setIsReview(true);
				LevelSelectScene.setScene();
			}
		});


		//Creates review button, that will start a new review quiz when clicked
		Button oneLifeBtn = new Button("One Life");
		oneLifeBtn.setMinWidth(BTN_WIDTH);
		oneLifeBtn.setMinHeight(BTN_HEIGHT);
		oneLifeBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setIsReview(true);
				LevelSelectScene.setScene();
			}
		});


		//Creates review button, that will start a new review quiz when clicked
		Button threeLivesBtn = new Button("Three Lives");
		threeLivesBtn.setMinWidth(BTN_WIDTH);
		threeLivesBtn.setMinHeight(BTN_HEIGHT);
		threeLivesBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setIsReview(true);
				LevelSelectScene.setScene();
			}
		});


		//Creates statistics button, that will present word statistics when clicked
		Button hiscoreBtn = new Button("High Score");
		hiscoreBtn.setMinWidth(BTN_WIDTH);
		hiscoreBtn.setMinHeight(BTN_HEIGHT);
		hiscoreBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				StatisticsScene.setScene();
			}

		});

		//Creates settings button, that will take user to settings menu when clicked
		Button settingsBtn = new Button("Settings");
		settingsBtn.setMinWidth(BTN_WIDTH);
		settingsBtn.setMinHeight(BTN_HEIGHT);
		settingsBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				SettingsScene.setScene();
			}

		});



		gridPane.add(quizBtn,0,0);
		gridPane.add(timeBtn, 1, 0);
		gridPane.add(oneLifeBtn, 0, 1);
		gridPane.add(threeLivesBtn, 1, 1);
		gridPane.add(hiscoreBtn, 0, 2);
		gridPane.add(settingsBtn, 1, 2);
		
		layout1.getStylesheets().add("app/scene/myStyle.css");

		gridPane.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(title, gridPane);
		layout1.setBackground(AppModel.getBackground());
		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

	}

	//Builds scene
	public static void setScene(){
		Scene mainMenuScene = build();
		AppModel.setScene(mainMenuScene);
	}
}
