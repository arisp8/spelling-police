package com.spelling_police;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MistakeTest {

	@Test
	public void findWrongPositionTest() {
		Config.bootstrap();
		Mistake test = new Mistake("δοκιμί", "el", 1 ,1);
		List<Integer> output = test.findWrongPosition("δοκιμή");
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(5);
	
		assertArrayEquals(list1.toArray(), output.toArray());


	}


}


