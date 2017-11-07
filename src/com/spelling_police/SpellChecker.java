package com.spelling_police;
import java.util.ArrayList;

public class SpellChecker {

	private String language;
	/** Default constructor for SpellChecker
	* @param lannguage The language of the dictionary
	*/

	public SpellChecker(String language) {
		this.language = language;
	}
	
	/** Method that creates an ArrayList which contains all the wrong words
	 * @param input A single word or a phrase 
	 * @return A list of words that are wrong and weren't found in the Dictionary
	*/
	private ArrayList<String> findMistakes(String input) {
		ArrayList<String> mistakes = new ArrayList<String>();
		Parser parser = new Parser(language);
		String[] words = parser.seperate(input);
		Dictionary dict = new Dictionary(language);
		for(int i = 0; i < words.length; i++) {
			boolean exists = dict.wordExists(words[i]);
			if (!exists) {
				mistakes.add(words[i]);
			}
		}
		return mistakes;
	}
	
	public static void main(String[] args) {
		SpellChecker test = new SpellChecker("el");
		ArrayList<String> test2 = test.findMistakes("παρασκεβή πηγαίνο βόλτα");
		System.out.println(test2);
		
	}
	
}





