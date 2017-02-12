package de.ralleytn.fmcs.editor;

import java.io.InputStream;

import de.ralleytn.fmcs.Utils;

public enum Theme {

	DEFAULT("Default", Theme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/default.xml")),
	DEFAULT_ALT("Default (alt)", Theme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/default-alt.xml")),
	ECLIPSE("Eclipse", Theme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/eclipse.xml")),
	IDEA("Idea", Theme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/idea.xml")),
	VS("Visual Studio", Theme.class.getClassLoader().getResourceAsStream("org/fife/ui/rsyntaxtextarea/themes/vs.xml")),
	DARK("Dark", Utils.getFileInputStream("res/themes/editor/dark.xml")),
	MONOKAI("Monokai", Utils.getFileInputStream("res/themes/editor/monokai.xml"));
	
	private String name;
	private org.fife.ui.rsyntaxtextarea.Theme theme;
	
	private Theme(String name, InputStream resource) {
		
		this.name= name;
		
		try {
			
			this.theme = org.fife.ui.rsyntaxtextarea.Theme.load(resource);
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
	}
	
	public String getName() {
		
		return name;
	}
	
	public org.fife.ui.rsyntaxtextarea.Theme getTheme() {
		
		return this.theme;
	}
}
