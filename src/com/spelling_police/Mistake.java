
package com.spelling_police;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Mistake {
		
	private String word;
	private String language;
	private List<String> suggestions;
	private List<Integer> wrongPositions;
	
	private Thread suggestionsThread;
	
	private int position;
	private int sentence;
	
	public Mistake(String word, String language, int sentence, int position){
		this.word = word;
		this.sentence = sentence;
		this.position = position;
		this.language = language;
	}
	
	public List<String> getSuggestions() {
		
		if (suggestions == null) {
			suggestions = Solver.findBestSuggestions(word, 3, language);
		}
		return suggestions;
	}
	
	public List<Integer> getWrongPosition(){
		if (this.wrongPositions == null) {
			wrongPositions = findWrongPosition(word);
		}
		return wrongPositions;
	}
	
	/**
	 * word : The word the user inputs OR one of the words of a String the user inputs
	 * suggestions : The list of the classified suggestions taken by Solver class.
	 * suggestions.get(1) : The best result. 
	 */
	public List<Integer> findWrongPosition(String word){
		return wrongPositions = indexOfDifference(word, this.getSuggestions().get(0));
	}
	public static List<Integer> indexOfDifference(String word1, String word2) {
		List<Integer> positions = new ArrayList<Integer>();
	    /*if (str1 == str2) {
	        return -1;
	    }*/
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
	    	if (word1.charAt(i) != word2.charAt(i)) {
	       		positions.add(i);
		    }
	    }
	    
	    return positions;
	}
	
	public static String[] makeWordsEqual(String wrong_word , String best_suggestion){
		
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
			
			if (i >= smaller.length()) {
				smaller += "0";
				continue;
			}
			
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
	
	public static void main(String[]args){
		
		Mistake test = new Mistake("����������", "el", 1, 1);
		System.out.println(test.getSuggestions());
		System.out.println(test.getWrongPosition());
		
		Mistake test2 = new Mistake("��������", "el", 1, 1);
		System.out.println(test2.getSuggestions());
		System.out.println(test2.getWrongPosition());
		
	}
}
