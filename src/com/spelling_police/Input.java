package com.spelling_police;
/**
 * @class
 * Reads a text from user's keyboard or uploaded file
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {

	/**
	 * Read from keyboard
	 * @return String text
	 */
	public static String keyboard() {

		System.out.println("Please, write the text you want to check");
		Scanner in = new Scanner(System.in);
		String text = in.nextLine();//read all user's text in one string
		in.close();
		return text;
	}

	/**
	 *read from file
	 *@return String text
	 */
	public static String inFile(String fileName, String encoding){

		try {
			BufferedReader buffread = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			String text= buffread.readLine();
			String content = "";
			while (text != null) {
				content = content + text;
				text= buffread.readLine();
			}
			buffread.close();
			return content;
		} catch(IOException ex){
			System.out.println("Could not find file " );
			return null;
		}
	}
	
	
	public static void main(String args[]) {
		String test = keyboard();
		System.out.println(test);
	}
}
