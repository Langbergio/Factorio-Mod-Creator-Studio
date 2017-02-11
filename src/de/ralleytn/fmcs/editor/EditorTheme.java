package de.ralleytn.fmcs.editor;

import java.io.InputStream;

import org.fife.ui.rsyntaxtextarea.Theme;

import de.ralleytn.fmcs.Utils;

public enum EditorTheme {

	DEFAULT("Default", EditorTheme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/default.xml")),
	DEFAULT_ALT("Default (alt)", EditorTheme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/default-alt.xml")),
	ECLIPSE("Eclipse", EditorTheme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/eclipse.xml")),
	IDEA("Idea", EditorTheme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/idea.xml")),
	VS("Visual Studio", EditorTheme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/vs.xml")),
	DARK("Dark", Utils.getFileInputStream("res/themes/editor/dark.xml")),
	MONOKAI("Monokai", Utils.getFileInputStream("res/themes/editor/monokai.xml"));
	
	private String name;
	private Theme theme;
	
	private EditorTheme(String name, InputStream resource) {
		
		this.name= name;
		
		try {
			
			this.theme = Theme.load(resource);
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
	}
	
	public String getName() {
		
		return name;
	}
	
	public Theme getTheme() {
		
		return this.theme;
	}
}
