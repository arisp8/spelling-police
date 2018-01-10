package com.spelling_police;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InputTest {

	@Test
	public void inputFromFileTest() {
		String output = Input.inFile(System.getProperty("user.dir") + "\\resources\\test\\text_el.txt", "windows-1253");
		assertEquals("Όλα καλά!", output);
		output = Input.inFile(System.getProperty("user.dir") + "\\resources\\test\\text_en.txt", "UTF-8");
		assertEquals("It's all good.", output);

	}

}