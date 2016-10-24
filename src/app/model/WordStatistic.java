package app.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Fraser McIntosh on 20/08/2016. Responsible for the statistics
 * information for a corresponding word, in terms of passed/failed
 */
public class WordStatistic {
	private SimpleStringProperty word;
	private SimpleIntegerProperty failed;
	private SimpleIntegerProperty mastered;

	/**
	 * Pass in the word to the constructor and sets the fields
	 */
	WordStatistic(String word, int level) {
		this.word = new SimpleStringProperty(word);
		failed = new SimpleIntegerProperty(FileModel.countOccurencesInLevel(WordFile.FAILED, word, level));
		mastered = new SimpleIntegerProperty(FileModel.countOccurencesInLevel(WordFile.MASTERED, word, level));
	}

	/**
	 * Empty constructor to help with one life/three lives statistics
	 */
	WordStatistic() {
	}

	/**
	 * Returns word
	 */
	public String getWord() {
		return word.get();
	}

	/**
	 * Sets word
	 */
	public void setWord(String word) {
		this.word = new SimpleStringProperty(word);
	}

	/**
	 * Returns failed statistic
	 */
	public int getFailed() {
		return failed.get();
	}

	/**
	 * Sets failed
	 */
	public void setFailed(int failed) {
		this.failed = new SimpleIntegerProperty(failed);
	}

	/**
	 * Returns mastered statistic
	 */
	public int getMastered() {
		return mastered.get();
	}

	/**
	 * Sets masteredc
	 */
	public void setMastered(int mastered) {
		this.mastered = new SimpleIntegerProperty(mastered);
	}
}
