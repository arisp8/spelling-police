package com.spelling_police;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.spelling_police.Dictionary;

public class DictionaryTests {
	
	private static Dictionary dictionary = new Dictionary("el");
	
    @Test
    public void itHasALanguageCode() {
        assertEquals("el", dictionary.getLanguage(), "The language code for Greek should be el.");
    }
}