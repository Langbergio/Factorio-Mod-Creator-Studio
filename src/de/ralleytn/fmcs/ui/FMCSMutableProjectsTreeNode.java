package de.ralleytn.fmcs.ui;

import javax.swing.tree.DefaultMutableTreeNode;

public class FMCSMutableProjectsTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 5755292419324646475L;
	
	private String iconName;
	
	public FMCSMutableProjectsTreeNode(String text) {
		
		super(text);
	}
	
	public FMCSMutableProjectsTreeNode(String text, boolean allowChildren) {
		
		super(text, allowChildren);
	}

	public FMCSMutableProjectsTreeNode(String text, boolean allowChildren, String iconName) {
		
		super(text, allowChildren);
		
		this.iconName = iconName;
	}
	
	public void setIconName(String iconName) {
		
		this.iconName = iconName;
	}
	
	public String getIconName() {
		
		return this.iconName;
	}
}
