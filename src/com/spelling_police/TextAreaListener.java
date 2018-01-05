package com.spelling_police;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextAreaListener implements DocumentListener {
	
	private JTextArea textArea;
	private String currentWord;
	private HashMap<String, Mistake> mistakesFound;
	private SpellChecker spellCheck;
	private Parser parser;
	
	private int sentenceCount = 1;
	private int wordCount = 1;
	
	private int lastWordStart;
	private int lastWordEnd;
	
	private DefaultHighlightPainter mistakePainter = new DefaultHighlightPainter(Color.decode("#FF8380"));
	
	public TextAreaListener(JTextArea textArea) {
		this.textArea = textArea;
		mistakesFound = new HashMap<String, Mistake>();
		spellCheck = new SpellChecker("el");
		parser = new Parser();
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		
	}
	
	public HashMap<String, Mistake> getMistakesFound() {
		return mistakesFound;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		int offset = e.getOffset();
		
		try {
			// If more than a single character have been appended then it's 
			// probably a paste operation and we need to find all mistakes in there.
			if (e.getLength() > 1) {
				
				String newText = this.textArea.getText(offset, e.getLength());
				
				ArrayList<Mistake> mistakes = spellCheck.findMistakes(newText, sentenceCount, wordCount);
				
				for (Mistake mistake : mistakes) {
					String word = mistake.getWord();
					mistakesFound.put(word, mistake);
				}
					
				
			} else {
				
				char lastChar = e.getDocument().getText(offset, 1).charAt(0);
				
				if (Character.isLetterOrDigit(lastChar)) {
					currentWord = findCurrentWord(this.textArea);
				} else if (currentWord != "" && currentWord != null) {
					Mistake currentMistake = spellCheck.singleWordCheck(currentWord, sentenceCount, wordCount);
					
					if (currentMistake != null) {
						mistakesFound.put(currentWord, currentMistake);
					}
					
					currentWord = "";
					
					if (isPunctuation(lastChar)) {
						sentenceCount++;
						wordCount = 1;
					} else {
						wordCount++;
					}
					
				}
				
			}
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		
		highlightMistakes();
		
	}
	
	private void highlightMistakes() {
		
		String text = textArea.getText();
		Highlighter hl = textArea.getHighlighter();
        hl.removeAllHighlights();
        
		for (String word : mistakesFound.keySet()) {                          
            
            Matcher m = Pattern.compile(word + "[-\\s.?;:]").matcher(text);
            
            while (m.find()) {
            	try {
					Object o = hl.addHighlight(m.start(), m.start() + word.length(), mistakePainter);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
           
            
		}
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected String findCurrentWord(JTextArea ta) {
        try {
            int start = Utilities.getWordStart(ta, ta.getCaretPosition());
            int end = Utilities.getWordEnd(ta, ta.getCaretPosition());
            String text = ta.getDocument().getText(start, end - start);
            
            if (Character.isLetterOrDigit(text.charAt(text.length() - 1))) {
            	lastWordStart = start;
            	lastWordEnd = end;
            }
            
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "";
    }
	
	protected static boolean isPunctuation(char c) {
        return c == ','
            || c == '.'
            || c == '!'
            || c == '?'
            || c == ':'
            || c == ';'
            || c == '\''
            ;
    }
	
	public Mistake mistakeFromWord(String word) {
		return mistakesFound.get(word);
	}

}
