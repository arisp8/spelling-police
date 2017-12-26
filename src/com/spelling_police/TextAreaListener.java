package com.spelling_police;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.Utilities;

public class TextAreaListener implements DocumentListener {
	
	private JTextArea textArea;
	private String currentWord;
	private List<Mistake> mistakesFound;
	private SpellChecker spellCheck;
	
	private int sentenceCount = 1;
	private int wordCount = 1;
	
	private int lastWordStart;
	private int lastWordEnd;
	
	private DefaultHighlightPainter mistakePainter;
	
	public TextAreaListener(JTextArea textArea) {
		this.textArea = textArea;
		mistakesFound = new ArrayList<Mistake>();
		spellCheck = new SpellChecker("el");
		mistakePainter = new DefaultHighlightPainter(Color.RED);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		//
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		int offset = e.getOffset();
		try {
			char lastChar = e.getDocument().getText(offset, 1).charAt(0);
			
			if (Character.isLetterOrDigit(lastChar)) {
				currentWord = findCurrentWord(this.textArea);
			} else if (currentWord != "") {
				Mistake currentMistake = spellCheck.singleWordCheck(currentWord, sentenceCount, wordCount);
				
				if (currentMistake != null) {
					highlight(lastWordStart, lastWordEnd);
					mistakesFound.add(currentMistake);
				}
				
				currentWord = "";
				
				if (isPunctuation(lastChar)) {
					sentenceCount++;
					wordCount = 1;
				} else {
					wordCount++;
				}
				
			}
			
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	protected void highlight(int start, int end) {
		try {
	        Highlighter hilite = this.textArea.getHighlighter();
	        int pos = start;

            hilite.addHighlight(start, end, mistakePainter);
	         
	    } catch (BadLocationException e) {
	    	//
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
            ;
    }

}
