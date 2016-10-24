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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LevelSelectScene {

	private static boolean _isReview;
	private static GameState _gameState;

	private static Scene build(){
		//Set title
		AppModel.getWindow().setTitle("Level Select");

		//Labels current quiz mode - either new quiz or review mode
		Label titleLbl = new Label();

		//Sets title to mode type
		switch(_gameState){
		case QUIZ:
			titleLbl.setText("Standard Quiz");
		break;
		case TIME:
			titleLbl.setText("Time Attack");
			break;
		case ONELIFE:
			titleLbl.setText("One Life");
		break;
		case THREELIVES:
			titleLbl.setText("Three Lives");
			break;
		}
		titleLbl.setId("headingtext");

		//Details instructions for user
		Label promptLbl = new Label("What level do you want to test on?");
		promptLbl.setId("captiontext");
		//Create overarching layout for this app.scene and centers it
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);

		//Layout for the 11 buttons
		GridPane buttonLayout = new GridPane();
		buttonLayout.setPadding(new Insets(20,20,20,20));
		buttonLayout.setVgap(15);
		buttonLayout.setHgap(20);
		int j = 0;
		//Generates a level button for each level, one by one
		for(int i = 1; i <= AppModel.getNumLevels(); i++){
			//Sets the text of button
			final Button levelBtn = new Button("Level "+i);
			levelBtn.setAlignment(Pos.CENTER);
			//Generates event for the current button
			levelBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					//Gets the level that the button corresponds to
					String str = levelBtn.getText().replaceAll("\\D+","");
					int level = Integer.parseInt(str);
					AppModel.startQuiz(_gameState, level);
				}
			});

			//Disables button if it corresponds to a level that is not unlocked yet
			if(i > AppModel.getLevelsUnlocked()){
				levelBtn.setDisable(true);
			}
			//Adds button to the button layout
			GridPane.setConstraints(levelBtn, ((i -1)%3), j);
			buttonLayout.getChildren().add(levelBtn);
			j = i /3;
		}
		//Centers button layout
		buttonLayout.setAlignment(Pos.CENTER);

		//Create button to return user to main menu
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
        });
        
		//Adds all components to root layout and returns the app.scene containing the layout
		root.getChildren().addAll(titleLbl, promptLbl,buttonLayout, returnBtn);
		root.setBackground(AppModel.getBackground());

		root.getStylesheets().add("app/scene/myStyle.css");
		
		return(new Scene(root, AppModel.getWidth(),AppModel.getHeight()));
	}

	//Sets the app.scene of the window as the Level Select Scene
	public static void setScene(){
		Scene lvlSelectScene = build();
		AppModel.setScene(lvlSelectScene);
	}

	//Allows this class to know if it is in review mode or not
	public static void setGameState(GameState gameState){
		_gameState = gameState;
	}

}
