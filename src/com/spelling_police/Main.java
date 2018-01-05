package com.spelling_police;

public class Main {
	
	public static void main(String[] args) {
		Config.bootstrap();
		ApplicationWindow window = new ApplicationWindow();
		window.createStartPage();
	}
	
}
