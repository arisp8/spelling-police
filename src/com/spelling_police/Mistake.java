
package com.spelling_police;

import java.util.ArrayList;
import java.util.List;

public class Mistake {
		
	private String word;
	private String language;
	private List<String> suggestions;
	
	private Thread suggestionsThread;
	
	private int position;
	private int sentence;
	
	/**
	 * Default Constructor of Mistake 
	 * @param word : The wrong word given by the user
	 * @param language : The selected language 
	 * @param sentence : ???
	 * @param position : ???
	 */
	public Mistake(String word, String language, int sentence, int position){
		this.word = word;
		this.sentence = sentence;
		this.position = position;
		this.language = language;
	}
	/**
	 * Finds the suggestions from class solver
	 * @return Returns the best suggestions
	 */
	public List<String> getSuggestions() {
		
		if (suggestions == null) {
			suggestions = Solver.findBestSuggestions(word, 3, language);
		}
		return suggestions;
	}
	
	public List<Integer> findWrongPosition(String compareTo){
		return indexOfDifference(this.word, compareTo);
	}
	
	/**
	 * word : The word the user inputs OR one of the words of a String the user inputs
	 * getSuggestions().get(0) : The first word of the list of the classified suggestions taken by Solver class.
	 */
	public List<Integer> findWrongPosition() {
		return indexOfDifference(this.word, this.getSuggestions().get(0));
	}
	/**
	 * Finds the differences between the two words.
	 * @param word1 : The wrong word
	 * @param word2 : The right word chosen by the suggestions
	 * @return Returns a List with the exact positions of the mistakes in the wrong word.
	 */
	public static List<Integer> indexOfDifference(String word1, String word2) {
		List<Integer> positions = new ArrayList<Integer>();
		// Checks if neither of the words are null OR the two words are the same and prints a message of error. 
	    if (word1 == null || word2 == null || word2 == word1) {
	        System.out.println("We have a problem");
	    }
	    int i;
	    
	    if (word1.length() != word2.length()){
	    	String[] equalLength = makeWordsEqual(word1, word2);
		    word1 = equalLength[0];
		    word2 = equalLength[1];   
	    }
	    
	    for (i = 0; i < word1.length(); ++i) {
	    	try {
	    		if (word1.charAt(i) != word2.charAt(i)) {
		       		positions.add(i);
			    }
	    	} catch (StringIndexOutOfBoundsException e) {
	    		// Adds as many zeros as needed in the wrong word 
	    		if (word1.length() < word2.length()) {
	    			while(word1.length() < word2.length()) {
	    				word1 += "0";
	    			}
	    		} else {
	    			// Adds as many zeros as needed in the correct word 
	    			while(word2.length() < word1.length()) {
	    				word2 += "0";
	    			}
	    		}
	    		return indexOfDifference(word1, word2);
	    	}
	    	
	    }
	    
	    return positions;
	}
	/**
	 * Make the words equal by adding zeros("0") where necessary.
	 * @param wrong_word : The wrong word
	 * @param best_suggestion: The best suggestion
	 * @return Returns a table with the two words that are now equal.
	 */
	public static String[] makeWordsEqual(String wrong_word, String best_suggestion){
		
		String bigger;
		String smaller;
		
		if (wrong_word.length() > best_suggestion.length()) {
			bigger = wrong_word;
			smaller = best_suggestion;
		} else {
			bigger = best_suggestion;
			smaller = wrong_word;
		}
		for (int i = 0; i < bigger.length(); i++) {
			// Add zero at the end of the smallest word
			if (i >= smaller.length()) {
				smaller += "0";
				continue;
			}
			// Add zero if a difference is found between the two words.
			if (smaller.charAt(i) != bigger.charAt(i)) {
				smaller = smaller.substring(0, i) + "0" + smaller.substring(i, smaller.length());
			}
		}
		
		String[] words = {smaller, bigger};
		
		return words;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public String toString() {
		return this.word + "(" + this.sentence + ", " + this.position + ")";
	}
	
}
