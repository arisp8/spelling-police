package com.spelling_police;

import java.util.ArrayList;
import java.util.List;

public class SpellChecker {

	private Parser parser;
	private Dictionary dict;
	/**
	 * Default constructor for SpellChecker
	 * 
	 * @param language
	 *            The language of the dictionary
	 */

	public SpellChecker() {
		dict = Dictionary.getDictionary(Config.getActiveLanguageConfig().getLanguageCode());
		parser = new Parser();
	}

	/**
	 * Method that creates an ArrayList which contains all the wrong words
	 * 
	 * @param input
	 *            A single word or a phrase
	 * @return A list of words that are wrong and weren't found in the
	 *         Dictionary
	 */
	public ArrayList<Mistake> findMistakes(String input, int sentenceStart, int wordStart) {

		ArrayList<Mistake> mistakes = new ArrayList<Mistake>();
		ArrayList<ArrayList<String>> sentences = parser.separate(input);
		for (List<String> sentence : sentences) {
			for (String word : sentence) {
				Mistake singleWord = singleWordCheck(word, sentenceStart, wordStart);
				if (singleWord != null) {
					mistakes.add(singleWord);
				}
				wordStart++;
			}
			sentenceStart++;
			wordStart = 1;
		}
		return mistakes;
	}

	public ArrayList<Mistake> findMistakes(String input) {
		return findMistakes(input, 1, 1);
	}

	/**
	 * Checks the spelling of a single word
	 * @param word The word to be checked
	 * @return a Mistake object if the word is not contained in the dictionary
	 * or null if the word is contained
	 */
	public Mistake singleWordCheck(String word, int sentence, int position, boolean strict) {
		Mistake wrongWord = null;
		
		// When strict is enabled it means that the word probably hasn't been validated already.
		if (strict && !this.parser.isWord(word)) {
			return null;
		}
		
		if (!dict.wordExists(word)) {
			wrongWord = new Mistake(word, dict.getLanguage(), sentence, position);
		}
		return wrongWord;
	}
	
	/**
	 * Wrapper method for singleWordCheck. When no value is given for strict we default to false.
	 */
	public Mistake singleWordCheck(String word, int sentence, int position) {
		return this.singleWordCheck(word, sentence, position, false);
	}

}
