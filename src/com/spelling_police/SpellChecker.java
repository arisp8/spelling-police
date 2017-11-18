package com.spelling_police;
import java.util.ArrayList;

public class SpellChecker {

	private String language;
	/** Default constructor for SpellChecker
	* @param language The language of the dictionary
	*/

	public SpellChecker(String language) {
		this.language = language;
	}
	
	/** Method that creates an ArrayList which contains all the wrong words
	 * @param input A single word or a phrase 
	 * @return A list of words that are wrong and weren't found in the Dictionary
	*/
	public ArrayList<Mistake> findMistakes(String input) {
		ArrayList<Mistake> mistakesArray = new ArrayList<Mistake>();
		Parser parser = new Parser(language);
		String[] words = parser.seperate(input);
		for(int i = 0; i < words.length; i++) {
			Mistake singleWord = singleWordCheck(words[i]);
			if (singleWord!=null) {
				mistakesArray.add(singleWord);
			}
		}
		return mistakesArray;
	}
	
	
	/** Method that checks a single word every time findMistakes method calls it
	 * @param checkWord the word to be checked
	 *@return a Mistake object if the word is not contained in the dictionary or null if the word is contained
	 */
	public Mistake singleWordCheck(String checkWord)
	{
		Dictionary dict = new Dictionary(language);
		Mistake wrongWord = new Mistake(checkWord);
		boolean exists = dict.wordExists(checkWord);
		if (!exists){
			return wrongWord;
		} else {
			return null;
		}
	}
	
	
	
	
	// Just to check that everithing works fine
	public static void main(String[] args) {
		SpellChecker test = new SpellChecker("el");
		ArrayList<String> test2 = test.findMistakes("παρασκεβή πηγαίνο βόλτα");
		System.out.println(test2);
		
	}
	
}





