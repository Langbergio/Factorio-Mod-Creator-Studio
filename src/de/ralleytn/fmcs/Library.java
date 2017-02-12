package de.ralleytn.fmcs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.fife.ui.autocomplete.Completion;

import com.alee.laf.label.WebLabel;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class Library {

	private File zip;
	private String name;
	private List<Completion> completions;
	
	/**
	 * 
	 * @param zip
	 * @since 0.1.0
	 */
	public Library(File zip) {
		
		this.zip = zip;
		this.name = zip.getName().substring(0, zip.getName().lastIndexOf('.'));
		this.completions = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public WebLabel createLabel() {
		
		return new WebLabel(this.name + " (" + (this.zip.length() / 1000) + "KB)", Icons.get("library"));
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public String getName() {
		
		return this.name;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public File getZip() {
		
		return this.zip;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public List<Completion> getCompletions() {
		
		return this.completions;
	}
}
