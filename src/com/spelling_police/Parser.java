package com.spelling_police;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/*
 * Parser splits Strings into individual words and inserts them in an Arraylist.
 * The method returns the ArrayList 'word'.
 */
public class Parser {

	private String language;
	private static Pattern pattern = Pattern.compile("[0-9.,\\[\\]\\(\\)\\/%«»]+");

	public Parser(String language) {
		this.language = language;
	}
/*A privat method that seperates a given text/string into individual periods and
* inserts them in an ArrayList.
*/
	private  ArrayList<String> splitIntoSentences(String text) {
			ArrayList<String> periods = new ArrayList<String>();
			String[] arrayPeriods = text.split("[\\.\\!\\?\\;]");

			for (int i=0; i<arrayPeriods.length; i++) {
				periods.add(arrayPeriods[i]);
		    }
			return periods;
	}
	

	/* isWord: A method to determine if the element in question is a word or not and returns
	 *those that are.
	 */
	private boolean isWord(String element) {

		if (element.length() == 0) {
			return false;
		}

		Matcher matcher = pattern.matcher(element);
		return !matcher.find();
	}
	
	// Splits text into individual sentences & words.
	public ArrayList<ArrayList<String>> separate(String text){
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> sentences = this.splitIntoSentences(text);
		for (String sentence : sentences) {
			
			String[] elements  = sentence.split("\\s+");
			ArrayList<String> words = new ArrayList<String>();
			
			for (int i=0; i < elements.length; i++){
				elements[i] = elements[i].replaceAll("[-.,!;?:\\(\\)\\[\\]]", "");

				if (isWord(elements[i])) {
					words.add(elements[i]);
				}
			}
			result.add(words);
		}
		
		return result;
	}

}
