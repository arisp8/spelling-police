package com.spelling_police;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InputTest {

	@Test
	void inputFromFileTest() {
		Input test = new Input() ;
		String output = test.inFile("texttest", "UTF-8");
		AssertEquals("Όλα καλά.", output);
		
	}

}
