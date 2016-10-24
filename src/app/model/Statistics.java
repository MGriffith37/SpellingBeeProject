package app.model;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * Created by Fraser McIntosh on 20/08/2016. Responsible for the logic in
 * building the statistics table for score scene Builds the table view part of
 * the score GUI
 */
public class Statistics {

	// Values for currently selected level and gamestate
	private int _level;
	private static GameState _gameState;

	/**
	 * Constructor to set level and gamestate
	 */
	public Statistics(int level, GameState gameState) {
		_level = level;
		_gameState = gameState;
	}

	/**
	 * Creates a list of word statistics using all the words in the attempted
	 * list So doesn't create a statistic for a word that hasn't been attempted
	 */
	public ObservableList<WordStatistic> getWordStatistics() {
		ObservableList<WordStatistic> statistics = FXCollections.observableArrayList();

		// If the stats are for standard and review quiz, gets stats from
		// mastered and failed txt files using filemodel
		if (_gameState.equals(GameState.QUIZ) || _gameState.equals(GameState.REVIEW)) {
			ArrayList<String> levelWords = FileModel.getWordsFromLevel(WordFile.ATTEMPTED, _level);

			// should read from file only the words between specified level and
			// the next level
			for (String word : levelWords) {
				// trim newline when comparing with lineToRemove
				String trimmedLine = word.trim();
				statistics.add(new WordStatistic(trimmedLine, _level));
			}
		} else {
			// If the stats are for the one life/three lives modes, read in from
			// the corresponding txt file
			ArrayList<String> levelWords;
			int lives;
			if (_gameState.equals(GameState.ONELIFE)) {
				levelWords = FileModel.getWordsFromLevel(WordFile.ONELIFESCORE, _level);
				lives = 1;
			} else {
				levelWords = FileModel.getWordsFromLevel(WordFile.THREELIVESSCORE, _level);
				lives = 3;
			}

			// Goes through all available user scores
			for (int i = 0; i < levelWords.size(); i++) {

				// splits the parsed string from file into the user name and
				// their score
				String[] parts = levelWords.get(i).split(":");

				// Creates a mock word statistic object around the high scores
				// for users
				WordStatistic word = new WordStatistic();

				// Get user's name for the score
				word.setWord(parts[0]);
				// Get user's score
				word.setMastered(Integer.parseInt(parts[1]));
				// Get the lives used in their corresponding attempt
				word.setFailed(lives);
				statistics.add(word);
			}
			/*
			 * Sort the statistics by most times mastered
			 */
			FXCollections.sort(statistics, new Comparator<WordStatistic>() {
				@Override
				public int compare(WordStatistic s1, WordStatistic s2) {
					if (s1.getMastered() < s2.getMastered()) {
						return 1;
					} else if (s1.getMastered() == s2.getMastered()) {
						return 0;
					} else {
						return -1;
					}
				}
			});
		}
		return statistics;
	}

	@SuppressWarnings("unchecked")
	public ScrollPane constructTableLayout() {
		// Creates word column
		TableColumn<WordStatistic, String> wordColumn = new TableColumn<>("Word");
		wordColumn.setPrefWidth(330);
		wordColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, String>("Word"));

		// Creates mastered column
		TableColumn<WordStatistic, Integer> masteredColumn = new TableColumn<>("Correct");
		masteredColumn.setPrefWidth(250);
		masteredColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("mastered"));

		// Creates failed column
		TableColumn<WordStatistic, Integer> failedColumn = new TableColumn<>("Incorrect");
		failedColumn.setPrefWidth(250);
		failedColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("failed"));

		// If statistics are for one life/three lives change column headers
		if (_gameState.equals(GameState.ONELIFE) || _gameState.equals(GameState.THREELIVES)) {
			wordColumn.setText("Name");
			masteredColumn.setText("Score");
			failedColumn.setText("Lives Used");
		}

		// Create table out of the three columns
		TableView<WordStatistic> table = new TableView<>();

		table.setItems(getWordStatistics());
		table.getColumns().addAll(wordColumn, masteredColumn, failedColumn);

		table.setPrefHeight(400);
		// Create layout and add scroll pane and table as contents
		VBox root = new VBox();

		// Style the layout to match GUI style
		root.getStylesheets().add("app/scene/myStyle.css");
		root.getChildren().addAll(table);
		root.setAlignment(Pos.CENTER);

		// Create scroll pane parent container object to house table view
		ScrollPane scrollpane = new ScrollPane();
		scrollpane.setFitToWidth(true);
		scrollpane.setFitToHeight(true);
		scrollpane.setContent(root);
		return scrollpane;
	}

}