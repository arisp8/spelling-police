package com.spelling_police;
/**
 * @class
 * Reads a text from user's keyboard or uploaded file
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Input {

	/**
	 * Read from keyboard
	 * @return String text
	 */
	public static String keyboard() {
		System.out.println("Please, write the text you want to check");
		Scanner in = new Scanner(System.in);
		String text = in.nextLine();
		return text;
	}

	/**
	 *read from file
	 *@return String text
	 */
	public static String inFile() throws IOException {
		Scanner inFile = new Scanner(System.in);
		System.out.println("Enter the file name");
		String fileName = inFile.nextLine();
		BufferedReader buffread = new BufferedReader(new FileReader(fileName));
		String text= buffread.readLine();
		return text;
		}
}
