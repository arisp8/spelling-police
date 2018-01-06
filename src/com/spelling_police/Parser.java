package com.spelling_police;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/*
 * Parser splits Strings into periods and then the periods into individual words
 * and inserts them in an Arraylist of ArrayLists.That means that every ArrayList represents
 * a period of the original text and every element in it represents a words in that period.
 */

public class Parser {

	
	private static Pattern pattern = Pattern.compile("^[^A-Za-zΑ-Ωα-ωά-ώΐϊϋ]{1,}$");
	private static Pattern endOfSentence = Pattern.compile("([\\.!\\?;]){1,}(\\s+[Α-ΖΑ-Ω]|$)");


/*
 *splitIntoSentences: A private method that seperates a given text/string into individual periods and
 * inserts them in an ArrayList.
 */
	private  ArrayList<String> splitIntoSentences(String text) {
		ArrayList<String> sentences = new ArrayList<String>();
		Matcher match = endOfSentence.matcher(text);

		int last_index = 0;
		
		while (match.find()) {
			sentences.add(text.substring(last_index, match.start()));
			last_index = match.start() + 1;
		}

		if (last_index < text.length()) {
			sentences.add(text.substring(last_index, text.length()));
		}

		return sentences;
	}
	
/* 
 * isWord: A private method to determine if the element in question is a word or not and returns
 *true or false depending on the outcome.
 */
	private boolean isWord(String element) {
		if (element.length() == 0) {
			return false;
		}

		Matcher matcher = pattern.matcher(element);
		return !matcher.find();
	}
/*
 * separate: A public method that splits every period of the given text into individual words and
 * inserts them,after using the 'isWord' method in the ArrayList of ArrayLists 'result'.
 */
	public ArrayList<ArrayList<String>> separate(String text){
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> sentences = this.splitIntoSentences(text);
		for (String sentence : sentences) {
			
			String[] elements  = sentence.split("\\s+");
			ArrayList<String> words = new ArrayList<String>();
			
			for (int i=0; i < elements.length; i++){
				elements[i] = elements[i].replaceAll("(?:[a-zA-Zα-ωΑ-Ω]\\.){2,}+","");
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
