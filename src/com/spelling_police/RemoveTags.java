package com.spelling_police;

import java.io.*;

/**
 * @class
 * Removes the tags from the html text
 */

public class RemoveTags {

		/**
		 *Removes the tags from the html text
		 *@return String text
	 	 */

		public String removeTags(String text) {
		String txt = text.replaceAll("<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>", " ");
		txt = text.replaceAll("\\<[^>]*>","");
		return txt;
		}
}
