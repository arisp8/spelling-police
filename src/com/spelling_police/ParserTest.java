package com.spelling_police;

import static org.junit.Assert.*; 

import java.util.ArrayList;

import org.junit.Test;

public class ParserTest {
	
Parser test = new Parser();

	@Test
	public void isWordTest() {
		boolean output = test.isWord("�������");
		assertEquals(true,output);	
	}
	
	@Test
	public void separateTest() {
		ArrayList<ArrayList<String>> output = test.separate("� ������� ���� ���� �������.���� ��� �� ����� �� ��� �����, ��� �����.");
		ArrayList<String> myArray = new ArrayList<String>();
	    myArray.add("�,�������,����,����,�������");
	    myArray.add("����,���,��,�����,�� ��� �����,���,�����");
		assertEquals(myArray,output);	
	}
}
