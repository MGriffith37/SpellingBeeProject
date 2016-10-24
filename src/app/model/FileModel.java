package app.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import app.AppModel;

/**
 * Created by Fraser McIntosh on 16/09/2016. Responsible for controlling file
 * management; reading and writing to files
 */
public class FileModel {

	// Stores Words
	static HashMap<WordFile, HashMap<Integer, ArrayList<String>>> _fileMap = new HashMap<>();
	// List of custom spelling lists
	static ArrayList<String> _customLists = new ArrayList<String>();

	/**
	 * Gets the an arraylist of words for the designated level
	 */
	static private ArrayList<String> getLevelWords(WordFile file, int level) {
		HashMap<Integer, ArrayList<String>> fileWords = _fileMap.get(file);
		if (fileWords.containsKey(level)) {
			return fileWords.get(level);
		} else {
			ArrayList<String> emptyList = new ArrayList<>();
			fileWords.put(level, emptyList);
			return getLevelWords(file, level);
		}
	}

	/**
	 * Creates files that don't already exist and also parses files into
	 * _fileMap for easy access during application
	 */
	public static void initialise() {
		createFiles();
		parseFiles();
	}

	/**
	 * Helper method to create files that don't already exist
	 */
	private static void createFiles() {
		for (WordFile filename : WordFile.values()) {
			File f = new File(filename + "");
			if (!f.isFile()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Helper method to quickly calculate the number of levels within the
	 * spelling list
	 */
	public static int calcNumLevels() {
		File file = new File(WordFile.SPELLING_LIST + "");
		BufferedReader in;
		int level = 0;
		try {
			in = new BufferedReader(new FileReader(file + ""));

			String currentLine = in.readLine();

			// loop through till end of file

			// to keep track of the right level

			while (currentLine != null) {
				if (currentLine.contains("%")) {
					level++;
				}
				currentLine = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return level;

	}

	/**
	 * Helper method that parses the files and converts them into a more easily
	 * read format. Need to parse every time application is started Coupled to
	 * format of text file
	 */
	private static void parseFiles() {
		// Loop through every file
		for (WordFile filename : WordFile.values()) {
			File file = new File(filename + "");
			BufferedReader in;
			// file de-constructed into lists of levels
			HashMap<Integer, ArrayList<String>> fileWords = new HashMap<>();
			try {
				in = new BufferedReader(new FileReader(file + ""));

				String currentLine = in.readLine();

				// loop through till end of file
				// to keep track of the right level
				int level = 1;
				while (currentLine != null) {
					currentLine = in.readLine();

					// construct levels between each $Level, relying on indexing
					// to store lists in right location
					ArrayList<String> levelWords = new ArrayList<>();
					while (currentLine != null && currentLine.charAt(0) != '%') {
						levelWords.add(currentLine);
						currentLine = in.readLine();
					}

					// add levels to construct file
					fileWords.put(level, levelWords);
					// next level's words
					level++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			// put all files into a map
			_fileMap.put(filename, fileWords);
		}
	}

	/**
	 * Sync files with file map in case words have been added that are not on
	 * file
	 */
	public static void SyncFile(WordFile filename) {
		// clear every file
		clearFile(filename);
		// Loop through every file
		File file = new File(filename + "");
		PrintWriter output;

		try {
			// make writer
			output = new PrintWriter(new FileWriter(file, true));
			HashMap<Integer, ArrayList<String>> fileWords = _fileMap.get(filename);

			// loop through every file
			for (int level = 1; level <= AppModel.getNumLevels(); level++) {
				// write out level header
				output.println("%Level " + (level));

				// if level exists
				if (fileWords.containsKey(level)) {
					ArrayList<String> levelWords = fileWords.get(level);

					// write each word
					for (String word : levelWords) {
						output.println(word);
					}
				}

			}
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Clears selected type of list in hashmap
	 */
	public static void clearList(WordFile filename) {
		HashMap<Integer, ArrayList<String>> fileWords = _fileMap.get(filename);
		for (int level = 1; level <= AppModel.getNumLevels(); level++) {
			ArrayList<String> levelWords = new ArrayList<>();
			fileWords.put(level, levelWords);
			_fileMap.put(filename, fileWords);
		}
	}

	/**
	 * Clears selected file by emptying it
	 */
	public static void clearFile(WordFile filename) {
		File f = new File(filename + "");
		if (f.isFile()) {
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
		}

	}

	/**
	 * Clear all data from files
	 */
	public static void clearFiles() {
		// Loop through each file and if it exists clear it
		for (WordFile filename : WordFile.values()) {
			// Don't want to clear the spelling list
			if (filename.equals(WordFile.SPELLING_LIST)) {
				continue;
			}
			// Clear the file then sync it
			clearList(filename);
			clearFile(filename);
			SyncFile(filename);
		}
	}

	/**
	 * Helper method that returns all words from a level in a file selected
	 */
	public static ArrayList<String> getWordsFromLevel(WordFile file, int level) {
		return getLevelWords(file, level);
	}

	/**
	 * Helper method that adds a unique word to the file selected, if word
	 * already exists in list nothing happens
	 */
	public static void addUniqueWordToLevel(WordFile file, String word, int level) {
		if (!containsWordInLevel(file, word, level)) {
			addWordToLevel(file, word, level);
		}
		SyncFile(file);
	}

	/**
	 * Helper method that adds a word to the selected file in the designated
	 * level
	 */
	public static void addWordToLevel(WordFile file, String word, int level) {
		getLevelWords(file, level).add(word);
		SyncFile(file);
	}

	/**
	 * returns whether a word is in a level
	 */
	public static boolean containsWordInLevel(WordFile file, String word, int level) {
		return getLevelWords(file, level).contains(word);
	}

	/**
	 * Removes word from list
	 */
	public static void removeWordFromLevel(WordFile file, String word, int level) {
		if (containsWordInLevel(file, word, level)) {
			getLevelWords(file, level).remove(word);
		}
		// Sync file with array
		SyncFile(file);
	}

	/**
	 * Returns how many matches for a word there are in a level of a file
	 */
	public static int countOccurencesInLevel(WordFile file, String word, int level) {
		ArrayList<String> levelWords = getLevelWords(file, level);
		if (levelWords == null) {
			return 0;
		} else {
			int count = 0;
			for (String s : levelWords) {
				if (s.equals(word)) {
					count++;
				}
			}
			return count;
		}
	}

	/**
	 * Returns custom list arraylist
	 */
	public static ArrayList<String> getCustomList() {
		return _customLists;
	}

	/**
	 * Adds file address to custom spelling list arraylist
	 */
	public static void addToCustomList(String address) {
		_customLists.add(address);
	}
}