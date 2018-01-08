package com.spelling_police;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
	
	private String language; 
	
	private  HashMap<String,List<String>> findBestSuggestions (String word , int limit){ //returns the wrong word with correct suggestions 
        
		Dictionary dict= Dictionary.getDictionary(language);
		
        HashMap<String,Double> foundWords = new HashMap<String,Double>();
        
        double fuzzyness = 0.7;
        boolean strict = true;

           do {
               foundWords = dict.fuzzySearch(word, fuzzyness, strict);
		       fuzzyness -= 0.05;
		   } while(foundWords != null && foundWords.size() < limit);
       	
           if (foundWords == null) {
    	       return null;
           }
       
		 Object[] a = foundWords.entrySet().toArray(); 
		 
		 Arrays.sort(a, new Comparator() {
		              public int compare(Object o1, Object o2) {
		              return ((Map.Entry<String, Double>) o2).getValue()
		              .compareTo(((Map.Entry<String, Double>) o1).getValue()); } }
		            );
		 
		 List<String> similarList = new ArrayList<String>();
		 
		 for (int i = 0; i < limit; i++) {
			similarList.add(((Map.Entry<String, Double>) a[i]).getKey());
		 }
		 
		 HashMap<String,List<String>> wordAndSuggestions = new  HashMap<String,List<String>>();
		 wordAndSuggestions.put(word,similarList);
		
         return wordAndSuggestions;
	}

}
