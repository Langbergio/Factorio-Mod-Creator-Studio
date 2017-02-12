package de.ralleytn.fmcs;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.alee.laf.WebLookAndFeel;

/**
 * Responsible for all the styling.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public final class StyleManager {

	private StyleManager() {} // prevents instantiation of this class
	
	private static boolean LOADED;
	
	/**
	 * Loads up the style of the program.
	 * @since 0.1.0
	 */
	public static final void load() {
		
		WebLookAndFeel.install();
		Enumeration<?> keys = UIManager.getDefaults().keys();
		
	    while(keys.hasMoreElements()) {
	    	
	    	Object key = keys.nextElement();
	    	Object value = UIManager.get(key);
	    	
	    	if(value != null && value instanceof FontUIResource) {
	    		
	    		UIManager.put(key, Fonts.get("default"));
	    	}
	    }

		StyleManager.LOADED = true;
	}
	
	/**
	 * @return {@code true} if {@link #load()} was called at least once, otherwise {@code false}
	 * @since 0.1.0
	 */
	public static final boolean isLoaded() {
		
		return StyleManager.LOADED;
	}
}
