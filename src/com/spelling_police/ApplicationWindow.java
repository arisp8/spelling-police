package com.spelling_police;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
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
        
        ImageIcon img = new ImageIcon(imagesPath + "Logo.png");
        frame.setIconImage(img.getImage());
 
        JLabel fileLabel = new JLabel("File");
        JLabel optionsLabel = new JLabel("Options");
    	
        // Set padding for the top buttons
        fileLabel.setBorder(new EmptyBorder(10, 10, 10, 0));
    	optionsLabel.setBorder(new EmptyBorder(10, 0, 10, 10));
        
    	// Increase label font size
    	fileLabel.setFont(fileLabel.getFont().deriveFont(16.0f));
    	optionsLabel.setFont(optionsLabel.getFont().deriveFont(16.0f));
    	
    	// Set gray text color for the labels
    	fileLabel.setForeground(Color.decode("#666666"));
    	optionsLabel.setForeground(Color.decode("#666666"));
    	
        JLabel fileIcon = new JLabel(new ImageIcon(imagesPath + "File.png"));
        JLabel textIcon = new JLabel(new ImageIcon(imagesPath + "Text.png"));
        JLabel linkIcon = new JLabel(new ImageIcon(imagesPath + "Link.png"));
        JLabel imageIcon = new JLabel(new ImageIcon(imagesPath + "Image.png"));
        fileIcon.addMouseListener(new LabelListener(fileIcon));
        textIcon.addMouseListener(new LabelListener(textIcon));
        linkIcon.addMouseListener(new LabelListener(linkIcon));
        imageIcon.addMouseListener(new LabelListener(imageIcon));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(fileLabel, BorderLayout.LINE_START);
        topPanel.add(optionsLabel, BorderLayout.LINE_END);
        topPanel.setBackground(Color.decode("#ebebeb"));
        
        JPanel topGroupPanel = new JPanel();
        topGroupPanel.add(textIcon, BorderLayout.WEST);
        topGroupPanel.add(fileIcon, BorderLayout.EAST);
        topGroupPanel.setBackground(Color.WHITE);
        
        JPanel bottomGroupPanel = new JPanel();
        bottomGroupPanel.add(linkIcon, BorderLayout.WEST);
        bottomGroupPanel.add(imageIcon, BorderLayout.EAST);
        bottomGroupPanel.setBackground(Color.WHITE);
        
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