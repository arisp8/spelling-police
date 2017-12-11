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
    	// First we remove all script and style tags along with their contents and then we
    	// remove all the other tags while keeping the content between them.
    	// As a final step we replace all sequences of whitespace to single spaces.
    	String txt = text.replaceAll("<script[^<]*(?:(?!</script>)<[^<]*)*</script>", " ")
          .replaceAll("<style[^<]*(?:(?!</style>)<[^<]*)*</style>", " ").replaceAll("\\<[^>]*>","")
          .replaceAll("\\s+", " ");
    	return txt;
    }
}
