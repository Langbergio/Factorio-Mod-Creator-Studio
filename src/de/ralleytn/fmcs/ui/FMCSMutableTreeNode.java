package de.ralleytn.fmcs.ui;

import javax.swing.tree.DefaultMutableTreeNode;

public class FMCSMutableTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 5755292419324646475L;
	
	private String iconName;
	
	public FMCSMutableTreeNode(String text) {
		
		super(text);
	}
	
	public FMCSMutableTreeNode(String text, boolean allowChildren) {
		
		super(text, allowChildren);
	}

	public FMCSMutableTreeNode(String text, boolean allowChildren, String iconName) {
		
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
