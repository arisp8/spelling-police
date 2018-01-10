package com.spelling_police;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.*;

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
