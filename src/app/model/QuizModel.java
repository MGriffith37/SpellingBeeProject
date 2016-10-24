package app.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import app.AppModel;

/**
 * Created by Fraser McIntosh on 13/09/2016. Responsible for all information
 * pertaining to a quiz instance - the gamemode, quiz words, what level is
 * selected, etc
 */
public class QuizModel {

	private int _numWordsInQuiz;
	private int _numCorrectWords;
	private int _levelSelected;
	private ArrayList<String> _quizWords;
	private int _curruntWordIndex;
	private QuizState _quizState;
	private WordModel _wordModel;

	private static int MAX_QUIZ_WORDS = 10;
	private static final int PASS_LEVEL_SCORE = 9;

	private boolean _successfulQuiz = false;
	private boolean _isHardestLevel;

	private GameState _gameState;
	private int _numberOfLives;

	/**
	 * Initialises new quiz model, with selected game mode and level. Checks if
	 * the level is the highest unlocked level Allocates number of lives if the
	 * game mode is one life/three lives
	 */
	public QuizModel(GameState gameState, int levelSelected) {
		_gameState = gameState;
		_levelSelected = levelSelected;

		if (_gameState == GameState.ONELIFE) {
			_numberOfLives = 1;
			MAX_QUIZ_WORDS = 100;
		} else if (_gameState == GameState.THREELIVES) {
			_numberOfLives = 3;
			MAX_QUIZ_WORDS = 100;
		} else {
			_numberOfLives = -1;
		}

		if (getLevelSelected() == AppModel.getLevelsUnlocked()
				&& AppModel.getLevelsUnlocked() < AppModel.getNumLevels()) {
			_isHardestLevel = true;
		} else {
			_isHardestLevel = false;
		}
	}

	/**
	 * Starts the quiz by generating the list of quiz words to be tested and
	 * info surrounding that Returns the quiz state on whether it is ready to
	 * start or if there are any problems existing
	 */
	public QuizState start() {
		_curruntWordIndex = 0;
		_quizWords = generateQuizWords();
		_numWordsInQuiz = _quizWords.size();
		_numCorrectWords = 0;

		if (_numWordsInQuiz > 0) {
			_quizState = QuizState.READY;
			_wordModel = new WordModel(getCurrentWord());
		} else
			_quizState = QuizState.NO_WORDS;
		return _quizState;
	}

	/**
	 * Helper method for constructor that generates the words for a quiz,
	 * utilisting app.model.FileModel's methods for getting words.
	 */
	private ArrayList<String> generateQuizWords() {
		// List of quiz words
		ArrayList<String> quizWords = new ArrayList<>();

		// Designates file to be used to gather quiz words
		WordFile file = WordFile.SPELLING_LIST;
		if (_gameState.equals(GameState.REVIEW)) {
			file = WordFile.REVIEW;
		}
		// Gets words from the selected file and level
		ArrayList<String> wordsFromList = FileModel.getWordsFromLevel(file, getLevelSelected());

		// Calculates number of quiz words to get
		int numWordsInQuiz = MAX_QUIZ_WORDS;
		if (wordsFromList.size() < MAX_QUIZ_WORDS) {
			numWordsInQuiz = wordsFromList.size();
		}

		for (int i = 0; i < numWordsInQuiz; i++) {
			// Take a random word
			int index = new Random().nextInt((wordsFromList.size()));
			String word = wordsFromList.get(index);

			// If word is already in quiz word list, take another random word
			// until a unique word is found
			while (quizWords.contains(word)) {
				index = new Random().nextInt((wordsFromList.size()));
				word = wordsFromList.get(index);

			}
			quizWords.add(word);
		}
		return quizWords;
	}

	// Getters
	// -----------------------------------------------------------------------------------------

	/**
	 * Get number of words in quiz
	 */
	public int getNumWordsInQuiz() {
		return _numWordsInQuiz;
	}

	/**
	 * Get the selected level
	 */
	public int getLevelSelected() {
		return _levelSelected;
	}

	/**
	 * get the state of the word
	 */
	public WordState getWordState() {
		return _wordModel.getWordState();
	}

	/**
	 * Get the current word index
	 */
	public int getCurruntWordIndex() {
		return _curruntWordIndex;
	}

	/**
	 * Get the quiz state
	 */
	public QuizState getQuizState() {
		return _quizState;
	}

	/**
	 * Get number of correct words
	 */
	public int getNumCorrectWords() {
		return _numCorrectWords;
	}

	/**
	 * Get whether quiz was successful or not
	 */
	public boolean getSuccessfulQuiz() {
		return _successfulQuiz;
	}

	/**
	 * Get current word
	 */
	public String getCurrentWord() {
		return _quizWords.get(_curruntWordIndex);
	}

	/**
	 * Get whether level is hardest level
	 */
	public boolean getIsHardestLevel() {
		return _isHardestLevel;
	}

	/**
	 * Get game state
	 */
	public GameState getGameState() {
		return _gameState;
	}

	/**
	 * Get number of lives
	 */
	public int getNumberOfLives() {
		return _numberOfLives;
	}

	/**
	 * Get whether user is out of lives
	 */
	public boolean outOfLives() {
		return (_numberOfLives == 0);
	}
	// End of getters
	// ------------------------------------------------------------------------------------------

	/**
	 * Set number of lives
	 */
	public void setNumberOfLives(int number) {
		_numberOfLives = number;
	}

	/**
	 * Update the current state of the quiz, including the state of the word
	 */
	public void updateQuizState() {
		// If the word is failed or mastered, it is finished so need to go to
		// the next word

		addWordToFiles();
		_curruntWordIndex++;
		if (!_wordModel.getWordState().equals(WordState.FAILED)) {
			_numCorrectWords++;
		} else {
			_numberOfLives--;
		}

		// If we have gone through all words in the quiz, the quiz is finished

		if (_numWordsInQuiz == _curruntWordIndex) {
			_quizState = QuizState.FINISHED;
			if (getNumCorrectWords() >= PASS_LEVEL_SCORE) {
				_successfulQuiz = true;
				try {
					// If current level is highest unlocked level
					// And not the highest level possible
					if (_isHardestLevel) {
						// unlock the next level
						AppModel.setLevelsUnlocked(AppModel.getLevelsUnlocked() + 1);
					}
				} catch (FileNotFoundException e) {
				}
			}
		}
	}

	/*
	 * Called when moving to next word.
	 */
	private void addWordToFiles() {
		switch (_wordModel.getWordState()) {
		// Word is inccorect so add to failed word list and review list for
		// score and review quiz
		case FAILED:
			FileModel.addWordToLevel(WordFile.FAILED, _wordModel.getWord(), getLevelSelected());
			FileModel.addUniqueWordToLevel(WordFile.REVIEW, _wordModel.getWord(), getLevelSelected());
			break;
		// Word is correct so add to mastered list and remove from review list
		case MASTERED:
			FileModel.addWordToLevel(WordFile.MASTERED, _wordModel.getWord(), getLevelSelected());
			FileModel.removeWordFromLevel(WordFile.REVIEW, _wordModel.getWord(), getLevelSelected());
			break;
		default:
		}
		// add word to attempted for statistics implementation
		FileModel.addUniqueWordToLevel(WordFile.ATTEMPTED, getCurrentWord(), getLevelSelected());
	}

	/**
	 * Goes to next word
	 */
	public void nextWord() {
		_wordModel = new WordModel(getCurrentWord());
	}
	// Answer submission logic
	// ---------------------------------------------------------------------------------

	/**
	 * Checks whether the answer is valid and updates the word state within the
	 * word model class as well
	 */
	public boolean submitAnswer(String answer) {
		// Verify valid
		if (!answer.matches("[a-zA-Z]+")) {
			return false;
		} else {
			// update app.model state by passing through the answer result
			// (true/false)
			_wordModel.updateWordState(answer.toLowerCase().equals(getCurrentWord().toLowerCase()));
			updateQuizState();
		}
		// Return a true response to the view if successful submission
		return true;
	}

}
