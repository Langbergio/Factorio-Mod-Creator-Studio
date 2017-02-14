package de.ralleytn.fmcs.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;

import de.ralleytn.fmcs.AbstractAdapter;
import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.dialog.DialogWizardSelection;
import de.ralleytn.fmcs.wizard.WizardNewProject;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class FMCSToolBar extends WebMenuBar {

	// FIXED -- setting the layout to FlowLayout fixed it somehow.
	// When detaching a tool bar and then re-attaching it through the X-button produces an error.
	// harmless, but should be fixed in case that it bites us in the ass later.
	
	private static final long serialVersionUID = 7175738020472277924L;
	
	private ToolBarNewAndSave toolbarNewAndSave;
	private ToolBarImportExport toolbarImportExport;
	private ToolBarUndoRedo toolbarUndoRedo;
	private ToolBarEdit toolbarEdit;
	private ToolBarQuickSearch toolbarQucikSearch;

	public FMCSToolBar() {
		
		this.toolbarNewAndSave = new ToolBarNewAndSave();
		this.toolbarImportExport = new ToolBarImportExport();
		this.toolbarUndoRedo = new ToolBarUndoRedo();
		this.toolbarEdit = new ToolBarEdit();
		this.toolbarQucikSearch = new ToolBarQuickSearch();
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // fixed the detaching bug
		this.add(this.toolbarNewAndSave);
		this.add(this.toolbarImportExport);
		this.add(this.toolbarUndoRedo);
		this.add(this.toolbarEdit);
		this.add(this.toolbarQucikSearch);
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	public class ToolBarNewAndSave extends WebToolBar {

		private static final long serialVersionUID = -6959396910696277873L;
		
		private WebButton buttonNew;
		private WebButton buttonSave;
		private WebButton buttonSaveAll;
		private Adapter adapter;
		
		public ToolBarNewAndSave() {

			this.adapter = new Adapter(this);
			
			this.buttonNew = new WebButton(Icons.get("new"));
			this.buttonNew.getActionMap().put("openWizardSelection", new AbstractAction() {

				private static final long serialVersionUID = 4479705508870155399L;

				@Override
				public void actionPerformed(ActionEvent event) {
					
					new DialogWizardSelection(new WizardNewProject());
				}
			});
			this.buttonNew.addActionListener(this.adapter);
			this.buttonNew.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control N"), "openWizardSelection");
		
			this.buttonSave = new WebButton(Icons.get("save"));
			
			this.buttonSaveAll = new WebButton(Icons.get("save-all"));
			
			this.add(this.buttonNew);
			this.add(this.buttonSave);
			this.add(this.buttonSaveAll);
			this.setToolbarStyle(ToolbarStyle.attached);
		}
		
		/**
		 * 
		 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
		 * @version 0.1.0
		 * @since 0.1.0
		 */
		private class Adapter extends AbstractAdapter<ToolBarNewAndSave> {

			public Adapter(ToolBarNewAndSave motherClassInstance) {
				
				super(motherClassInstance);
			}
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				Object source = event.getSource();
				
				if(source == this.getMotherClassInstance().buttonNew) {
					
					new DialogWizardSelection(new WizardNewProject());
				}
			}
		}
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	public class ToolBarImportExport extends WebToolBar {

		private static final long serialVersionUID = 6560491870769911590L;
		
		private WebButton buttonImport;
		private WebButton buttonExport;
		
		public ToolBarImportExport() {
			
			this.buttonImport = new WebButton(Icons.get("import"));
			
			this.buttonExport = new WebButton(Icons.get("export"));
			
			this.add(this.buttonImport);
			this.add(this.buttonExport);
			this.setToolbarStyle(ToolbarStyle.attached);
		}
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	public class ToolBarUndoRedo extends WebToolBar {

		private static final long serialVersionUID = -4162406422469577069L;
		
		private WebButton buttonUndo;
		private WebButton buttonRedo;
		
		public ToolBarUndoRedo() {
			
			this.buttonUndo = new WebButton(Icons.get("undo"));
			
			this.buttonRedo = new WebButton(Icons.get("redo"));
			
			this.add(this.buttonUndo);
			this.add(this.buttonRedo);
			this.setToolbarStyle(ToolbarStyle.attached);
		}
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	public class ToolBarEdit extends WebToolBar {

		private static final long serialVersionUID = 3704616688789406228L;
		
		private WebButton buttonCopy;
		private WebButton buttonPaste;
		private WebButton buttonCut;
		private WebButton buttonDelete;
		
		public ToolBarEdit() {
			
			this.buttonCopy = new WebButton(Icons.get("copy"));
			
			this.buttonPaste = new WebButton(Icons.get("paste"));
			
			this.buttonCut = new WebButton(Icons.get("cut"));
			
			this.buttonDelete = new WebButton(Icons.get("delete"));
			
			this.add(this.buttonCopy);
			this.add(this.buttonPaste);
			this.add(this.buttonCut);
			this.add(this.buttonDelete);
			this.setToolbarStyle(ToolbarStyle.attached);
		}
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	public class ToolBarQuickSearch extends WebToolBar {

		private static final long serialVersionUID = -6529794681284872056L;
		
		private WebButton buttonSearch;
		private WebTextField fieldSearchText;
		
		public ToolBarQuickSearch() {
			
			this.buttonSearch = new WebButton(Icons.get("search"));
			
			this.fieldSearchText = new WebTextField();
			this.fieldSearchText.setPreferredWidth(230);
			
			this.add(new WebLabel("Quick Search: "));
			this.add(this.fieldSearchText);
			this.add(this.buttonSearch);
			this.setToolbarStyle(ToolbarStyle.attached);
			this.setSpacing(0);
		}
	}
}
