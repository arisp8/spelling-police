package com.spelling_police;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputTest {

	@Test
	public void inputFromFileTest() {
		Input test = new Input() ;
		String output = test.inFile(System.getProperty("user.dir") + "\\resources\\test\\text_el.txt", "windows-1253");
		assertEquals("Όλα καλά!", output);
		output = test.inFile(System.getProperty("user.dir") + "\\resources\\test\\text_en.txt", "UTF-8");
		assertEquals("It's all good.", output);

	}

}