package com.spelling_police;

import java.util.*;
import java.util.ArrayList;
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
	private  ArrayList<String> parseText(String text) {
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
	// Separate : the method which splits the Strings into individual words.
	public ArrayList<String> seperate(String period){
		//Creation of an Array the same size as the number of the words in the period.
		String elements[] = period.split("\\s+");

		ArrayList<String> words = new ArrayList<String>();

		//Inserts only the word elements into the list.
		for (int i=0; i < elements.length; i++){
			elements[i] = elements[i].replaceAll("[-.,!;?:\\(\\)\\[\\]]", "");

			if (isWord(elements[i])) {
				words.add(elements[i]);
			}
		}
		//Returns the list.
		return words;
	}

	public static void main (String args[] ){

		Scanner input = new Scanner(System.in);

		Parser textParser = new Parser("el");

		System.out.println("Input text please");
		String testText = input.nextLine();
		ArrayList<String> splittedText = textParser.parseText(testText);

		Parser periodParser = new Parser("el");
		
		ArrayList<String> splittedPeriods = periodParser.seperate(testText);
	}
}
