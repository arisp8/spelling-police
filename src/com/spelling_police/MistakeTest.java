package com.spelling_police;

import static org.junit.Assert.*;

import org.junit.Test;

class MistakeTest {

	@Test
	public void findWrongPositionTest() {
	Mistake test = new Mistake("ασταίρι", "el", 1 ,1);
	List<Integer> output = test.findWrongPosition("ασταίρι");
	List<Integer> list1 = new List<Integer>();
	list1.add(new Mistake("ασταίρι","el",1 ,1));

	assertListEquals(list1, output);


	}


}


