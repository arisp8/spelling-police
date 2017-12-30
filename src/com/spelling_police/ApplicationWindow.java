package com.spelling_police;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.SwingUtilities;

import java.awt.Robot;
import java.awt.Dimension;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.awt.event.*;

public class ApplicationWindow implements MouseListener {
	
	private static JFrame frame;
	private static String imagesPath = System.getProperty("user.dir") + "\\resources\\images\\";
	private List<JComponent> dynamicComponents = new ArrayList<JComponent>();
	private JTextArea textArea;
	private JPanel suggestionsPanel;
	private TextAreaListener textAreaListener;
	private DefaultHighlightPainter mistakePainter = new DefaultHighlightPainter(Color.decode("#FF8380"));
	
	/** 
     * Creates the GUI for the starting page that appears
     * when a user runs the application.
     */
    private void createStartPage() {
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
        fileIcon.setName("from-file");
        JLabel textIcon = new JLabel(new ImageIcon(imagesPath + "Text.png"));
        textIcon.setName("text-editor");
        JLabel linkIcon = new JLabel(new ImageIcon(imagesPath + "Link.png"));
        linkIcon.setName("from-url");
        JLabel imageIcon = new JLabel(new ImageIcon(imagesPath + "Image.png"));
        imageIcon.setName("from-image");
        
        fileIcon.addMouseListener(new MainIconListener(fileIcon, this));
        textIcon.addMouseListener(new MainIconListener(textIcon, this));
        linkIcon.addMouseListener(new MainIconListener(linkIcon, this));
        imageIcon.addMouseListener(new MainIconListener(imageIcon, this));
        
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
        
        // Specify the panels that should be removed on update.
        dynamicComponents.add(topGroupPanel);
        dynamicComponents.add(bottomGroupPanel);
        
    	frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);
    	frame.getContentPane().add(topGroupPanel, BorderLayout.CENTER);
    	frame.getContentPane().add(bottomGroupPanel, BorderLayout.AFTER_LAST_LINE);
    	
    	// Display the window.
        frame.setSize(800, 800);
        frame.setVisible(true);
    }
    
    /**
     * Removes all dynamic elements, e.g. all elements that should be replaced
     * when the layout is changed.
     */
    private void updatePage() {
    	
    	int index = 0;
    	for (JComponent component : dynamicComponents) {
			frame.getContentPane().remove(component);
			index++;
		}
    	
	    dynamicComponents = new ArrayList<JComponent>();
    }
    
    /**
     * Creates a text editor page without a default string.
     */
    public void createTextEditorPage() {
    	createTextEditorPage("");
    }
    
    /**
     * Creates the text editor page where mistakes are highlighted 
     * and suggestions are displayed on right click.
     * @param defaultText The default text to show in the text area.
     */
    public void createTextEditorPage(String defaultText) {
    	updatePage();
    	
    	JPanel mainPanel = new JPanel();
    	JLabel textLabel = new JLabel("Enter your text");
    	textArea = new JTextArea();
    	
    	textArea.setName("main-text-editor");
    	textArea.setColumns(70);
        textArea.setLineWrap(true);
        textArea.setRows(40);
        textArea.setWrapStyleWord(true);
        
        mainPanel.setBackground(Color.WHITE);
        
        JScrollPane jScrollPane1 = new JScrollPane(textArea);
        textAreaListener = new TextAreaListener(textArea);
        textArea.getDocument().addDocumentListener(textAreaListener);
        
    	textArea.addMouseListener(this);
    	
    	mainPanel.add(textLabel);
    	mainPanel.add(jScrollPane1);
    	
    	suggestionsPanel = new JPanel();
    	
    	frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    	frame.getContentPane().add(suggestionsPanel, BorderLayout.AFTER_LAST_LINE);
    	frame.setVisible(true);
    	frame.repaint();
    	
    	// Add this layout's panels that should be removed when the page is updated.
    	dynamicComponents.add(mainPanel);
    	dynamicComponents.add(suggestionsPanel);
    }
    
    public void loadFileFromSystem() {
    	// wip
    }
    
    public void loadFromRemoteURL() {
    	// wip
    }
	
    public void fillSuggestionsPanel(List<String> suggestions) {
    	suggestionsPanel.removeAll();
    	for (String suggestion : suggestions) {
    		JButton button = new JButton(suggestion);
    		button.setName(suggestion);
    		suggestionsPanel.add(button);
    	}
    	frame.setVisible(true);
    	frame.repaint();
    }
	
	public static void main(String[] args) {
		ApplicationWindow window = new ApplicationWindow();
		window.createStartPage();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String name = e.getComponent().getName();
		
		switch (name) {
			case "main-text-editor":
				handleTextAreaClick(e);
		}
		
	}
	
	public Mistake getMistakeFromPoint(Point point) {
		textArea.setCaretPosition(textArea.viewToModel(point));
	    String currentWord = findCurrentWord(textArea);
	    Mistake mistake = textAreaListener.mistakeFromWord(currentWord);
	    return mistake;
	}
	
	protected String findCurrentWord(JTextArea ta) {
        try {
            int start = Utilities.getWordStart(ta, ta.getCaretPosition());
            int end = Utilities.getWordEnd(ta, ta.getCaretPosition());
            String text = ta.getDocument().getText(start, end - start);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "";
    }
	
	public void handleTextAreaClick(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Mistake mistake = getMistakeFromPoint(e.getPoint());
			if (mistake != null) {
				suggestionsPanel.setName(mistake.getWord());
				fillSuggestionsPanel(mistake.getSuggestions());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}