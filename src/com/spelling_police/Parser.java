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
		String period = "Γη Από τη Βικιπαίδεια, την ελεύθερη εγκυκλοπαίδεια Γη  Αστρονομικό σύμβολο της Γης Η Γη (αρχαία ελληνικά: Γα?α3 λατινικά: Terra,[4] αγγλικά: Earth) αποτελεί τον 3ο πιο κοντινό πλανήτη στον Ήλιο, τον πιο πυκνό και τον 5ο μεγαλύτερο σε μάζα στο Ηλιακό Σύστημα και ειδικότερα τον μεγαλύτερο ανάμεσα στους γήινους πλανήτες, δηλαδή τους πλανήτες με στερεό φλοιό και το μοναδικό γνωστό ουράνιο σώμα που φιλοξενεί ζωή. Θέλω 200€ και έχει 20°± κελσίου!";
		Parser obj = new Parser("el");
		ArrayList<String> periodSplit = obj.seperate(period);

		System.out.println(periodSplit);
	}
}
