package de.ralleytn.fmcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.ralleytn.fmcs.ui.FMCSMutableTreeNode;
import de.ralleytn.fmcs.ui.FMCSTree;

/**
 * Represents a single project.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class Project {

	private File file;
	private FMCSMutableTreeNode projectNode;
	private JSONObject json;
	private DefaultCompletionProvider completionProvider;
	
	/**
	 * Constructor used for new projects.
	 * @param name Name of the project
	 * @param projectFile
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public Project(String name, File projectFile) {

		this.json = new JSONObject();
		this.json.put("project_name", name);
		
		this.file = projectFile;
		
		this.completionProvider = new DefaultCompletionProvider();
		this.completionProvider.setParameterizedCompletionParams('(', ", ", ')');
		this.completionProvider.setAutoActivationRules(Settings.getBoolean("editor.lua.code.completion.open-while-typing"), Settings.getBoolean("editor.lua.code.completion.open-on-dot") ? "." : null);
	}
	
	/**
	 * Constructor used for loaded projects.
	 * @since 0.1.0
	 */
	public Project() {
		
		this.json = new JSONObject();
		this.completionProvider = new DefaultCompletionProvider();
	}
	
	/**
	 * @param info Informations about the mod
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setInfo(ModInfo info) {
		
		this.json.put("info", info.toJSON());
	}
	
	/**
	 * Loads up a project file
	 * @param projectFile
	 * @since 0.1.0
	 */
	public void load(File projectFile) {
		
		this.file = projectFile;
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(projectFile), "UTF-8"))) {
			
			this.json = (JSONObject)new JSONParser().parse(reader);
			
		} catch(IOException | ParseException exception) {
			
			Utils.handleException(exception);
		}
	}
	
	/**
	 * Attaches the project to the projects tree.
	 * @since 0.1.0
	 */
	public void attachToTree() {
		
		FMCSTree projectsTree = Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getProjectsTree();
		FMCSMutableTreeNode rootNode = projectsTree.getRootNode();
		FMCSMutableTreeNode[] projectChildNodes = {
				
			new FMCSMutableTreeNode("Mod Info", false, "document"),
			new FMCSMutableTreeNode("Script", false, "code"),
			new FMCSMutableTreeNode("Custom Libraries", true, "folder"),
			new FMCSMutableTreeNode("Graphics", true, "folder"),
			new FMCSMutableTreeNode("Items", true, "folder"),
			new FMCSMutableTreeNode("Recipes", true, "folder"),
			new FMCSMutableTreeNode("Entities", true, "folder"),
			new FMCSMutableTreeNode("Categories", true, "folder"),
			new FMCSMutableTreeNode("Fluids", true, "folder"),
			new FMCSMutableTreeNode("Technologies", true, "folder"),
			new FMCSMutableTreeNode("Equipment", true, "folder"),
			new FMCSMutableTreeNode("Signals", true, "folder"),
			new FMCSMutableTreeNode("Tiles", true, "folder")
		};

		this.projectNode = new FMCSMutableTreeNode("" + this.json.get("project_name"), true, "project");
		
		for(FMCSMutableTreeNode node : projectChildNodes) {
			
			this.projectNode.add(node);
		}
		
		rootNode.add(this.projectNode);
		projectsTree.reload();
	}
	
	/**
	 * Detaches this project from the projects tree.
	 * @since 0.1.0
	 */
	public void detachFromTree() {
		
		FMCSTree projectsTree = Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getProjectsTree();
		projectsTree.removeSelectionPath(projectsTree.getPathForNode(this.projectNode));
		projectsTree.reload();
	}
	
	/**
	 * Saves the project
	 * @since 0.1.0
	 */
	public void save() {
		
		try(PrintWriter writer = new PrintWriter(this.file)) {
			
			writer.println(this.json.toJSONString());
			writer.flush();
			
		} catch(IOException exception) {
			
			Utils.handleException(exception);
		}
	}
	
	/**
	 * Deletes the project
	 * @since 0.1.0
	 */
	public void delete() {
		
		this.detachFromTree();
		Projects.removeProject(this);
		this.file.delete();
	}
	
	/**
	 * @return project name
	 * @since 0.1.0
	 */
	public String getName() {
		
		return (String)this.json.get("project_name");
	}
	
	/**
	 * @return project file
	 * @since 0.1.0
	 */
	public File getProjectFile() {
		
		return this.file;
	}
	
	/**
	 * @return The node of this project in the projects tree
	 * @since 0.1.0
	 */
	public FMCSMutableTreeNode getTreeNode() {
		
		return this.projectNode;
	}
	
	public DefaultCompletionProvider getCompletionProvider() {
		
		return this.completionProvider;
	}
	
	/**
	 * @return The informations about the mod.
	 * @since 0.1.0
	 */
	public ModInfo getInfo() {
		
		return new ModInfo((JSONObject)this.json.get("info"));
	}
}
