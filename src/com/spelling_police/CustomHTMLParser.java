package com.spelling_police;
import java.io.*;

/**
 * @class
 * Removes the tags from the html text
 */

public class CustomHTMLParser {

    /**
     *Removes the tags from the html text
     *@return String text
     */
    public static String removeTags(String text) {
    	// First we remove all script and style tags along with their contents.
    	String txt = text.replaceAll("<script[^<]*(?:(?!<\\/script>)<[^<]*)*<\\/script>", " ");
    	txt = txt.replaceAll("<style[^<]*(?:(?!<\\/style>)<[^<]*)*<\\/style>", " ");
    	// Then we remove all the other tags while keeping the content between them.
    	txt = txt.replaceAll("\\<[^>]*>", " ");
    	// As a final step we replace all sequences of 2 or more spaces to single spaces.
		txt = txt.trim().replaceAll("\\s{2,}", "\0");
    	
    	return txt;
    }
}
