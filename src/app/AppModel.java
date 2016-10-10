package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import app.model.FileModel;
import app.model.QuizModel;
import app.model.QuizState;
import app.scene.EnterWordScene;
import app.scene.MainMenuScene;
import app.scene.NoWordsScene;
import app.scene.WelcomeScene;


public class AppModel extends Application{

	private static Boolean _isFirstTime;
	private static int _levelsUnlocked;
	private static String _voice;
	
	private static Stage _window;
	private static QuizModel _quizModel;
	
	private static Background _background;
	
	private final static Color DEFAULT_FONT_COLOUR = Color.RED;
	private final static Font DEFAULT_HEADING_FONT = Font.font("Verdana",FontWeight.BOLD, 35);
	private final static Font DEFAULT_TEXT_FONT = Font.font("Verdana",FontWeight.BOLD, 16);
	//"500" is a placeholder for the actual default dimensions
	private final static int DEFAULT_WIDTH = 832;
	private final static int DEFAULT_HEIGHT = 600;

	private static int _numLevels = 11;

	/*
	 * Reads in the 3 settings values from .settings.txt file. 
	 * These values will persist even if the application is closed.
	 */
	private static void setup(){
		//Initialise files
		FileModel.initialise();
		setNumLevels(FileModel.calcNumLevels());
		try{
			BufferedReader reader = new BufferedReader(new FileReader(".app_files/.settings.txt"));
			_isFirstTime = Boolean.parseBoolean(reader.readLine());
			_levelsUnlocked = Integer.parseInt(reader.readLine());
			_voice = reader.readLine();
			setBackground();
			reader.close();
		}catch(FileNotFoundException e){
			//worth creating an alert box to inform user that .setting.txt file is missing?
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Getter methods
	public static int getLevelsUnlocked(){
		return _levelsUnlocked;
	}
	public static Boolean isItFirstTime(){
		return _isFirstTime;
	}
	public static String getVoice(){
		return _voice;
	}
	public static QuizModel getQuizModel(){
		return _quizModel;
	}
	public static Stage getWindow(){
		return _window;
	}
	public static Color getFontColor(){
		return DEFAULT_FONT_COLOUR;
	}
	public static Font getHeadingFont(){
		return DEFAULT_HEADING_FONT;
	}
	public static Font getTextFont(){
		return DEFAULT_TEXT_FONT;
	}
	public static int getWidth(){
		return DEFAULT_WIDTH;
	}
	public static int getHeight(){
		return DEFAULT_HEIGHT;
	}
	public static int getNumLevels(){
		return _numLevels;
	}
	public static Background getBackground(){
		return _background;
	}

	//Setter methods
	public static void setLevelsUnlocked(int value) throws FileNotFoundException{
		_levelsUnlocked = value;
		updateTxtFile();
	}
	public static void setNotFirstTime() throws FileNotFoundException{
		_isFirstTime = false;
		updateTxtFile();
	}
	public static void setVoice(String voice) throws FileNotFoundException{
		_voice = voice;
		updateTxtFile();
	}
	public static QuizState setQuizModel(boolean isReview, int levelSelected){
		_quizModel = new QuizModel(isReview, levelSelected);
		return _quizModel.start();
	}
	//to be invoked from start() method that starts the GUI
	public static void setWindow(Stage window){
		_window = window;
	}
	public static void setScene(Scene scene){
		//String image = AppModel.class.getResource("chalkboard.png").toExternalForm();
		
		_window.setScene(scene);
		
		_window.show();
	}
	public static void setToDefault() throws FileNotFoundException{
		_isFirstTime = true;
		_levelsUnlocked = 0;
		_voice = "default";
		updateTxtFile();
	}

	public static void setBackground(){
		File file = new File(".media/field.jpg");
		Image image = new Image(file.toURI().toString());
		
		BackgroundSize backgroundSize = new BackgroundSize(100,100,true,true,true,false);
		BackgroundImage backgroundImg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		_background = new Background(backgroundImg);
	}
	public static void setNumLevels(int numLevels) {
		_numLevels = numLevels;
	}
	
	//Overwrites .settings.txt file with updated field values 
	public static void updateTxtFile() throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(".app_files/.settings.txt");
		writer.println(_isFirstTime.toString());
		writer.println(_levelsUnlocked);
		writer.println(_voice);
		writer.close();
	}

	public static void main(String[] args){
		setup();
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
//		StackPane root = new StackPane();
//		
//		File file = new File("src/app/chalkboard.png");
//		Image image = new Image(file.toURI().toString());
//		
//		BackgroundSize backgroundSize = new BackgroundSize(AppModel.getWidth(),AppModel.getHeight(),true,true,true,false);
//		BackgroundImage backgroundImg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
//		Background background = new Background(backgroundImg);
//		root.setBackground(background);
//		System.out.println("path: "+image);
//		root.setStyle(" -fx-background-imageL url('" + image +"'); " 
//					+ "fx-background-positionL center center; "
//					+ "fx-background-repeat: stretch;");
//		primaryStage.setScene(new Scene(root,300,250));
//		primaryStage.show();
		_window = primaryStage;
		if(_isFirstTime){
			WelcomeScene.setScene();
		}else{
			MainMenuScene.setScene();
		}
	}

	public static void startQuiz( boolean isReview, int level) {
		//Initialises new quiz app.model object with the selected level
		QuizState quizState = AppModel.setQuizModel(isReview,level);

		//If Quiz is ready
		// Initialises new app.scene.EnterWordScene app.scene to be built next
		if(quizState.equals(QuizState.READY)) {
			EnterWordScene wordScene = new EnterWordScene();
			wordScene.setScene();
			// Else if no words display no words app.scene
		} else if (quizState.equals(QuizState.NO_WORDS)){
			NoWordsScene.setScene();
		}
	}
}
