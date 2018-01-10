package com.spelling_police;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class TopMenuListener implements ActionListener {
	
	private CompoundBorder hoverBorder;
	private ApplicationWindow applWind;
	private EmptyBorder originalBorder;
	
	/**
	 * Default constructor of TopMenuListener class
	 * @param applWind : ApplicationWindow's object
	 */
	public TopMenuListener(ApplicationWindow applWind) {
		
		this.applWind = applWind;
		
	}
	
	/**
	 * Performing the actions of the Menu for each case(choice)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			//first case : creates a new editor page
			case "New":
				applWind.createTextEditorPage();
				break;
			//second case : open a file
			case "Open":
				applWind.loadFileFromSystem();
				break;
			//third case : saves the document
			case "Save":
				applWind.saveFile();
				break;
			//last case: exits the program
			case "Exit":
				System.exit(0);
				break;
		}
		
	}
	
	

}
