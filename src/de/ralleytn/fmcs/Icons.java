package de.ralleytn.fmcs;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Manages the icons for the user interface.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public final class Icons {
	
	private static final File ICONS_DIRECTORY = new File("res/icons");
	private static Map<String, ImageIcon> ICONS;
	
	private Icons() {} // prevents instantiation of this class
	
	/**
	 * Loads up all the GIFs and PNGs from the "res/icons" folder.
	 * @since 0.1.0
	 */
	public static final void load() {
		
		Icons.ICONS = new HashMap<>();
		
		for(File imgFile : Icons.ICONS_DIRECTORY.listFiles((dir, name) -> name.toUpperCase().endsWith(".PNG") || name.toUpperCase().endsWith(".GIF"))) {
			
			if(imgFile.isFile()) {
				
				try {
					
					Icons.ICONS.put(imgFile.getName().substring(0, imgFile.getName().lastIndexOf('.')), new ImageIcon(imgFile.toURI().toURL()));
					
				} catch(Exception exception) {
					
					Utils.handleException(exception);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param target
	 * @param x
	 * @param y
	 * @return
	 * @since 0.1.0
	 */
	public static final ImageIcon merge(String source, String target, int x, int y) {
		
		Image sourceImage = Icons.get(source).getImage();
		Image targetImage = Icons.get(target).getImage();
		BufferedImage newImage = new BufferedImage(targetImage.getWidth(null), targetImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = newImage.createGraphics();
		graphics.drawImage(targetImage, 0, 0, null);
		graphics.drawImage(sourceImage, x, y, null);
		graphics.dispose();
		
		return new ImageIcon(newImage);
	}
	
	/**
	 * @param name Name of the icon (filename without the format ending)
	 * @return An instance of the {@linkplain ImageIcon} class filled with the wished image
	 * @since 0.1.0
	 */
	public static final ImageIcon get(String name) {
		
		return Icons.ICONS.get(name);
	}
	
	/**
	 * @return {@code true} if {@link #load()} was called at least once, otherwise {@code false}
	 * @since 0.1.0
	 */
	public static final boolean isLoaded() {
		
		return Icons.ICONS != null;
	}
}
