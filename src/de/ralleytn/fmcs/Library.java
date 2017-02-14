package de.ralleytn.fmcs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.fife.ui.autocomplete.Completion;

import com.alee.laf.label.WebLabel;

import de.ralleytn.fmcs.ui.FMCSMutableProjectsTreeNode;

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
	private FMCSMutableProjectsTreeNode node;
	private Project project;
	
	/**
	 * 
	 * @param zip
	 * @since 0.1.0
	 */
	public Library(File zip, Project project) {
		
		this.zip = zip;
		this.name = zip.getName().substring(0, zip.getName().lastIndexOf('.'));
		this.completions = new ArrayList<>();
		this.project = project;
	}
	
	public void setNode(FMCSMutableProjectsTreeNode node) {
		
		this.node = node;
	}
	
	public void buildTree() {
		
		try(ZipFile zipFile = new ZipFile(this.zip)) {
			
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
			Map<String, FMCSMutableProjectsTreeNode> map = new HashMap<>();
			
			while(enumeration.hasMoreElements()) {

				final ZipEntry entry = enumeration.nextElement();
				String[] parts = entry.getName().split("/");
				FMCSMutableProjectsTreeNode node = new FMCSMutableProjectsTreeNode(parts[parts.length - 1], entry.isDirectory(), entry.isDirectory() ? "folder" : (entry.getName().toUpperCase().endsWith(".LUA") || entry.getName().toUpperCase().endsWith(".JS") ? "code" : (entry.getName().toUpperCase().endsWith(".PNG") || entry.getName().toUpperCase().endsWith(".JPG") || entry.getName().toUpperCase().endsWith(".JPEG") || entry.getName().toUpperCase().endsWith(".BMP") || entry.getName().toUpperCase().endsWith(".GIF") ? "image" : "file")));
				
				if(entry.isDirectory()) {
					
					map.put(entry.getName(), node);
					
				} else {
					
					node.bindToZipEntry(this.zip, entry.getName());
					node.bindToProject(this.project);
				}
				
				if(parts.length > 1) {
					
					FMCSMutableProjectsTreeNode parent = map.get(entry.getName().substring(0, entry.getName().length() - parts[parts.length - 1].length()));
					
					if(parent != null) {
						
						parent.add(node);
						
					} else {
						
						this.node.add(node);
					}
					
				} else {
					
					this.node.add(node);
				}
			}
			
		} catch(IOException exception) {
			
			Utils.handleException(exception);
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public WebLabel createLabel() {
		
		return new WebLabel(this.name + " (" + (this.zip.length() / 1000) + "KB)", Icons.get("library"));
	}
	
	public FMCSMutableProjectsTreeNode getNode() {
		
		return this.node;
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
