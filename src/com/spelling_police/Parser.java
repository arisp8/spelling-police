package com.spelling_police;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Parser splits Strings into individual words and inserts them in an Arraylist.
 * The method returns the ArrayList 'words'.
 */
public class Parser {

	private String language;
	private static Pattern pattern = Pattern.compile("[0-9.,\\[\\]\\(\\)\\/%«»]+");

	public Parser(String language) {
		this.language = language;
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
	// Separate : the method which splits the Strings
	public ArrayList<String> seperate(String period){
		//Creation of an ArrayList the same size as the number of the words in the period.
		String elements[] = period.split("\\s+");

		ArrayList<String> words = new ArrayList<String>();

		//Inserts only the word elements in order into the list.
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
		Parser obj = new Parser("el");
		String period = "Hey there!";
		ArrayList<String> periodSplit = obj.seperate(period);
	}
}
