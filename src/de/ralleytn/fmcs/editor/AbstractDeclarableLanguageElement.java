package de.ralleytn.fmcs.editor;

import org.fife.ui.autocomplete.Completion;
import org.json.simple.JSONObject;

import de.ralleytn.fmcs.Project;

public abstract class AbstractDeclarableLanguageElement {

	protected final JSONObject json;
	protected final Project project;
	
	protected Completion completion;
	
	public AbstractDeclarableLanguageElement(JSONObject json, Project project) {
		
		this.json = json;
		this.project = project;
	}
	
	public JSONObject toJSON() {
		
		return this.json;
	}
	
	public Project getProject() {
		
		return this.project;
	}
	
	public Completion getCompletion() {
		
		return this.completion;
	}
}
