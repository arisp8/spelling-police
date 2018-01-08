package com.spelling_police;

import static org.junit.Assert.*; 

import java.util.ArrayList;

import org.junit.Test;

public class ParserTest {
	
Parser test = new Parser();

	@Test
	public void isWordTest() {
		boolean output = test.isWord("Μαθητής");
		assertEquals(true,output);	
	}
	
	@Test
	public void separateTest() {
		ArrayList<ArrayList<String>> output = test.separate("Η δασκάλα ήναι πολύ βαραιτή.Ομώς εγώ θα πρέπη να την ακούω, όσο μιλάη.");
		ArrayList<String> myArray = new ArrayList<String>();
	    myArray.add("Η,δασκάλα,ήναι,πολύ,βαραιτή");
	    myArray.add("Ομώς,εγώ,θα,πρέπη,να την ακούω,όσο,μιλάη");
		assertEquals(myArray,output);	
	}
}
