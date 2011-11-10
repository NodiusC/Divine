package com.nodinchan.irc.divine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("serial")
public class ConfigManager extends Properties {
	
	public static Properties defaultProperty;
	public static Properties property;
	
	public static FileInputStream in;
	public static FileOutputStream out;	
	
	public static void defaultProperties() throws IOException {
		defaultProperty = new Properties();
		in = new FileInputStream("default-commands");
		defaultProperty.load(in);
		in.close();
	}
	
	public static void createProperties() {
		property = new Properties(defaultProperty);
	}
	
	public static Properties loadProperties() throws IOException {
		in = new FileInputStream("commands");
		property.load(in);
		in.close();
		return property;
	}
	
	public static void saveProperties() throws IOException {
		out = new FileOutputStream("commands");
		property.store(out, "-Commands Saved-");
		out.close();
	}
}
