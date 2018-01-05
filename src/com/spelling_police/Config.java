package com.spelling_police;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
	
	private String encoding;
	private String displayName;
	private String languageCode;
	private boolean active;
	
	private static HashMap<String, Config> availableLanguages = new HashMap<String, Config>();
	private static String activeLanguage = "";
	
	public static void bootstrap() {
		activeLanguage = findActiveLanguage();
		
		File folder = new File(System.getProperty("user.dir") + "\\resources\\config\\");
		for (final File fileEntry : folder.listFiles()) {
            
			if (fileEntry.getName().equals("active_language.txt")) {
				continue;
			}
			
			String languageName = fileEntry.getName().replaceAll(".info", "");
            boolean active = activeLanguage.equals(languageName);
			Config config = new Config(languageName, active);
			availableLanguages.put(languageName, config);
	    }
	}
	
	private static String findActiveLanguage() {
		File languagesFile = new File(System.getProperty("user.dir") + "\\resources\\config\\active_language.txt");
		String language = "";
		
		try {
			Scanner scan = new Scanner(languagesFile);
			if (scan.hasNext()) {
				language = scan.next();
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return language;
	}
	
	public Config(String language) {
		this(language, true);
	}
	
	public Config(String language, boolean active) {
		String configPath = System.getProperty("user.dir") + "\\resources\\config\\" + language + ".info";
		HashMap<String, String> fileConfig = loadConfig(configPath);
		this.languageCode = language;
		this.encoding = fileConfig.get("encoding");
		this.displayName = fileConfig.get("display_name");
		this.active = active;
	}
	
	private HashMap<String, String> loadConfig(String filePath) {
		HashMap<String, String> config = new HashMap<String, String>();

		try (Scanner scan  = new Scanner(new File(filePath), "utf-8")) {
			while (scan.hasNext()) {
				String line = scan.next();
				String[] components = line.split(":");
				if (components.length == 2) {
					config.put(components[0], components[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong when opening config file: " + e.getMessage());
		}


		return config;
	}
	
	public String getEncoding() {
		return this.encoding;
	}
	
	public String getLanguageCode() {
		return this.languageCode;
	}
	
	public static Config getActiveLanguageConfig() {
		return availableLanguages.get(activeLanguage);
	}
	
}
