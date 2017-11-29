package com.spelling_police;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
//import java.awt.event.*;

public class ApplicationWindow {
	
	/**
     * Creates the GUI for the starting page that appears
     * when a user runs the application.
     */
    private static void createStartPage() {
        //Create and set up the window.
        JFrame frame = new JFrame("Spelling Police");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel fileLabel = new JLabel("File");
        JLabel optionsLabel = new JLabel("Options");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(fileLabel, BorderLayout.LINE_START);
        panel.add(optionsLabel, BorderLayout.LINE_END);
        
    	frame.getContentPane().add(panel, BorderLayout.PAGE_START);
        //Display the window.
        frame.setSize(800, 800);
        frame.setVisible(true);
    }
	
	
	public static void main(String[] args) {
		createStartPage();		
	}

}