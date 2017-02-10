package de.ralleytn.fmcs;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.spinner.WebSpinner;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public final class Utils {

	private static OperatingSystem system;
	private static String osName;
	
	static {
		
		Utils.osName = System.getProperty("os.name");
		Utils.system = System.getProperty("java.vendor").toLowerCase().contains("android") ? OperatingSystem.ANDROID : (Utils.osName.toLowerCase().contains("win") ? OperatingSystem.WINDOWS : (Utils.osName.toLowerCase().contains("mac") ? OperatingSystem.MAC_OS_X : (Utils.osName.toLowerCase().contains("nix") || Utils.osName.toLowerCase().contains("nux") ? OperatingSystem.LINUX : (Utils.osName.toLowerCase().contains("sunos") ? OperatingSystem.SOLARIS : OperatingSystem.UNKNOWN))));
	}
	
	public static final int[] getVersion(String version) {
		
		String[] splitted = version.split("\\.");
		int[] array = new int[splitted.length];
		
		for(int index = 0; index < array.length; index++) {
			
			array[index] = Integer.parseInt(splitted[index]);
		}
		
		return array;
	}
	
	public static final String getVersion(WebSpinner[] spinners) {
		
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		
		for(WebSpinner spinner : spinners) {
			
			if(first) {
				
				first = false;
				
			} else {
				
				builder.append('.');
			}
			
			builder.append(spinner.getValue().toString());
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @param array
	 * @param element
	 * @return
	 * @since 0.1.0
	 */
	public static final <T>boolean contains(T[] array, T element) {
		
		for(T e : array) {
			
			if(e == element) {
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Replaces all the invalid characters for file names with a '-' letter.
	 * @param text
	 * @return the resulting text
	 * @since 0.1.0
	 */
	public static final String replaceInvalidCharacters(String text) {
		
		return text.replaceAll("[\\\\/:*?\"<>|]", "-");
	}
	
	/**
	 * @return The operating system of this machine
	 * @since 0.1.0
	 */
	public static final OperatingSystem getOperatingSystem() {
		
		return Utils.system;
	}
	
	/**
	 * Opens the native browser of the user.
	 * @param url URL which should be opened.
	 * @since 0.1.0
	 */
	public static final void openNativeBrowser(String url) {

		Process process = null;
		
		try {
			
			switch(Utils.system) {
			
				case LINUX:
					String[] browsers = {
							"epiphany",
							"firefox",
							"mozilla",
							"konqueror",
							"netscape",
							"opera",
							"links",
							"lynx",
							"vivaldi"
					};
					
					StringBuffer cmd = new StringBuffer();
					
					for(int index = 0; index < browsers.length; index++) {
						
						cmd.append((index == 0  ? "" : " || " ) + browsers[index] +" \"" + url + "\" ");
					}
					
					process = Runtime.getRuntime().exec(new String[] {"sh", "-c", cmd.toString()});
					break;
				
				case MAC_OS_X:
					process = Runtime.getRuntime().exec("open " + url);
					break;
				
				case WINDOWS:
					process = Runtime.getRuntime().exec("cmd /c start " + url);
					break;
				
				default:
					if(Desktop.isDesktopSupported()) {
					
						if(Desktop.getDesktop().isSupported(Action.BROWSE)) {
						
							Desktop.getDesktop().browse(new URL(url).toURI());
						}
					}
				
					break;
			}
			
		} catch(URISyntaxException | IOException exception) {
			
			Utils.handleException(exception);
		}
		
		if(process != null) {
			
			try {
				
				process.waitFor();
				
			} catch(InterruptedException exception) {

				Utils.handleException(exception);
			}
		}
	}
	
	/**
	 * Represents an operating system.
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	public static enum OperatingSystem {
		
		/**
		 * 
		 * @since 0.1.0
		 */
		WINDOWS,
		
		/**
		 * 
		 * @since 0.1.0
		 */
		LINUX,
		
		/**
		 * 
		 * @since 0.1.0
		 */
		MAC_OS_X,
		
		/**
		 * 
		 * @since 0.1.0
		 */
		SOLARIS,
		
		/**
		 * 
		 * @since 0.1.0
		 */
		ANDROID,
		
		/**
		 * 
		 * @since 0.1.0
		 */
		UNKNOWN;
	}
	
	/**
	 * Prints out the stack trace of an exception on the console and prompts an error dialog.
	 * @param exception Exception to handle
	 * @since 0.1.0
	 */
	public static final void handleException(Exception exception) {
		
		// Print on console
		exception.printStackTrace();
		
		// Prompt error dialog
		StringBuilder message = new StringBuilder();
		Throwable cause = exception;
		
		while(cause != null) {
			
			message.append(cause.getClass().getName() + ": " + (cause.getMessage() != null ? cause.getMessage() : "[no message]") + "\n");
			
			for(StackTraceElement stackTraceElement : cause.getStackTrace()) {
				
				message.append("\t" + stackTraceElement.toString() + "\n");
			}
			
			message.append('\n');
		}

		WebOptionPane.showConfirmDialog(Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getFrame(), message, "Error!", WebOptionPane.DEFAULT_OPTION, WebOptionPane.ERROR_MESSAGE);
	}
}
