package de.ralleytn.fmcs.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.alee.laf.button.WebButton;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.toolbar.WebToolBar;

import de.ralleytn.fmcs.Icons;

public class FMCSToolBar extends WebMenuBar {

	// FIXED -- setting the layout to FlowLayout fixed it somehow.
	// When detaching a tool bar and then re-attaching it through the X-button produces an error.
	// harmless, but should be fixed in case that it bites us in the ass later.
	
	private static final long serialVersionUID = 7175738020472277924L;
	
	private ToolBarNewAndSave toolbarNewAndSave;
	private ToolBarImportExport toolbarImportExport;
	private ToolBarUndoRedo toolbarUndoRedo;

	public FMCSToolBar() {
		
		this.toolbarNewAndSave = new ToolBarNewAndSave();
		this.toolbarImportExport = new ToolBarImportExport();
		this.toolbarUndoRedo = new ToolBarUndoRedo();
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // fixed the detaching bug
		this.add(this.toolbarNewAndSave);
		this.add(this.toolbarImportExport);
		this.add(this.toolbarUndoRedo);
	}
	
	public class ToolBarNewAndSave extends WebToolBar {

		private static final long serialVersionUID = -6959396910696277873L;
		
		private WebButton buttonNew;
		private WebButton buttonSave;
		private WebButton buttonSaveAll;
		
		public ToolBarNewAndSave() {

			this.buttonNew = new WebButton(Icons.get("new"));
			this.buttonNew.getActionMap().put("openWizardSelection", new AbstractAction() {

				private static final long serialVersionUID = 4479705508870155399L;

				@Override
				public void actionPerformed(ActionEvent event) {
					
					
				}
			});
			this.buttonNew.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control N"), "openWizardSelection");
		
			this.buttonSave = new WebButton(Icons.get("save"));
			
			this.buttonSaveAll = new WebButton(Icons.get("save-all"));
			
			this.add(this.buttonNew);
			this.add(this.buttonSave);
			this.add(this.buttonSaveAll);
		}
	}
	
	public class ToolBarImportExport extends WebToolBar {

		private static final long serialVersionUID = 6560491870769911590L;
		
		private WebButton buttonImport;
		private WebButton buttonExport;
		
		public ToolBarImportExport() {
			
			this.buttonImport = new WebButton(Icons.get("import"));
			
			this.buttonExport = new WebButton(Icons.get("export"));
			
			this.add(this.buttonImport);
			this.add(this.buttonExport);
		}
	}
	
	public class ToolBarUndoRedo extends WebToolBar {

		private static final long serialVersionUID = -4162406422469577069L;
		
		private WebButton buttonUndo;
		private WebButton buttonRedo;
		
		public ToolBarUndoRedo() {
			
			this.buttonUndo = new WebButton(Icons.get("undo"));
			
			this.buttonRedo = new WebButton(Icons.get("redo"));
			
			this.add(this.buttonUndo);
			this.add(this.buttonRedo);
		}
	}
}
