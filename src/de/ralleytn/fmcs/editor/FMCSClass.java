package de.ralleytn.fmcs.editor;

import org.fife.ui.autocomplete.BasicCompletion;
import org.json.simple.JSONObject;

import de.ralleytn.fmcs.Project;

public class FMCSClass extends AbstractDeclarableLanguageElement {

	public FMCSClass(JSONObject json, Project project) {
		
		super(json, project);
		
		this.completion = new BasicCompletion(project.getCompletionProvider(), (String)this.json.get("name"), (String)this.json.get("short_description"), (String)this.json.get("summary"));
	}
	
	public String getName() {
		
		return (String)this.json.get("name");
	}
}
