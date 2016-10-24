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

		TextArea description = new TextArea();
		description.setMaxWidth(260);
		description.setMaxHeight(40);
		description.setEditable(false);
		
		//Creates new quiz button, that will start a new quiz when clicked
		Button quizBtn = new Button("Standard Quiz");
		quizBtn.setMinWidth(BTN_WIDTH);
		quizBtn.setMinHeight(BTN_HEIGHT);
		quizBtn.getStyleClass().add("btn");
		quizBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.QUIZ);
				LevelSelectScene.setScene();
			}
		});
		quizBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				quizBtn.setEffect(new DropShadow());
				description.setText("Spell 10 words from a selected level.\nSpell 9 or more to get a video prize!");
			}
		});
		quizBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				quizBtn.setEffect(null);
				description.clear();
			}
		});

		//Creates review button, that will start a new review quiz when clicked
		Button timeBtn = new Button("Review Quiz");
		timeBtn.setMinWidth(BTN_WIDTH);
		timeBtn.setMinHeight(BTN_HEIGHT);
		timeBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.REVIEW);
				LevelSelectScene.setScene();
			}
		});
		timeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				timeBtn.setEffect(new DropShadow());
				description.setText("Spell previously mispelt words.\nMaster those nasty words!");
			}
		});
		timeBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				timeBtn.setEffect(null);
				description.clear();
			}
		});
		
		

		//Creates review button, that will start a new review quiz when clicked
		Button oneLifeBtn = new Button("One Life");
		oneLifeBtn.setMinWidth(BTN_WIDTH);
		oneLifeBtn.setMinHeight(BTN_HEIGHT);
		oneLifeBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.ONELIFE);

				LevelSelectScene.setScene();
			}
		});
		oneLifeBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				oneLifeBtn.setEffect(new DropShadow());
				description.setText("Spell words until you run out of lives.\nGo for a high score!");
			}
		});
		oneLifeBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				oneLifeBtn.setEffect(null);
				description.clear();
			}
		});
		
		

		//Creates review button, that will start a new review quiz when clicked
		Button threeLivesBtn = new Button("Three Lives");
		threeLivesBtn.setMinWidth(BTN_WIDTH);
		threeLivesBtn.setMinHeight(BTN_HEIGHT);
		threeLivesBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setGameState(GameState.THREELIVES);
				
				LevelSelectScene.setScene();
			}
		});
		

		threeLivesBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				threeLivesBtn.setEffect(new DropShadow());
				description.setText("Spell words until you run out of lives.\nGo for a high score!");
			}
		});
		threeLivesBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				threeLivesBtn.setEffect(null);
				description.clear();
			}
		});
		
		//Creates statistics button, that will present word statistics when clicked
		Button hiscoreBtn = new Button("High Score");
		hiscoreBtn.setMinWidth(BTN_WIDTH);
		hiscoreBtn.setMinHeight(BTN_HEIGHT);
		hiscoreBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				ScoreSelectScene.setScene();
			}

		});
		hiscoreBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				hiscoreBtn.setEffect(new DropShadow());
				description.setText("Look at your high scores!");
			}
		});
		hiscoreBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				hiscoreBtn.setEffect(null);
				description.clear();
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
		settingsBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				settingsBtn.setEffect(new DropShadow());
				description.setText("Configure the application settings.");
			}
		});
		settingsBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				settingsBtn.setEffect(null);
				description.clear();
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
		layout1.getChildren().addAll(title, gridPane,description);
		layout1.setBackground(AppModel.getBackground());
		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

	}

	//Builds scene
	public static void setScene(){
		Scene mainMenuScene = build();
		AppModel.setScene(mainMenuScene);
	}
}
