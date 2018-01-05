package com.spelling_police;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

public class Dictionary {

	private String language;
	private TreeSet<String> wordList;
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
	
	public boolean findOneWord(String word) {
			
		boolean matchedWord = false;

		// Makes sure loading of dictionary has finished
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			return matchedWord;
		}
		
		double fuzzyness = 0.6;
		
	    for (String s : wordList) {
    		
	    	if (s.charAt(0) != word.charAt(0)) {
	    		continue;
	    	}
	    	
	        // Calculate the Levenshtein distance:
	        int levenshteinDistance = levenshteinDistance(word, s);

	        // Length of the longer string:
	        int length = Math.max(word.length(), s.length());

	        // Calculate the score:
	        double score = 1.0 - (double)levenshteinDistance / length;
	        
	        // Match?
	        if (score > fuzzyness) {
	        	return true;
	        }
	    }

	    return matchedWord;
	}
	
	public static Dictionary getDictionary(String language) {
		if (!activeDictionaries.containsKey(language)) {
			System.out.println("Loading dictionary anew");
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

	/**
	 * Performs a search based on a string that will match similar words
	 * @param word The word to search for
	 * @param fuzzyness Indicates how much tolerance the method should have for matching words
	 * @return A HashMap of Strings and Integers with similar words and each levenshtein distance
	 */
	private HashMap<String,Double> fuzzySearch(String word, double fuzzyness, boolean strict) {
		
		HashMap<String,Double> foundWords = new HashMap<String,Double>();

		// Makes sure loading of dictionary has finished
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			return foundWords;
		}
		
		System.out.println(wordList);
		
	    for (String s : wordList) {
	    	
	    	if (strict && s.charAt(0) != word.charAt(0)) {
	    		continue;
	    	}
	    	
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

  public List<String> similarList (String word , int limit){

        HashMap<String,Double> foundWords = new HashMap<String,Double>();
        
       double fuzzyness = 0.7;
       boolean strict = true;

       do {
           foundWords = this.fuzzySearch(word, fuzzyness, strict);
		   fuzzyness -= 0.05;
		} while(foundWords.size() < limit);
       
		 Object[] a = foundWords.entrySet().toArray(); 
		 
		 Arrays.sort(a, new Comparator() {
		     public int compare(Object o1, Object o2) {
		         return ((Map.Entry<String, Double>) o2).getValue()
		                    .compareTo(((Map.Entry<String, Double>) o1).getValue());
		     }
		 });
		 
		 List<String> similarList = new ArrayList<String>();
		 
		 for (int i = 0; i < limit; i++) {
			similarList.add(((Map.Entry<String, Double>) a[i]).getKey());
		 }

        return similarList;
	}

}