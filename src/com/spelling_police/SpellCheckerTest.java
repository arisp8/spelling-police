package com.spelling_police;

import static org.junit.Assert*;

import java.util.ArrayList;

import org.junit.Test;

class SpellCheckerTest {

	@Test
	void spellCheckerFindsMistakeInString() {
		SpellChecker test = new SpellChecker();
		ArrayList<Mistake> output = test.findMistakes("αβγδ");
		ArrayList<Mistake> list1 = new ArrayList<Mistake>();
		list1.add(new Mistake("αβγδ", "el", 1, 1));

		assertArrayEquals(list1.toArray(),output.toArray());

	}

}
