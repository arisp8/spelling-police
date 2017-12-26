package com.spelling_police;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
//import java.awt.event.*;

public class ApplicationWindow {
	
	private static JFrame frame;
	private static String imagesPath = System.getProperty("user.dir") + "\\resources\\images\\";
	private static List<JComponent> dynamicComponents = new ArrayList<JComponent>();
	
	/**
     * Creates the GUI for the starting page that appears
     * when a user runs the application.
     */
    private static void createStartPage() {
    	updatePage();
    	
    	//Create and set up the window.
        frame = new JFrame("Spelling Police");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon img = new ImageIcon(imagesPath + "Logo.png");
        frame.setIconImage(img.getImage());
 
        JLabel fileLabel = new JLabel("File");
        JLabel optionsLabel = new JLabel("Options");
    	
        // Set padding for the top buttons
        fileLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
    	optionsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
    	// Increase label font size
    	fileLabel.setFont(fileLabel.getFont().deriveFont(16.0f));
    	optionsLabel.setFont(optionsLabel.getFont().deriveFont(16.0f));
    	
    	fileLabel.addMouseListener(new TopMenuListener(fileLabel));
    	optionsLabel.addMouseListener(new TopMenuListener(optionsLabel));
    	
    	// Set gray text color for the labels
    	fileLabel.setForeground(Color.decode("#666666"));
    	optionsLabel.setForeground(Color.decode("#666666"));
    	
        JLabel fileIcon = new JLabel(new ImageIcon(imagesPath + "File.png"));
        JLabel textIcon = new JLabel(new ImageIcon(imagesPath + "Text.png"));
        JLabel linkIcon = new JLabel(new ImageIcon(imagesPath + "Link.png"));
        JLabel imageIcon = new JLabel(new ImageIcon(imagesPath + "Image.png"));
        
        fileIcon.addMouseListener(new MainIconListener(fileIcon));
        textIcon.addMouseListener(new MainIconListener(textIcon));
        linkIcon.addMouseListener(new MainIconListener(linkIcon));
        imageIcon.addMouseListener(new MainIconListener(imageIcon));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(fileLabel, BorderLayout.LINE_START);
        topPanel.add(optionsLabel, BorderLayout.LINE_END);
        topPanel.setBackground(Color.WHITE);
        
        JPanel topGroupPanel = new JPanel();
        topGroupPanel.add(textIcon, BorderLayout.WEST);
        topGroupPanel.add(fileIcon, BorderLayout.EAST);
        topGroupPanel.setBackground(Color.WHITE);
        
        JPanel bottomGroupPanel = new JPanel();
        bottomGroupPanel.add(linkIcon, BorderLayout.WEST);
        bottomGroupPanel.add(imageIcon, BorderLayout.EAST);
        bottomGroupPanel.setBackground(Color.WHITE);
        
        dynamicComponents.add(topGroupPanel);
        dynamicComponents.add(bottomGroupPanel);
        
    	frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);
    	frame.getContentPane().add(topGroupPanel, BorderLayout.CENTER);
    	frame.getContentPane().add(bottomGroupPanel, BorderLayout.AFTER_LAST_LINE);
    	//Display the window.
        frame.setSize(800, 800);
        frame.setVisible(true);
    }
    
    private static void updatePage() {
    	
    	int index = 0;
    	for (JComponent component : dynamicComponents) {
			frame.getContentPane().remove(component);
			index++;
		}
    	
	    dynamicComponents = new ArrayList<JComponent>();
    }
    
    public static void createTextEditorPage() {
    	updatePage();
    	
    	JPanel mainPanel = new JPanel();
    	JLabel textLabel = new JLabel("Enter your text");
    	JTextArea textArea = new JTextArea();
    	
    	textArea.setColumns(70);
        textArea.setLineWrap(true);
        textArea.setRows(40);
        textArea.setWrapStyleWord(true);
        
        mainPanel.setBackground(Color.WHITE);
        
        JScrollPane jScrollPane1 = new JScrollPane(textArea);
    	textArea.getDocument().addDocumentListener(new TextAreaListener(textArea));
        
//    	JTextPane textPane = new JTextPane();
//    	textPane.setText("Lorem ipsum dolor sit amet, consecteur in the l.a. noire sequel");
//    	StyledDocument doc = textPane.getStyledDocument();
//    	
//    	SimpleAttributeSet keyWord = new SimpleAttributeSet();
//    	StyleConstants.setForeground(keyWord, Color.RED);
//    	StyleConstants.setBackground(keyWord, Color.YELLOW);
//    	StyleConstants.setUnderline(keyWord, Boolean.TRUE );
//    	StyleConstants.setBold(keyWord, true);
//    	
//    	doc.setCharacterAttributes(20, 4, keyWord, false);
    	
    	mainPanel.add(textLabel);
    	mainPanel.add(jScrollPane1);
//    	mainPanel.add(textPane);
    	
    	frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    	frame.setVisible(true);
    	frame.repaint();
    }
	
	
	public static void main(String[] args) {
		createStartPage();		
	}

}