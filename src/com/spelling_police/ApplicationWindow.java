package com.spelling_police;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
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

import java.awt.Component;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import javax.swing.*;

import java.awt.event.*;
//import java.awt.event.*;

public class ApplicationWindow implements MouseListener {
	
	private static JFrame frame;
	private static String imagesPath = System.getProperty("user.dir") + "\\resources\\images\\";
	private List<JComponent> dynamicComponents = new ArrayList<JComponent>();
	private JTextArea textArea;
	private JPanel suggestionsPanel;
	private TextAreaListener textAreaListener;
	private DefaultHighlightPainter correctionPainter = new DefaultHighlightPainter(Color.decode("#C6ED77"));
	private DefaultHighlightPainter mistakePainter = new DefaultHighlightPainter(Color.decode("#FF8380"));
	private boolean suggestionsActive = false;
	
	/**
	 * Creates the GUI for the starting page that appears when a user runs the
	 * application.
	 */
	public void createStartPage() {
		updatePage();

		// Create and set up the window.
		frame = new JFrame("Spelling Police");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon img = new ImageIcon(imagesPath + "Logo.png");
		frame.setIconImage(img.getImage());
		
		JMenuBar jmenubar = new JMenuBar();
		frame.setJMenuBar(jmenubar);
		
		JMenu file = new JMenu("File");
		jmenubar.add(file);
		JMenuItem new1 = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(new1);
		file.add(open);
		file.add(save);
		file.add(exit);
		
		JMenu options = new JMenu("Options");
		jmenubar.add(options);
		
		HashMap<String, Config> availableLanguages = Config.getAvailableLanguages();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		int i = 0;
		for (Config config : availableLanguages.values()) {
			JToggleButton toggleButton = new JToggleButton(config.getDisplayName());
			toggleButton.setName(config.getLanguageCode());
			buttonGroup.add(toggleButton);
			options.add(toggleButton);
			
			toggleButton.addItemListener(new ItemListener() {
			   public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange() == ItemEvent.SELECTED){
			    	  System.out.println(((Component) ev.getSource()).getName());
			    	  Config.setActiveLanguage(((Component) ev.getSource()).getName());
			      }
			   }
			});
			i++;
		}
		
		new1.addActionListener(new TopMenuListener(this));
		open.addActionListener(new TopMenuListener(this));
		save.addActionListener(new TopMenuListener(this));
		exit.addActionListener(new TopMenuListener(this));
		
		// Set padding for the top buttons
		file.setBorder(new EmptyBorder(10, 10, 10, 10));
		options.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Increase label font size
		file.setFont(file.getFont().deriveFont(16.0f));
		options.setFont(options.getFont().deriveFont(16.0f));

		// Set gray text color for the labels
		file.setForeground(Color.decode("#666666"));
		options.setForeground(Color.decode("#666666"));

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

		frame.getContentPane().add(topGroupPanel, BorderLayout.CENTER);
		frame.getContentPane().add(bottomGroupPanel, BorderLayout.AFTER_LAST_LINE);

		// Display the window.
		frame.setSize(800, 800);
		frame.setVisible(true);
	}
	
	
	
	private void highlightCorrections(int start, int end, List<Integer> corrections) {
		Highlighter hl = textArea.getHighlighter();
//        hl.removeAllHighlights();
		try {
			for (int offset : corrections) {
				Object o2 = hl.addHighlight(start + offset, start + offset + 1, correctionPainter);
			}
			Object o1 = hl.addHighlight(start, end, mistakePainter);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * Creates the text editor page where mistakes are highlighted and
	 * suggestions are displayed on right click.
	 * 
	 * @param defaultText
	 *            The default text to show in the text area.
	 */
	public void createTextEditorPage(String defaultText) {
		updatePage();

		JPanel mainPanel = new JPanel();
		textArea = new JTextArea();

		textArea.setName("main-text-editor");
		textArea.setColumns(70);
		textArea.setLineWrap(true);
		Font font = textArea.getFont();
		float size = 16.0f;
		textArea.setFont(font.deriveFont(size));
		textArea.setRows(30);
		textArea.setWrapStyleWord(true);

		textAreaListener = new TextAreaListener(textArea);
		textArea.getDocument().addDocumentListener(textAreaListener);

		textArea.setText(defaultText);

		mainPanel.setBackground(Color.WHITE);

		JScrollPane jScrollPane1 = new JScrollPane(textArea);

		textArea.addMouseListener(this);

		mainPanel.add(jScrollPane1);

		suggestionsPanel = new JPanel();

		frame.getContentPane().add(mainPanel, BoxLayout.X_AXIS);
		frame.getContentPane().add(suggestionsPanel, BorderLayout.AFTER_LAST_LINE);
		frame.setVisible(true);
		frame.repaint();

		// Add this layout's panels that should be removed when the page is
		// updated.
		dynamicComponents.add(mainPanel);
		dynamicComponents.add(suggestionsPanel);
	}
	
	public void loadFileFromSystem() {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
		   File selectedFile = fileChooser.getSelectedFile();
		   String filePath =selectedFile.toString();
		   String text = Input.inFile(filePath, Config.getActiveLanguageConfig().getEncoding());
		   createTextEditorPage(text);
		}
	}
	
	
	/**
	 * Saves the text into a file
	 * @throws IOException
	 */
	public void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    String fileSave = fileToSave.toString();
		    /*System.out.println("Save as file: " + fileToSave.getAbsolutePath());*/
		    if (textArea!=null) {
		    	FileOutputStream out = null;
				try {
					out = new FileOutputStream(fileSave);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
		    	try {
					out.write(textArea.getText().getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		    }
		}
		
	}
	
	
	
	public void loadFromRemoteURL() {
		String url = (String) JOptionPane.showInputDialog(frame, "Enter the url:", "Load from URL",
				JOptionPane.PLAIN_MESSAGE, null, null, "");

		// If a string was returned, say so.
		if ((url != null) && (url.length() > 0)) {
			String text = UrlContentReader.extractURLContents(url, true);
			if (text != null) {
				createTextEditorPage(text);
			}
		}
	}

	public void fillSuggestionsPanel(final Mistake mistake, final String wrongWord) {
		suggestionsActive = true;
		suggestionsPanel.removeAll();
		
		final int originalPosition = textArea.getCaretPosition();
		
		for (String suggestion : mistake.getSuggestions()) {
			JButton button = new JButton(suggestion);
			button.setName(suggestion);
			suggestionsPanel.add(button);

			button.addMouseListener(new MouseListener() {
				
				String startingText = "";
				
				@Override
				public void mouseClicked(MouseEvent e) {
					String replacement = e.getComponent().getName();
					textArea.setText(textArea.getText().replaceAll("\\b" + wrongWord + "\\b", replacement));
					clearSuggestionsPanel();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					startingText = textArea.getText();
					textArea.setCaretPosition(originalPosition);
					int start = 0;
					int end = 0;
					
					try {
						start = Utilities.getWordStart(textArea, textArea.getCaretPosition());
						end = Utilities.getWordEnd(textArea, textArea.getCaretPosition());
					} catch (BadLocationException exc) {
						exc.printStackTrace();
						return;
					}
					
					String replacement = e.getComponent().getName();
					List<Integer> differentIndexes = mistake.findWrongPosition(replacement);
					String newText = startingText.substring(0, start) + replacement + startingText.substring(end, startingText.length());
					textArea.setText(newText);
					highlightCorrections(start, end, differentIndexes);
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					textArea.setCaretPosition(originalPosition);
					textArea.setText(startingText);
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

		}
		frame.setVisible(true);
		frame.repaint();
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

	public void clearSuggestionsPanel() {
		suggestionsPanel.removeAll();
		frame.setVisible(true);
		frame.repaint();
		suggestionsActive = false;
	}

	public void handleTextAreaClick(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Mistake mistake = getMistakeFromPoint(e.getPoint());
			if (mistake != null) {
				suggestionsPanel.setName(mistake.getWord());
				fillSuggestionsPanel(mistake, mistake.getWord());
			} else {
				clearSuggestionsPanel();
			}
		} else {
			clearSuggestionsPanel();
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
	
	public boolean areSuggestionsActive() {
		return suggestionsActive;
	}
	
}