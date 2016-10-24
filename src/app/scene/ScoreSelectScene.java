package app.scene;

import java.io.File;

import app.AppModel;
import app.model.GameState;
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

public class ScoreSelectScene {
	private final static int BTN_WIDTH=200;
	private final static int BTN_HEIGHT=40;

	private static Scene build(){
		//Sets title and labels for the window
		AppModel.getWindow().setTitle("Select High Score");

		//Creates vertical layout with the four buttons
		VBox layout1 = new VBox();
		layout1.setAlignment(Pos.CENTER);

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);
		
		Label title = new Label("Select the Mode to display statistics for:");
		title.setId("captiontext");
		

		//Creates new quiz button, that will start a new quiz when clicked
		Button quizBtn = new Button("Standard Quiz Stats");
		quizBtn.setMinWidth(BTN_WIDTH);
		quizBtn.setMinHeight(BTN_HEIGHT);
		quizBtn.getStyleClass().add("btn");
		quizBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				StatisticsScene.setScene();
			}
		});

		//Creates review button, that will start a new review quiz when clicked
		Button timeBtn = new Button("Review Quiz");
		timeBtn.setMinWidth(BTN_WIDTH);
		timeBtn.setMinHeight(BTN_HEIGHT);
		timeBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.REVIEW);
				ScoreScene.setScene();
			}
		});


		//Creates review button, that will start a new review quiz when clicked
		Button oneLifeBtn = new Button("One Life");
		oneLifeBtn.setMinWidth(BTN_WIDTH);
		oneLifeBtn.setMinHeight(BTN_HEIGHT);
		oneLifeBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.ONELIFE);

				ScoreScene.setScene();
			}
		});


		//Creates review button, that will start a new review quiz when clicked
		Button threeLivesBtn = new Button("Three Lives");
		threeLivesBtn.setMinWidth(BTN_WIDTH);
		threeLivesBtn.setMinHeight(BTN_HEIGHT);
		threeLivesBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ScoreScene.setGameState(GameState.THREELIVES);
				
				ScoreScene.setScene();
			}
		});
		
		Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                MainMenuScene.setScene();
            }
        });

		gridPane.add(quizBtn,0,0);
		gridPane.add(timeBtn, 1, 0);
		gridPane.add(oneLifeBtn, 0, 1);
		gridPane.add(threeLivesBtn, 1, 1);
		
		layout1.getStylesheets().add("app/scene/myStyle.css");

		gridPane.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(title, gridPane,returnBtn);
		layout1.setBackground(AppModel.getBackground());
		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));
	}

	//Builds scene
	public static void setScene(){
		Scene ScoreSelectScene = build();
		AppModel.setScene(ScoreSelectScene);
	}
}

