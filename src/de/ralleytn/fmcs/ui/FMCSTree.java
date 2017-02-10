package de.ralleytn.fmcs.ui;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import com.alee.laf.tree.WebTree;

import de.ralleytn.fmcs.Icons;

public class FMCSTree extends WebTree<FMCSMutableTreeNode> {

	private static final long serialVersionUID = -2096893885105817461L;
	
	private DefaultTreeModel model;

	public FMCSTree() {

		this.setModel((this.model = new DefaultTreeModel(new FMCSMutableTreeNode("Projects", true, "projects"))));
		this.setCellRenderer(new CellRenderer());
	}
	
	public void reload() {
		
		this.model.reload();
	}
	
	private final class CellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -3964305420274944365L;
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			
			Component comp = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			this.setIcon(Icons.get(((FMCSMutableTreeNode)value).getIconName()));
			
			return comp;
		}
	}
}
