package com.spelling_police;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	
	public static void setLookAndFeel() {
		
		try {
            // Set system look and feel.
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       System.out.println("The System Look And Feel is not supported. " + e.getMessage());
	    }
	    catch (ClassNotFoundException e) {
	    	System.out.println("The class for the System Look and Feel was not found. " + e.getMessage());
	    }
		catch (InstantiationException e) {
			System.out.print("The look and feel could not be instantiated.");
	    }
	    catch (IllegalAccessException e) {
	       System.out.println("The class for the system look and feel could not be accessed");
	    }
		
	}
	
	public static void main(String[] args) {
		Config.bootstrap();
		setLookAndFeel();
		ApplicationWindow window = new ApplicationWindow();
		window.createStartPage();
	}

}
