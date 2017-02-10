package de.ralleytn.fmcs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.fife.ui.autocomplete.Completion;

import com.alee.laf.label.WebLabel;

public class Library {

	private File zip;
	private String name;
	private List<Completion> completions;
	
	public Library(File zip) {
		
		this.zip = zip;
		this.name = zip.getName().substring(0, zip.getName().lastIndexOf('.'));
		this.completions = new ArrayList<>();
	}
	
	public WebLabel createLabel() {
		
		return new WebLabel(this.name + " (" + (this.zip.length() / 1000) + "KB)", Icons.get("library"));
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public File getZip() {
		
		return this.zip;
	}
	
	public List<Completion> getCompletions() {
		
		return this.completions;
	}
}
