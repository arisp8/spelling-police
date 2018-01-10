package com.spelling_police;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;

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
		spellCheck = new SpellChecker();
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
				} else if (lastChar == ' ' || lastChar == ',' || lastChar == '\n' && currentWord != null) {
					currentWord = currentWord.replace(',', '\0');
					// Call singleWordCheck with strict enabled to make sure everything works correctly.
					Mistake currentMistake = spellCheck.singleWordCheck(currentWord, sentenceCount, wordCount, true);
					
					if (currentMistake != null) {
						mistakesFound.put(currentWord, currentMistake);
					}
					
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
            // In order to avoid matching substrings in other words, we make sure
			// to only highlight words that are followed by either whitespace or 
			// punctuation.
            Matcher m = Pattern.compile("([\\s\\p{P}]|^)(" + word + ")([\\s\\p{P}])").matcher(text);
            
            while (m.find()) {
            	try {
					Object o = hl.addHighlight(m.start() + m.group(1).length(), m.start() + m.group(1).length() + word.length(), mistakePainter);
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
