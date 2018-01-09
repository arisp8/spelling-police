package com.spelling_police;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	 * constructor
	 * @param label
	 */
	public TopMenuListener(ApplicationWindow applWind) {
		
		this.applWind = applWind;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "New":
				applWind.createTextEditorPage();
				break;
			case "Open":
				applWind.loadFileFromSystem();
				break;
			case "Save":
				break;
			case "Exit":
				System.exit(0);
				break;
		}
		
	}
	
	

}
