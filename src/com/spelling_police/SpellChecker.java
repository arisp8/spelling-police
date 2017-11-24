package com.spelling_police;
import java.util.ArrayList;

public class SpellChecker {

	private String language;
	private Dictionary dict;
	private Parser parser;
	/** Default constructor for SpellChecker
	* @param language The language of the dictionary
	*/

	public SpellChecker(String language) {
		this.language = language;
		
		// Initialize required components
		dict = new Dictionary(language);
		parser = new Parser(language);
	}
	
	/** Method that creates an ArrayList which contains all the wrong words
	 * @param input A single word or a phrase 
	 * @return A list of words that are wrong and weren't found in the Dictionary
	*/
	public ArrayList<Mistake> findMistakes(String input) {
		
		ArrayList<Mistake> mistakes = new ArrayList<Mistake>();
		
		ArrayList<String> words = parser.seperate(input);
		for(String word : words) {
			Mistake singleWord = singleWordCheck(word);
			if (singleWord != null) {
				mistakes.add(singleWord);
			}
		}
		return mistakes;
	}
	
	
	/** Method that checks a single word
	 * @param checkWord the word to be checked
	 *@return a Mistake object if the word is not contained in the dictionary or null if the word is contained
	 */
	public Mistake singleWordCheck(String word) {
		if (!dict.wordExists(word)){
			Mistake wrongWord = new Mistake(word);
			return wrongWord;
		} else {
			return null;
		}
	}
	
	
	
	
	// Just to check that everything works fine
	public static void main(String[] args) {
		long start = System.nanoTime();
		SpellChecker test = new SpellChecker("el");
		ArrayList<Mistake> test2 = test.findMistakes("παρασκεβή πηγαίνο βόλτα");
		System.out.println(test2);
		
		ArrayList<Mistake> test3 = test.findMistakes("Η θαλάσσια χελώνα καρέττα (Caretta caretta) είναι είδος με παγκόσμια κατανομή. Ανήκει στην οικογένεια των χελωνιίδων. Οι χελώνες αυτές έχουν, κατά μέσο όρο, μήκος καβουκιού 100 με 120 cm όταν αναπτυχθούν πλήρως. Μια ενήλικη χελώνα ζυγίζει περίπου 100 εώς 150 kg, με τις μεγαλύτερες να ζυγίζουν περισσότερο από 450 kg. Το χρώμα του δέρματος κυμαίνεται από κίτρινο έως καστανό και το κέλυφος είναι συνήθως κοκκινωπό-καφέ. Μέχρις ότου η χελώνα ενηλικιωθεί δεν είναι ορατές εξωτερικές διαφορές ως προς το φύλο. Η πιο προφανής διαφορά είναι ότι τα ενήλικα αρσενικά έχουν πιο χοντρές ουρές και μικρότερο πλάστρο από τα θηλυκά.");
		System.out.println(test3);
		long finish = System.nanoTime();
		double elapsed = (double) (finish - start) / 1000000000;
		System.out.println("Seconds elapsed: " + elapsed);
	}
	
}





