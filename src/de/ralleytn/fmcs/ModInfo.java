package de.ralleytn.fmcs;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class ModInfo {

	private JSONObject json;
	
	/**
	 * 
	 * @param object
	 * @since 0.1.0
	 */
	public ModInfo(JSONObject object) {
		
		this.json = object;
	}
	
	/**
	 * 
	 * @since 0.1.0
	 */
	public ModInfo() {
		
		this.json = new JSONObject();
	}
	
	/**
	 * 
	 * @param dependencies
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setDependencies(List<Dependency> dependencies) {
		
		JSONArray array = new JSONArray();
		
		for(Dependency dependency : dependencies) {
			
			array.add(dependency.toJSON());
		}
		
		this.json.put("dependencies", array);
	}
	
	/**
	 * 
	 * @param name
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setName(String name) {
		
		this.json.put("name", name);
	}
	
	/**
	 * 
	 * @param version
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setVersion(String version) {
		
		this.json.put("version", version);
	}
	
	/**
	 * 
	 * @param targetGameVersion
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setTargetGameVersion(String targetGameVersion) {
		
		this.json.put("factorio_version", targetGameVersion);
	}
	
	/**
	 * 
	 * @param description
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setDescription(String description) {
		
		this.json.put("description", description);
	}
	
	/**
	 * 
	 * @param contact
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setContact(String contact) {
		
		this.json.put("contact", contact);
	}
	
	/**
	 * 
	 * @param homepage
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setHomepage(String homepage) {
		
		this.json.put("homepage", homepage);
	}
	
	/**
	 * 
	 * @param title
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setTitle(String title) {
		
		this.json.put("title", title);
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public List<Dependency> getDependencies() {
		
		List<Dependency> list = new ArrayList<>();
		
		for(Object element : (JSONArray)this.json.get("dependencies")) {
			
			list.add(new Dependency((JSONObject)element));
		}
		
		return list;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public JSONObject toJSON() {
		
		return this.json;
	}
}
