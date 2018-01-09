package com.spelling_police;

import static org.junit.Assert.*;

import org.junit.Test;

class InputTest {

	@Test
	void inputFromFileTest() {
		Input test = new Input() ;
		String output = test.inFile("texttest", "UTF-8");
		AssertEquals("Όλα καλά.", output);

	}

}
