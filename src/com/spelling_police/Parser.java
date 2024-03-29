package com.spelling_police;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
* @class Parser splits Strings/texts into their components.
*/
public class Parser {

	private static Pattern pattern = Pattern.compile("^[^A-Za-zΑ-Ωα-ωά-ώΐϊϋ\\p{P}]{1,}$");
	private static Pattern endOfSentence = Pattern.compile("([\\.!\\?;]){1,}(\\s+[Α-ΖΑ-Ω]|$)");
	private static Pattern acronyms = Pattern.compile("(?:[a-zA-Zα-ωΑ-Ω]\\.){2,}");

 	/**
 	* Separates the given text/string into  periods and
 	* returns an ArrayList containing the period.
 	* @param text This is the given text
 	* @return sentences ArrayList This the Arraylist containin the sentence
 	*/
	protected ArrayList<String> splitIntoSentences(String text) {
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

	/**
	* Checks if the element in question is a  word
	* @param element This is the element of the period about to be checked
	* @return boolean This returns 'true' if the element is a word or 'false' otherwise
	*/
	public boolean isWord(String element) {
		if (element.length() == 0) {
			return false;
		}

		if (acronyms.matcher(element).find()) {
			return false;
		}

		if (pattern.matcher(element).find()) {
			return false;
		}

		return true;
	}

 	/**
 	*Splits every period of the given text into individual words
	*@param text The given text
     @return ArrayList<ArrayList<String>> This returns the parsed text
	*/
	public ArrayList<ArrayList<String>> separate(String text){

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		ArrayList<String> sentences = this.splitIntoSentences(text);
		for (String sentence : sentences) {

			String[] elements  = sentence.split("\\s+");
			ArrayList<String> words = new ArrayList<String>();

			for (int i=0; i < elements.length; i++){

				// Having asserted that the current element is not an acronym, it's safe now to remove
				// any punctuation.
				if (isWord(elements[i])) {
					elements[i] = elements[i].replaceAll("\\p{P}", "");
					if(elements[i].length() > 0) {
						words.add(elements[i]);
					}
				}
			}
			result.add(words);
		}

		return result;
	}
}
