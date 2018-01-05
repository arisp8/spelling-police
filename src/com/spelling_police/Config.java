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
	private boolean active;
	
	private static HashMap<String, Config> availableLanguages = new HashMap<String, Config>();
	private static ArrayList<String> activeLanguages = new ArrayList<String>();
	
	public static void bootstrap() {
		
		activeLanguages = findActiveLanguages();
		
		File folder = new File(System.getProperty("user.dir") + "\\resources\\config\\");
		for (final File fileEntry : folder.listFiles()) {
            
			if (fileEntry.getName().equals("active_languages.txt")) {
				continue;
			}
			
			String languageName = fileEntry.getName().replaceAll(".info", "");
            boolean active = activeLanguages.contains(languageName);
			Config config = new Config(languageName, active);
			availableLanguages.put(languageName, config);
	    }
	}
	
	private static ArrayList<String> findActiveLanguages() {
		ArrayList<String> languages = new ArrayList<String>();
		File languagesFile = new File(System.getProperty("user.dir") + "\\resources\\config\\active_languages.txt");
		
		try {
			Scanner scan = new Scanner(languagesFile);
			while (scan.hasNext()) {
				languages.add(scan.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return languages;
	}
	
	public Config(String language) {
		this(language, true);
	}
	
	public Config(String language, boolean active) {
		String configPath = System.getProperty("user.dir") + "\\resources\\config\\" + language + ".info";
		HashMap<String, String> fileConfig = loadConfig(configPath);
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
	
	public static ArrayList<String> getActiveLanguages() {
		return activeLanguages;
	}
	
}
