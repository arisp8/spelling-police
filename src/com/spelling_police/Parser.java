package com.spelling_police;

import java.util.Scanner;

/**
 * Parser splits Strings into individual words and inserts them in a table.
 * The method returns the table 'word'.
 */
public class Parser {
	
	private String language;
	
	public Parser(String language) {
		this.language = language;
	}
	
	//Separate : the method which splits the Strings 
	public String[] seperate(String period){
		//Creation of a table the same size as the number of the words in the period.
		String words[] = period.split("\\s+");
		
		//Inserts the words in order into the table.
		for (int i=0;i<words.length;i++){
			words[i] = words[i].replaceAll("[^\\w]" , "");
		}
		//Returns the table.
		return words;
	}
	
	public static void main (String args[] ){
		Scanner input = new Scanner(System.in);
		System.out.println("Insert Text : ");
		String period = input.nextLine();
		Parser obj = new Parser("el");
		String[] periodSplit = obj.seperate(period);
		
		for (int i = 0; i < periodSplit.length; i++) {
				System.out.println(periodSplit[i]);
		}
		input.close();
	}
}
