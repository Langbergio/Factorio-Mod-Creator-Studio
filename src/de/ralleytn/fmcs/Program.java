package de.ralleytn.fmcs;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.ralleytn.fmcs.editor.JavaScriptFMCSTokenMaker;
import de.ralleytn.fmcs.editor.Language;
import de.ralleytn.fmcs.editor.Lua52TokenMaker;

/**
 * Starting point of the program.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public final class Program {

	/**
	 * Most recent version of Factorio.
	 * @since 0.1.0
	 */
	public static final String CURRENT_FACTORIO_VERSION = Program._fetchFactorioVersion();
	
	/**
	 * Global instance of the Factorio Mod Creator Studio.
	 * @since 0.1.0
	 */
	public static final Program FACTORIO_MOD_CREATOR_STUDIO = new Program();
	
	public static final List<Library> DEFAULT_LIBRARIES = Program._loadDefaultLibraries();
	
	public static final Language[] AVAILABLE_LANGUAGES = {
			
		new Language("Lua 5.2", "text/lua-5.2", Lua52TokenMaker.class),
		new Language("JavaScript(FMCS Edition)", "text/js-fmcs", JavaScriptFMCSTokenMaker.class)
	};
	
	public static final List<String> ALL_MODDABLE_FACTORIO_VERSIONS = Program._fetchModdableFactorioVersions();
	
	private GUI gui;
	
	private Program() {}
	
	public static void main(String[] args) {

		Fonts.load();
		Style.load();
		Icons.load();
		Settings.load();
		Program.FACTORIO_MOD_CREATOR_STUDIO.gui = new GUI();
		Projects.loadRegister();
		Program.FACTORIO_MOD_CREATOR_STUDIO.gui.create();
	}
	
	private static final List<Library> _loadDefaultLibraries() {
		
		File directory = new File("default-libs");
		List<Library> libraries = new ArrayList<>();
		
		if(directory.exists() && directory.isDirectory()) {
			
			for(File zip : directory.listFiles((dir, name) -> name.toUpperCase().endsWith(".ZIP"))) {
				
				libraries.add(new Library(zip));
			}
		}
		
		return libraries;
	}
	
	private static final List<String> _fetchModdableFactorioVersions() {
		
		List<String> versions = new ArrayList<>();
		
		try {
			
			Document document = Jsoup.connect("http://lua-api.factorio.com/").get();
			Elements elements = document.select("a");
			
			for(int index = 0; index < elements.size(); index++) {
				
				String text = elements.get(index).text();
				
				if(!text.equals("Latest version")) {
					
					String version = text.substring(0, text.lastIndexOf('.'));
					
					if(!versions.contains(version)) {
						
						versions.add(version);
					}
				}
			}
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
		
		versions.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				
				float num1 = Float.parseFloat(o1);
				float num2 = Float.parseFloat(o2);
				
				return num1 == num2 ? 0 : (num1 > num2 ? 1 : -1);
			}
		});
		
		return versions;
	}
	
	/**
	 * @return Most recent version of Factorio
	 * @since 0.1.0
	 */
	private static final String _fetchFactorioVersion() {
		
		try {
			
			Document document = Jsoup.connect("http://lua-api.factorio.com/latest/").get();
			Elements elements = document.select(".version");
			Element element = elements.get(0);
			String version = element.text().replace("Factorio ", "");
			version = version.substring(0, version.lastIndexOf("."));
			return version;
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
		
		return null;
	}
	
	/**
	 * Exists the program.
	 * @since 0.1.0
	 */
	public void exit() {
		
		Settings.set("window.width", Program.FACTORIO_MOD_CREATOR_STUDIO.gui.getFrame().getWidth());
		Settings.set("window.height", Program.FACTORIO_MOD_CREATOR_STUDIO.gui.getFrame().getHeight());
		Settings.set("window.state", Program.FACTORIO_MOD_CREATOR_STUDIO.gui.getFrame().getExtendedState());
		Settings.save();
		System.exit(0);
	}
	
	/**
	 * @return Instance of the GUI
	 * @since 0.1.0
	 */
	public GUI getGUI() {
		
		return this.gui;
	}
	
	public static final Language getLanguageByName(String name) {
		
		for(Language lang : Program.AVAILABLE_LANGUAGES) {
			
			if(lang.getName().equals(name)) {
				
				return lang;
			}
		}
		
		return null;
	}
}
