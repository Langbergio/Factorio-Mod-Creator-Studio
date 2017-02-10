package de.ralleytn.fmcs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public final class Settings {

	private static final Properties PROPERTIES = new Properties();
	private static final File SETTINGS_FILE = new File("settings.properties");
	
	private static boolean LOADED;
	
	public static final void load() {
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Settings.SETTINGS_FILE), "UTF-8"))) {
			
			Settings.PROPERTIES.load(reader);
			Settings.LOADED = true;
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
	}
	
	public static final void save() {
		
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Settings.SETTINGS_FILE), "UTF-8"))) {
			
			Settings.PROPERTIES.store(writer, null);
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
	}
	
	public static final String getString(String setting) {
		
		return "" + Settings.PROPERTIES.get(setting);
	}
	
	public static final int getInt(String setting) {
		
		return Integer.parseInt(Settings.getString(setting));
	}
	
	public static final long getLong(String setting) {
		
		return Long.parseLong(Settings.getString(setting));
	}
	
	public static final float getFloat(String setting) {
		
		return Float.parseFloat(Settings.getString(setting));
	}
	
	public static final double getDouble(String setting) {
		
		return Double.parseDouble(Settings.getString(setting));
	}
	
	public static final boolean getBoolean(String setting) {
		
		return Boolean.parseBoolean(Settings.getString(setting));
	}
	
	public static final void set(String setting, Object value) {
		
		Settings.PROPERTIES.setProperty(setting, "" + value);
	}
	
	public static final boolean isLoaded() {
		
		return Settings.LOADED;
	}
}
