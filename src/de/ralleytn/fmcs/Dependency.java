package de.ralleytn.fmcs;

import org.json.simple.JSONObject;

import com.alee.laf.label.WebLabel;

/**
 * Represents a dependency.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class Dependency {

	private JSONObject json;
	
	/**
	 * Constructor used when loading a project.
	 * @param json
	 * @since 0.1.0
	 */
	public Dependency(JSONObject json) {
		
		this.json = json;
	}
	
	/**
	 * Constructor used for new dependencies.
	 * @param mod The name of the needed modification
	 * @param required Whether this modification is required or not
	 * @param minVersion The minimum version required for this mod to work
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public Dependency(String mod, boolean required, String minVersion) {
		
		this.json = new JSONObject();
		this.json.put("mod", mod);
		this.json.put("required", required);
		this.json.put("min_version", minVersion);
	}
	
	/**
	 * @param mod The name of the needed modification
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setMod(String mod) {
		
		this.json.put("mod", mod);
	}
	
	/**
	 * @param required Whether this modification is required or not
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setRequired(boolean required) {
		
		this.json.put("required", required);
	}
	
	/**
	 * @param minVersion The minimum version required for this mod to work
	 * @since 0.1.0
	 */
	@SuppressWarnings("unchecked")
	public void setMinimumVersion(String minVersion) {
		
		this.json.put("min_version", minVersion);
	}
	
	/**
	 * @return The name of the needed modification
	 * @since 0.1.0
	 */
	public String getMod() {
		
		return (String)this.json.get("mod");
	}
	
	/**
	 * @return The minimum version required for this mod to work
	 * @since 0.1.0
	 */
	public String getMinimumVersion() {
		
		return (String)this.json.get("min_version");
	}
	
	/**
	 * @return Whether this modification is required or not
	 * @since 0.1.0
	 */
	public boolean isRequired() {
		
		return (boolean)this.json.get("required");
	}
	
	/**
	 * @return A new {@linkplain WebLabel} for {@linkplain JList}s.
	 * @since 0.1.0
	 */
	public WebLabel createLabel() {

		return new WebLabel(this.getMod() + " | Version: " + this.getMinimumVersion() + " (or higher)", this.isRequired() ? Icons.get("required") : Icons.get("empty"));
	}
	
	/**
	 * @return This instance as {@linkplain JSONObject}
	 * @since 0.1.0
	 */
	public JSONObject toJSON() {

		return this.json;
	}
}
