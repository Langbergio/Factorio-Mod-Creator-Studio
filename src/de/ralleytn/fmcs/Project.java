package de.ralleytn.fmcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.ralleytn.fmcs.ui.FMCSMutableProjectsTreeNode;
import de.ralleytn.fmcs.ui.FMCSProjectsTree;

/**
 * Represents a single project.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class Project {

	private File file;
	private FMCSMutableProjectsTreeNode projectNode;
	private JSONObject json;
	private DefaultCompletionProvider completionProvider;
	private Language language;
	private List<Library> libraries;
	
	/**
	 * Constructor used for new projects.
	 * @param name Name of the project
	 * @param projectLanguage
	 * @param projectFile
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public Project(String name, Language projectLanguage, File projectFile) {

		this.language = projectLanguage;
		
		this.json = new JSONObject();
		this.json.put("project_name", name);
		this.json.put("project_language", projectLanguage.getLangName());
		this.json.put("libraries", new JSONArray());
		
		this.file = projectFile;
		
		this.completionProvider = new DefaultCompletionProvider();
		this.completionProvider.setParameterizedCompletionParams('(', ", ", ')');
		this.completionProvider.setAutoActivationRules(Settings.getBoolean("editor.lua.code.completion.open-while-typing"), Settings.getBoolean("editor.lua.code.completion.open-on-dot") ? "." : null);
	
		this.libraries = new ArrayList<>();
	}
	
	/**
	 * Constructor used for loaded projects.
	 * @since 0.1.0
	 */
	public Project() {
		
		this.json = new JSONObject();
		this.completionProvider = new DefaultCompletionProvider();
		this.libraries = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public void addLibrary(Library library, boolean newLibrary) {
		
		this.libraries.add(library);
		FMCSMutableProjectsTreeNode librariesNode = this._getChild(this.projectNode, "Custom Libraries");
		FMCSMutableProjectsTreeNode libraryNode = new FMCSMutableProjectsTreeNode(library.getName(), true, "library");
		librariesNode.add(libraryNode);
		
		if(newLibrary) {
			
			String newFile = this.file.getParentFile().getAbsolutePath() + "/" + library.getZip().getName();
			Utils.copy(library.getZip().getAbsolutePath(), newFile);
			library = new Library(new File(newFile), this);
			((JSONArray)this.json.get("libraries")).add(library.getZip().getPath());
		}
		
		library.setNode(libraryNode);
		library.buildTree();
	}
	
	private FMCSMutableProjectsTreeNode _getChild(FMCSMutableProjectsTreeNode parent, String childName) {
		
		@SuppressWarnings("unchecked")
		Enumeration<FMCSMutableProjectsTreeNode> children = parent.children();
		
		while(children.hasMoreElements()) {
			
			FMCSMutableProjectsTreeNode child = children.nextElement();
			
			if(child.getUserObject().equals(childName)) {
				
				return child;
			}
		}
		
		return null;
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
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public Language getLanguage() {
		
		return this.language;
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
			
			for(Language lang : Language.values()) {
				
				if(lang.getLangName().equals(this.json.get("project_language"))) {
					
					this.language = lang;
				}
			}
			
		} catch(IOException | ParseException exception) {
			
			Utils.handleException(exception);
		}
		
		this.attachToTree();
		JSONArray array = (JSONArray)this.json.get("libraries");
		
		for(int index = 0; index < array.size(); index++) {

			this.addLibrary(new Library(new File((String)array.get(index)), this), false);
		}
	}
	
	/**
	 * Attaches the project to the projects tree.
	 * @since 0.1.0
	 */
	public void attachToTree() {
		
		FMCSProjectsTree projectsTree = Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getProjectsTree();
		FMCSMutableProjectsTreeNode rootNode = projectsTree.getRootNode();
		FMCSMutableProjectsTreeNode[] projectChildNodes = {
				
			new FMCSMutableProjectsTreeNode("Mod Info", false, "document"),
			new FMCSMutableProjectsTreeNode("Script", true, "code"),
			new FMCSMutableProjectsTreeNode("Custom Libraries", true, "folder"),
			new FMCSMutableProjectsTreeNode("Dependencies", true, "folder"),
			new FMCSMutableProjectsTreeNode("Graphics", true, "folder"),
			new FMCSMutableProjectsTreeNode("Items", true, "folder"),
			new FMCSMutableProjectsTreeNode("Recipes", true, "folder"),
			new FMCSMutableProjectsTreeNode("Entities", true, "folder"),
			new FMCSMutableProjectsTreeNode("Categories", true, "folder"),
			new FMCSMutableProjectsTreeNode("Fluids", true, "folder"),
			new FMCSMutableProjectsTreeNode("Technologies", true, "folder"),
			new FMCSMutableProjectsTreeNode("Equipment", true, "folder"),
			new FMCSMutableProjectsTreeNode("Signals", true, "folder"),
			new FMCSMutableProjectsTreeNode("Tiles", true, "folder")
		};
		
		projectChildNodes[1].add(new FMCSMutableProjectsTreeNode("data." + this.language.getFileEnding(), false, "code"));
		projectChildNodes[1].add(new FMCSMutableProjectsTreeNode("control." + this.language.getFileEnding(), false, "code"));

		this.projectNode = new FMCSMutableProjectsTreeNode("" + this.json.get("project_name"), true, "project");
		
		for(FMCSMutableProjectsTreeNode node : projectChildNodes) {
			
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
		
		FMCSProjectsTree projectsTree = Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getProjectsTree();
		projectsTree.removeSelectionPath(projectsTree.getPathForNode(this.projectNode));
		projectsTree.reload();
	}
	
	/**
	 * Saves the project
	 * @since 0.1.0
	 */
	public void save() {
		
		try {
			
			if(!this.file.getParentFile().exists() || !this.file.getParentFile().isDirectory()) {
				
				this.file.getParentFile().mkdirs();
			}
			
			if(!this.file.exists()) {
				
				this.file.createNewFile();
			}
			
			try(PrintWriter writer = new PrintWriter(this.file)) {
				
				writer.println(this.json.toJSONString());
				writer.flush();
			}
			
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
	public FMCSMutableProjectsTreeNode getTreeNode() {
		
		return this.projectNode;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
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
