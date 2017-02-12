package de.ralleytn.fmcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 * Manages the projects.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public final class Projects {

	/**
	 * 
	 * @since 0.1.0
	 */
	private static final List<Project> PROJECTS = new ArrayList<>();
	
	/**
	 * 
	 * @since 0.1.0
	 */
	private static final File DIRECTORY = new File("projects");
	
	/**
	 * 
	 * @since 0.1.0
	 */
	private static final File REGISTER = new File("projects/register.json");
	
	static {
		
		/*
		 * Checks the projects directory and its contents and creates them if they are missing.
		 */
		
		if(!Projects.DIRECTORY.exists() || !Projects.DIRECTORY.isDirectory()) {
			
			Projects.DIRECTORY.mkdirs();
		}
		
		if(!Projects.REGISTER.exists() || !Projects.REGISTER.isFile()) {
			
			try {
				
				Projects.REGISTER.createNewFile();
				
				try(PrintWriter writer = new PrintWriter(Projects.REGISTER)) {
					
					writer.print("[]");
					writer.flush();
				}
				
			} catch(Exception exception) {
				
				Utils.handleException(exception);
			}
		}
	}
	
	private Projects() {}  // prevents instantiation of this class
	
	/**
	 * Adds a project to the register.
	 * @param project Project to add
	 * @since 0.1.0
	 */
	public static final void addProject(Project project) {
		
		Projects.PROJECTS.add(project);
	}
	
	/**
	 * Removes the specified project from the register.
	 * @param project Project to remove
	 * @since 0.1.0
	 */
	public static final void removeProject(Project project) {
		
		Projects.PROJECTS.remove(project);
	}
	
	/**
	 * @param name Name of the project
	 * @return The requested project
	 * @since 0.1.0
	 */
	public static final Project getProjectByName(String name) {
		
		for(int index = 0; index < Projects.PROJECTS.size(); index++) {
			
			Project project = Projects.PROJECTS.get(index);
			
			if(project.getName().equals(name)) {
				
				return project;
			}
		}
		
		return null;
	}
	
	/**
	 * @return The first registered project in the list
	 * @since 0.1.0
	 */
	public static final Project getFirstProject() {
		
		return Projects.PROJECTS.get(0);
	}
	
	/**
	 * Loads up the project register.
	 * @since 0.1.0
	 */
	public static final void loadRegister() {
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Projects.REGISTER), "UTF-8"))) {
			
			JSONArray array = (JSONArray)new JSONParser().parse(reader);
			
			for(Object element : array) {

				File projectFile = new File((String)element);
				
				if(projectFile.exists() && projectFile.isFile()) {
					
					Project project = new Project();
					project.load(projectFile);
					project.attachToTree();
					
					Projects.addProject(project);
				}
			}
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
		
		Projects.saveRegister();
	}
	
	/**
	 * @return Number of registered projects
	 * @since 0.1.0
	 */
	public static final int getNumberOfProjects() {
		
		return Projects.PROJECTS.size();
	}
	
	/**
	 * Saves the project register.
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public static final void saveRegister() {
		
		JSONArray array = new JSONArray();
		
		for(Project project : Projects.PROJECTS) {

			array.add(project.getProjectFile().getAbsolutePath());
		}
		
		try(PrintWriter writer = new PrintWriter(Projects.REGISTER)) {
			
			writer.print(array.toJSONString());
			writer.flush();
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
	}
}
