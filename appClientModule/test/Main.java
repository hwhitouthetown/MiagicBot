package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import application.Convertor;
import heroes.EpicHeroesLeague;
import partie.Board;

public class Main {

	static String  content; 
	
	public static void main(String[] args) {
		
		Gson gson = new GsonBuilder().create();

		Board b = new Board(); 
		
		initProperties("config/content"); 
		
		Convertor t = new Convertor();
		
		t.convert(content);
	
		
		
	
	}
	
	
	
	public static void initProperties(String file) {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(file);

			// load a properties file
			prop.load(input);

			content = prop.getProperty("content");
	
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

}
