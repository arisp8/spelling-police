package com.spelling_police;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
//import java.awt.event.*;

public class ApplicationWindow {
	
	private static String imagesPath = System.getProperty("user.dir") + "\\resources\\images\\";
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
    	fileLabel.setBorder(new EmptyBorder(10, 10, 15, 0));
    	optionsLabel.setBorder(new EmptyBorder(10, 0, 15, 10));
        
        JLabel fileIcon = new JLabel(new ImageIcon(imagesPath + "File.png"));
        JLabel textIcon = new JLabel(new ImageIcon(imagesPath + "Text.png"));
        JLabel linkIcon = new JLabel(new ImageIcon(imagesPath + "Link.png"));
        JLabel imageIcon = new JLabel(new ImageIcon(imagesPath + "Image.png"));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(fileLabel, BorderLayout.LINE_START);
        topPanel.add(optionsLabel, BorderLayout.LINE_END);
        
        JPanel topGroupPanel = new JPanel();
        topGroupPanel.add(textIcon, BorderLayout.WEST);
        topGroupPanel.add(fileIcon, BorderLayout.EAST);
        
        JPanel bottomGroupPanel = new JPanel();
        bottomGroupPanel.add(linkIcon, BorderLayout.WEST);
        bottomGroupPanel.add(imageIcon, BorderLayout.EAST);
        
    	frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);
    	frame.getContentPane().add(topGroupPanel, BorderLayout.CENTER);
    	frame.getContentPane().add(bottomGroupPanel, BorderLayout.AFTER_LAST_LINE);
        //Display the window.
        frame.setSize(800, 800);
        frame.setVisible(true);
    }
	
	
	public static void main(String[] args) {
		createStartPage();		
	}

}