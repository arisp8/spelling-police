package com.spelling_police;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Dictionary {
	
	private String language;
	private TreeSet<String> wordList;
	private TreeMap<Character, String> firstWords = new TreeMap<Character, String>();
	private TreeMap<Character, String> lastWords = new TreeMap<Character, String>();
	private Config config;

	private Thread loadThread;
	
	private static HashMap<String, Dictionary> activeDictionaries = new HashMap<String, Dictionary>();

	/** Default constructor for Dictionaries
	 * @param language The language of the dictionary to load
	 */
	public Dictionary(String language) {
		this.language = language;
		final String path = System.getProperty("user.dir") + "\\resources\\dictionaries\\" + language + ".txt";
		this.config = new Config(language);
		final String encoding = this.config.getEncoding();

		loadThread = new Thread() {
			public void run() {
				wordList = readDictionary(path, encoding);
			}
		};
		
		
		loadThread.start();
	}
	
	public String getLanguage() {
		return this.language;
	}
	
	public static Dictionary getDictionary(String language) {
		if (!activeDictionaries.containsKey(language)) {
			activeDictionaries.put(language, new Dictionary(language));
		}
		
		return activeDictionaries.get(language);
	}
	
	/**
	 * Reads a dictionary from a file and converts it to an ArrayList
	 * @param filePath The path where the dictionary file should be found
	 * @return Returns an array list containing all words from the dictionary
	 */
	private TreeSet<String> readDictionary(String filePath, String encoding) {
		TreeSet<String> words = new TreeSet<>();

		try (Scanner scan  = new Scanner(new File(filePath), encoding)) {
			char previousChar = ' ';
			String previousWord = "";
			String word;
			while (scan.hasNext()) {
				word = scan.next();
				if (word.charAt(0) != previousChar) {
					firstWords.put(word.charAt(0), word);
					lastWords.put(previousChar, previousWord);
					previousChar = word.charAt(0);
				}
				words.add(word);
				previousWord = word;
			}
			lastWords.put(firstWords.lastKey(), previousWord);
		} catch (Exception e) {
			System.out.println("An exception has happened while reading the dictionary: "
								+ e.getMessage());
		}

		return words;
	}

	/**
	 * Checks if a word exists in the dictionary
	 * @param word The word to check
	 * @return A boolean indicating whether or not the word exists
	 */
	public boolean wordExists(String word) {

		// Makes sure loading of dictionary has finished
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		
		if (word.length() == 0) {
			return false;
		} else if (!firstWords.containsKey(word.charAt(0))) {
			// If a word is not from the current character space don't treat it as a mistake.
			return true;
		} else if (this.wordList.contains(word)) {
			return true;
		} else if (this.wordList.contains(word.toLowerCase())) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * Calculates the distance between 2 Strings, showing how many differences they have
	 * @param src  The word which will be compared
	 * @param dest Each String of the dictionary
	 * @return     Returns an integer showing the distance between the 2 strings
	 */
	private static int levenshteinDistance(String src, String dest) {
		int[][] distance = new int[src.length() + 1][dest.length() + 1];

		for (int i = 0; i <= src.length(); i++) {
			distance[i][0] = i;
		}
		for (int j = 1; j <= dest.length(); j++) {
			distance[0][j] = j;
		}

		for (int i = 1; i <= src.length(); i++) {
			for (int j = 1; j <= dest.length(); j++) {
                  distance[i][j] = Math.min(distance[i - 1][j] + 1 , Math.min(distance[i][j - 1] + 1,
                                  distance[i - 1][j - 1] + ((src.charAt(i - 1) == dest.charAt(j - 1)) ? 0 : 1)));
			}
		}

		return(distance[src.length()][dest.length()]);
    }
	
	private String getFirstWord(char letter) {
		return this.firstWords.get(letter);
	}
	
	private String getLastWord(char letter) {
		return this.lastWords.get(letter);
	}
	
	/**
	 * Performs a search based on a string that will match similar words
	 * @param word The word to search for
	 * @param fuzzyness Indicates how much tolerance the method should have for matching words
	 * @return A HashMap of Strings and Integers with similar words and each levenshtein distance
	 */
	public HashMap<String,Double> fuzzySearch(String word, double fuzzyness, boolean strict) {
		
		HashMap<String,Double> foundWords = new HashMap<String,Double>();

		// Makes sure loading of dictionary has finished
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			return foundWords;
		}
		String firstWord = getFirstWord(Character.toLowerCase(word.charAt(0)));
		String lastWord = getLastWord(Character.toLowerCase(word.charAt(0)));
		
		if (!firstWords.containsKey(word.charAt(0)) || !lastWords.containsKey(word.charAt(0))) {
			return null;
		}
		
		TreeSet<String> eligibleWords;
		
		if (strict) {
			eligibleWords = (TreeSet<String>) wordList.subSet(firstWord, lastWord);
		} else {
			eligibleWords = wordList;
		}
		
	    for (String s : eligibleWords) {
	    	
	        // Calculate the Levenshtein distance:
	        int levenshteinDistance = levenshteinDistance(word, s);

	        // Length of the longer string:
	        int length = Math.max(word.length(), s.length());

	        // Calculate the score:
	        double score = 1.0 - (double)levenshteinDistance / length;

	        // Match?
	        if (score > fuzzyness) {
	            foundWords.put(s,score);
	        }
	    }

	    return foundWords;
	}

}