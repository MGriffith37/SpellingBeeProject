package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import app.model.FileModel;
import app.model.GameState;
import app.model.QuizModel;
import app.model.QuizState;
import app.model.WordFile;
import app.scene.EnterWordScene;
import app.scene.MainMenuScene;
import app.scene.NoWordsScene;
import app.scene.WelcomeScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

/**
 * Created by Max Griffith on 10/09/2016
 * AppModel contains all information and states to maintain the
 * application itself. Sets up and initialises the GUI All classes
 * report to this class and all scenes are built and then sent to
 * AppModel to be displayed.
 */
public class AppModel extends Application {

	// settings variables
	private static Boolean _isFirstTime;
	private static int _levelsUnlocked;
	private static String _voice;

	// GUI variables
	private static Stage _window;
	private static QuizModel _quizModel;
	private static Background _background;

	// Default GUI dimensions
	private final static int DEFAULT_WIDTH = 832;
	private final static int DEFAULT_HEIGHT = 600;

	private static int _numLevels;

	/**
	 * Reads in the 3 settings values from .settings.txt file. These values will
	 * persist even if the application is closed. Sets up file model class to
	 * parse in word information from external txt files
	 */
	private static void setup() {
		// Initialise files
		FileModel.initialise();
		FileModel.addToCustomList(WordFile.SPELLING_LIST.toString());
		setNumLevels(FileModel.calcNumLevels());

		// Read settings info from external txt file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(".app_files/.settings.txt"));
			_isFirstTime = Boolean.parseBoolean(reader.readLine());
			_levelsUnlocked = Integer.parseInt(reader.readLine());
			_voice = reader.readLine();
			setBackground();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Getter methods-------------------------------------------------
	/**
	 * gets level unlocked
	 */
	public static int getLevelsUnlocked() {
		return _levelsUnlocked;
	}

	/**
	 * gets is first time boolean
	 */
	public static Boolean isItFirstTime() {
		return _isFirstTime;
	}

	/**
	 * get festival voice
	 */
	public static String getVoice() {
		return _voice;
	}

	/**
	 * get quiz model
	 */
	public static QuizModel getQuizModel() {
		return _quizModel;
	}

	/**
	 * get window
	 */
	public static Stage getWindow() {
		return _window;
	}

	/**
	 * get default width
	 */
	public static int getWidth() {
		return DEFAULT_WIDTH;
	}

	/**
	 * get default height
	 */
	public static int getHeight() {
		return DEFAULT_HEIGHT;
	}

	/**
	 * get number of levels
	 */
	public static int getNumLevels() {
		return _numLevels;
	}

	/**
	 * get background
	 */
	public static Background getBackground() {
		return _background;
	}

	// Setter
	// methods-----------------------------------------------------------------------
	/**
	 * Sets level unlocked value
	 */
	public static void setLevelsUnlocked(int value) throws FileNotFoundException {
		_levelsUnlocked = value;
		updateTxtFile();
	}

	/**
	 * Sets the isfirsttime boolean to false
	 */
	public static void setNotFirstTime() throws FileNotFoundException {
		_isFirstTime = false;
		updateTxtFile();
	}

	/**
	 * Sets festival voice
	 */
	public static void setVoice(String voice) throws FileNotFoundException {
		_voice = voice;
		updateTxtFile();
	}

	/**
	 * Sets quiz model
	 */
	public static QuizState setQuizModel(GameState gameState, int levelSelected) {
		_quizModel = new QuizModel(gameState, levelSelected);
		return _quizModel.start();
	}

	/**
	 * to be invoked from start() method that starts the GUI
	 */
	public static void setWindow(Stage window) {
		_window = window;
	}

	/**
	 * called by all scene clases to set the current scene
	 */
	public static void setScene(Scene scene) {
		_window.setScene(scene);
		_window.show();
	}

	/**
	 * Resets all data for application
	 */
	public static void setToDefault() throws FileNotFoundException {
		_isFirstTime = true;
		_levelsUnlocked = 0;
		_voice = "default";
		updateTxtFile();
	}

	/**
	 * Sets background image for GUI to the field image
	 */
	public static void setBackground() {
		File file = new File(".media/field.jpg");
		Image image = new Image(file.toURI().toString());

		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, backgroundSize);
		_background = new Background(backgroundImg);
	}

	/**
	 * Sets number of levels
	 */
	public static void setNumLevels(int numLevels) {
		_numLevels = numLevels;
	}

	/**
	 * Overwrites .settings.txt file with updated field values
	 */
	public static void updateTxtFile() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(".app_files/.settings.txt");
		writer.println(_isFirstTime.toString());
		writer.println(_levelsUnlocked);
		writer.println(_voice);
		writer.close();
	}

	/**
	 * Sets up app model instance and launches gui
	 */
	public static void main(String[] args) {
		setup();
		launch(args);
	}

	/**
	 * Starts GUI and gives the primary stage window where all scenes will be
	 * displayed
	 */
	public void start(Stage primaryStage) throws Exception {

		_window = primaryStage;
		_window.setResizable(false);
		if (_isFirstTime) {
			WelcomeScene.setScene();
		} else {
			MainMenuScene.setScene();
		}
	}

	/**
	 * Is called when a new quiz is to be started, either standard quiz, review
	 * or one/three lives mode
	 */
	public static void startQuiz(GameState gameState, int level) {
		// Initialises new quiz app.model object with the selected level
		QuizState quizState = AppModel.setQuizModel(gameState, level);

		// Initialises new app.scene.EnterWordScene app.scene to be built next
		if (quizState.equals(QuizState.READY)) {
			EnterWordScene wordScene = new EnterWordScene();
			wordScene.setScene();
			// Else if no words display no words app.scene
		} else if (quizState.equals(QuizState.NO_WORDS)) {
			NoWordsScene.setScene();
		}
	}
}
