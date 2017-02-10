package de.ralleytn.fmcs.editor;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import de.ralleytn.fmcs.Fonts;
import de.ralleytn.fmcs.Program;
import de.ralleytn.fmcs.Project;
import de.ralleytn.fmcs.Settings;

public class EditorLua extends RTextScrollPane {

	private static final long serialVersionUID = -5710518358876649520L;
	
	private final Project project;

	public EditorLua(Project project) {

		super(EditorLua._createEditor());
		
		this.project = project;

		AutoCompletion autoCompletion = new AutoCompletion(this.project.getCompletionProvider());
		autoCompletion.setDescriptionWindowSize(Settings.getInt("editor.lua.code.completion.desc-window.width"), Settings.getInt("editor.lua.code.completion.desc-window.height"));
		autoCompletion.setShowDescWindow(Settings.getBoolean("editor.lua.code.completion.show-desc-window"));
		autoCompletion.setListCellRenderer(new CompletionCellRenderer());
		autoCompletion.setAutoActivationDelay(Settings.getInt("editor.lua.code.completion.auto-activation-delay"));
		autoCompletion.setAutoActivationEnabled(Settings.getBoolean("editor.lua.code.completion.auto-activation"));
		autoCompletion.setAutoCompleteEnabled(Settings.getBoolean("editor.lua.code.completion"));
		autoCompletion.setAutoCompleteSingleChoices(Settings.getBoolean("editor.lua.code.completion.single-choices"));
	    autoCompletion.install(this.getTextArea());
	}
	
	public Project getProject() {
		
		return this.project;
	}
	
	private static final RSyntaxTextArea _createEditor() {
		
		RSyntaxTextArea editor = new RSyntaxTextArea();
		editor.setSyntaxEditingStyle(Program.getLanguageByName("Lua 5.2").getIdentifier());
		editor.setCodeFoldingEnabled(Settings.getBoolean("editor.lua.code.folding"));
		editor.setAnimateBracketMatching(true);
		editor.setAntiAliasingEnabled(true);
		editor.setAutoIndentEnabled(true);
		editor.setBracketMatchingEnabled(true);
		editor.setClearWhitespaceLinesEnabled(true);
		editor.setEOLMarkersVisible(true);
		editor.setFadeCurrentLineHighlight(true);
		editor.setWrapStyleWord(false);
		editor.setLineWrap(false);
		editor.setFractionalFontMetricsEnabled(true);
		editor.setHighlightCurrentLine(true);
		editor.setHyperlinksEnabled(true);
		editor.setRoundedSelectionEdges(true);
		editor.setFont(Fonts.get("code"));

	    return editor;
	}
}
