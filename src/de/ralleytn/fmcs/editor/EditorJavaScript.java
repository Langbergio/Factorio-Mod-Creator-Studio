package de.ralleytn.fmcs.editor;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import de.ralleytn.fmcs.Fonts;
import de.ralleytn.fmcs.Language;
import de.ralleytn.fmcs.Program;
import de.ralleytn.fmcs.Project;
import de.ralleytn.fmcs.Settings;

public class EditorJavaScript extends RTextScrollPane {

	private static final long serialVersionUID = 4590994466595501456L;
	
	private final Project project;

	public EditorJavaScript(Project project) {

		super(EditorJavaScript._createEditor());
		
		this.project = project;
	    
	    this.updateTheme();
	    this.getTextArea().setFont(Fonts.get("code"));
	    this.setIconRowHeaderEnabled(true);
	    this.setLineNumbersEnabled(true);
	    this.getGutter().setBookmarkingEnabled(true);
	    this.getVerticalScrollBar().setUnitIncrement(20);
	    this.getHorizontalScrollBar().setUnitIncrement(20);
	}
	
	public final void updateTheme() {
		
		for(Theme theme : Theme.values()) {
			
			if(theme.toString().equals(Settings.getString("editor.js.theme"))) {
				
				theme.getTheme().apply((RSyntaxTextArea)this.getTextArea());
			}
		}
	}
	
	public Project getProject() {
		
		return this.project;
	}
	
	private static final RSyntaxTextArea _createEditor() {
		
		RSyntaxTextArea editor = new RSyntaxTextArea();
		editor.setSyntaxEditingStyle(Language.JAVASCRIPT.getIdentifier());
		editor.setCodeFoldingEnabled(Settings.getBoolean("editor.js.code.folding"));
		editor.setAnimateBracketMatching(true);
		editor.setAntiAliasingEnabled(true);
		editor.setAutoIndentEnabled(true);
		editor.setBracketMatchingEnabled(true);
		editor.setClearWhitespaceLinesEnabled(true);
		editor.setEOLMarkersVisible(false);
		editor.setFadeCurrentLineHighlight(true);
		editor.setWrapStyleWord(false);
		editor.setLineWrap(false);
		editor.setFractionalFontMetricsEnabled(true);
		editor.setHighlightCurrentLine(true);
		editor.setHyperlinksEnabled(true);
		editor.setRoundedSelectionEdges(true);
		editor.addParser(Program.SPELL_CHECKER);
		//editor.addParser(Language.JAVASCRIPT.getParser());

	    return editor;
	}
}
