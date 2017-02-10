package de.ralleytn.fmcs;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the fonts for the user interface.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public final class Fonts {

	private static Map<String, Font> FONTS;
	
	private Fonts() {} // prevents instantiation of this class
	
	/**
	 * Loads up all the fonts.
	 * @since 0.1.0
	 */
	public static final void load() {
		
		Fonts.FONTS = new HashMap<>();
		Fonts.FONTS.put("default", new Font(Font.SANS_SERIF, 0, 14));
		Fonts.FONTS.put("code", new Font(Font.MONOSPACED, 0, 18));
	}
	
	/**
	 * @param name Name of the font
	 * @return The requested instance of the {@linkplain Font} class
	 * @since 0.1.0
	 */
	public static final Font get(String name) {
		
		return Fonts.FONTS.get(name);
	}
	
	/**
	 * @return {@code true} if {@link #load()} was called at least once, otherwise {@code false}
	 * @since 0.1.0
	 */
	public static final boolean isLoaded() {
		
		return Fonts.FONTS != null;
	}
}
