package app.scene;




import app.AppModel;
import app.model.FileModel;
import app.model.GameState;
import app.model.QuizModel;
import app.model.WordFile;
import app.model.WordState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by Fraser McIntosh on 19/09/2016.
 */
public class OutOfLivesScene {
    private QuizModel _quizModel;
    private GameState _gameState;
    @SuppressWarnings("unused")
	private WordState _currentWordState;

    OutOfLivesScene() {
        _quizModel = AppModel.getQuizModel();
        _gameState = _quizModel.getGameState();
        _currentWordState = _quizModel.getWordState();
    }

    // Only get to this app.scene if quiz still going, so don't need to check that (or do we??)
    private Scene build() {

        //Label informing the user if the answered correctly or not
        Label outcomeLabel = new Label("Out Of Lives");
        outcomeLabel.setId("subheadingtext");
       

        
        // Let user know their score
        Label scoreLabel = new Label("You got a score of " + _quizModel.getNumCorrectWords()+"!");
        scoreLabel.setId("captiontext");
        
        Label enterUserLbl = new Label("Enter your name for the Scoreboard");
        enterUserLbl.setId("captiontext");
        
        HBox userLayout = new HBox();
        userLayout.setAlignment(Pos.CENTER);
        final TextField userTxt = new TextField();
        userTxt.setPromptText("Your name here");
        Button submitBtn = new Button("Submit Name");
        userLayout.getChildren().addAll(userTxt, submitBtn);
        
        submitBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				String name = userTxt.getText();
				if(_quizModel.getGameState().equals(GameState.ONELIFE)){
				FileModel.addWordToLevel(WordFile.ONELIFESCORE, name + ": "+_quizModel.getNumCorrectWords(), _quizModel.getLevelSelected());
				}else{
					FileModel.addWordToLevel(WordFile.THREELIVESSCORE, name + ": "+_quizModel.getNumCorrectWords(), _quizModel.getLevelSelected());
				}
			}
        });
        // Button that either says "Next Word", or "Try Again", depending
        // on whether the previous answer was correct or not
        Button levelSelectButton = new Button("Level Select");
        levelSelectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LevelSelectScene.setScene();
            }
        });

        Button retryLevelButton = new Button("Retry Level");
        retryLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppModel.startQuiz(_gameState, _quizModel.getLevelSelected());
            }
        });

       
        //Layout
        HBox innerLayout = new HBox();
        // add components to inner layout

            innerLayout.getChildren().addAll(levelSelectButton, retryLevelButton);
        
        innerLayout.setAlignment(Pos.CENTER);
        VBox outerLayout = new VBox(10);
        outerLayout.setPadding(new Insets(30, 0, 0, 0));

        // Goes in outer layout
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                MainMenuScene.setScene();
            }
        });

        //Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(outcomeLabel, scoreLabel,enterUserLbl,userLayout);
        
        layout.getChildren().addAll(innerLayout, returnBtn);
        layout.setAlignment(Pos.CENTER);

        layout.setBackground(AppModel.getBackground());
        layout.getStylesheets().add("app/scene/myStyle.css");
        
        return new Scene(layout, AppModel.getWidth(), AppModel.getHeight());
    }

    public void setScene() {
        Scene OutOfLivesScene = build();
        AppModel.setScene(OutOfLivesScene);
    }


}
