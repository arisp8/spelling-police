package com.spelling_police;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class Dictionary {
	
	private String language;
	private List<String> wordList;
	
	/** Default constructor for Dictionaries
	 * 
	 * @param language The language of the dictionary to load
	 */
	public Dictionary(String language) {
		this.language = language;
		String path = System.getProperty("user.dir") + "\\resources\\dictionaries\\" + language + ".txt";
		this.wordList = readDictionary(path);
	}
	
	/**
	 * Reads a dictionary from a file and converts it to an ArrayList
	 * @param filePath The path where the dictionary file should be found
	 * @return Returns an array list containing all words from the dictionary
	 */
	private List<String> readDictionary(String filePath) {
		List<String> words = new ArrayList<>();
		
		try (Scanner scan  = new Scanner(new File(filePath))) {
			while (scan.hasNext()) {
				words.add(scan.next());
			}
		} catch (Exception e) {
			System.out.println("An exception has happened: " + e.getMessage());
		}
		
		return words;
	}
	
	/**
	 * Checks if a word exists in the dictionary
	 * @param word The word to check 
	 * @return A boolean indicating whether or not the word exists
	 */
	private boolean wordExists(String word) {
		if (this.wordList.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		Dictionary dict = new Dictionary("el");
	}
		
}