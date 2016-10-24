package app.model;

/**
 * Created by Fraser McIntosh on 16/09/2016. Helps to map a quiz word so that
 * all corresponding information is easy to obtain
 */
public class WordModel {

	// Word state variables
	private WordState _wordState;
	String _word;

	/**
	 * Constructor to initialise word state information
	 */
	WordModel(String word) {
		_wordState = WordState.STARTED;
		_word = word;
	}

	/**
	 * Return word state
	 */
	public WordState getWordState() {
		return _wordState;
	}

	/**
	 * Return word
	 */
	public String getWord() {
		return _word;
	}

	/**
	 * Updates word state to whether it is correct or not
	 */
	public void updateWordState(boolean isCorrectAnswer) {

		if (isCorrectAnswer) {
			_wordState = WordState.MASTERED;
		} else {
			_wordState = WordState.FAILED;
		}

	}
}
