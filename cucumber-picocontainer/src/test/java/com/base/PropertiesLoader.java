package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	
	public Properties loadProperties(String fileName){
		
		Properties applicationProperties = new Properties();
		
		//Get file from resources folder
		 ClassLoader classLoader = new PropertiesLoader().getClass().getClassLoader();
	       File file = new File(classLoader.getResource(fileName).getFile());
		InputStream input = null;
		try {

			input = new FileInputStream(file);

			// load a properties file
			applicationProperties.load(input);

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
		return applicationProperties;
	}

}
