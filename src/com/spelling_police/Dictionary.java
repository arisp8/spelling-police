package com.spelling_police;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.HashMap;

public class Dictionary {

	private String language;
	private TreeSet<String> wordList;
	private HashMap<String, String> config;
	
	private Thread loadThread;
	
	/** Default constructor for Dictionaries
	 * @param language The language of the dictionary to load
	 */
	public Dictionary(String language) {
		this.language = language;
		String path = System.getProperty("user.dir") + "\\resources\\dictionaries\\" + language + ".txt";
		String configPath = System.getProperty("user.dir") + "\\resources\\config\\" + language + ".info";

		this.config = getConfig(configPath);
		String encoding = this.config.get("encoding");
		
		loadThread = new Thread() {
			public void run() {
				wordList= readDictionary(path, encoding);
				
			}
		};
		loadThread.start();
		// this.wordList = readDictionary(path, encoding);
	}

	private HashMap<String, String> getConfig(String filePath) {
		HashMap<String, String> config = new HashMap<String, String>();

		try (Scanner scan  = new Scanner(new File(filePath), "utf-8")) {
			while (scan.hasNext()) {
				String line = scan.next();
				String[] components = line.split(":");
				config.put(components[0], components[1]);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong when opening config file: " + e.getMessage());
		}


		return config;
	}

	/**
	 * Reads a dictionary from a file and converts it to an ArrayList
	 * @param filePath The path where the dictionary file should be found
	 * @return Returns an array list containing all words from the dictionary
	 */
	private TreeSet<String> readDictionary(String filePath, String encoding) {
		TreeSet<String> words = new TreeSet<>();

		try (Scanner scan  = new Scanner(new File(filePath), encoding)) {
			while (scan.hasNext()) {
				words.add(scan.next());
			}
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
		
		if (this.wordList.contains(word)) {
			return true;
		} else if(this.wordList.contains(word.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculates the distance between 2 Strings, showing how many differences they have
	 * @param src  The word which will be compered
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

	/**
	 * Performs a search based on a string that will match similar words
	 * @param word The word to search for
	 * @param fuzzyness Indicates how much tolerance the method should have for matching words
	 * @return A List of Strings that are similar to the word given
	 */
	private List<String> fuzzySearch(String word, double fuzzyness) {
		
		List<String> foundWords = new ArrayList<String>();
		
		// Makes sure loading of dictionary has finished
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			return foundWords;
		}

	    for (String s : wordList) {
	        // Calculate the Levenshtein distance:
	        int levenshteinDistance = levenshteinDistance(word, s);

	        // Length of the longer string:
	        int length = Math.max(word.length(), s.length());

	        // Calculate the score:
	        double score = 1.0 - (double)levenshteinDistance / length;

	        // Match?
	        if (score > fuzzyness) {
	            foundWords.add(s);
	        }
	    }

	    return foundWords;
	}
  private List<String> similarList (String word , int limit){

		List<String> similarList = new ArrayList<String>();

        Dictionary dict = new Dictionary ("el");

        //defines fuzzyness
        double fuzzyness = 0.80;

       do {
           similarList = dict.fuzzySearch(word,fuzzyness);

		   fuzzyness -= 0.05;
		} while(similarList.size() < limit);

        similarList = similarList.subList(0,limit);

        return similarList;
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
        Dictionary dict = new Dictionary("el");

        List<String> option= dict.similarList("προειδοπίηση",5);

		System.out.println(option);
	}

}