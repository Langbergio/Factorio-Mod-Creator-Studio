package de.ralleytn.fmcs;

import java.awt.event.ActionEvent;

import com.alee.laf.button.WebButton;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.separator.WebSeparator;

import de.ralleytn.fmcs.wizards.WizardNewProject;

public class MenuBarButtons extends WebMenuBar {

	private static final long serialVersionUID = 3286370642576258861L;
	
	private WebButton buttonNew;
	private WebButton buttonOpen;
	private WebButton buttonSave;
	private WebButton buttonExport;
	private WebButton buttonUndo;
	private WebButton buttonRedo;
	private WebButton buttonPrint;
	private WebButton buttonCopy;
	private WebButton buttonCut;
	private WebButton buttonPaste;
	private WebButton buttonDelete;
	private WebButton buttonHelp;
	private Adapter adapter;
	
	public MenuBarButtons() {
		
		this.adapter = new Adapter(this);
		
		this.buttonNew = new WebButton(Icons.get("new"));
		this.buttonNew.addActionListener(this.adapter);
		
		this.buttonOpen = new WebButton(Icons.get("open"));
		this.buttonOpen.addActionListener(this.adapter);
		
		this.buttonSave = new WebButton(Icons.get("save"));
		this.buttonSave.addActionListener(this.adapter);
		this.buttonSave.setEnabled(false);
		
		this.buttonExport = new WebButton(Icons.get("export"));
		this.buttonExport.addActionListener(this.adapter);
		this.buttonExport.setEnabled(false);
		
		this.buttonUndo = new WebButton(Icons.get("undo"));
		this.buttonUndo.addActionListener(this.adapter);
		this.buttonUndo.setEnabled(false);
		
		this.buttonRedo = new WebButton(Icons.get("redo"));
		this.buttonRedo.addActionListener(this.adapter);
		this.buttonRedo.setEnabled(false);
		
		this.buttonPrint = new WebButton(Icons.get("print"));
		this.buttonPrint.addActionListener(this.adapter);
		this.buttonPrint.setEnabled(false);
		
		this.buttonCopy = new WebButton(Icons.get("copy"));
		this.buttonCopy.addActionListener(this.adapter);
		this.buttonCopy.setEnabled(false);
		
		this.buttonCut = new WebButton(Icons.get("cut"));
		this.buttonCut.addActionListener(this.adapter);
		this.buttonCut.setEnabled(false);
		
		this.buttonPaste = new WebButton(Icons.get("paste"));
		this.buttonPaste.addActionListener(this.adapter);
		this.buttonPaste.setEnabled(false);
		
		this.buttonDelete = new WebButton(Icons.get("delete"));
		this.buttonDelete.addActionListener(this.adapter);
		this.buttonDelete.setEnabled(false);
		
		this.buttonHelp = new WebButton(Icons.get("help"));
		this.buttonHelp.addActionListener(this.adapter);
		
		this.add(this.buttonNew);
		this.add(this.buttonOpen);
		this.add(this.buttonSave);
		this.add(this._createSeparator());
		this.add(this.buttonExport);
		this.add(this._createSeparator());
		this.add(this.buttonPrint);
		this.add(this._createSeparator());
		this.add(this.buttonUndo);
		this.add(this.buttonRedo);
		this.add(this._createSeparator());
		this.add(this.buttonCopy);
		this.add(this.buttonCut);
		this.add(this.buttonPaste);
		this.add(this.buttonDelete);
		this.add(this._createSeparator());
		this.add(this.buttonHelp);
	}
	
	public WebButton getButtonNew() {
		
		return this.buttonNew;
	}
	
	public WebButton getButtonOpen() {
		
		return this.buttonOpen;
	}
	
	public WebButton getButtonSave() {
		
		return this.buttonSave;
	}
	
	public WebButton getButtonExport() {
		
		return this.buttonExport;
	}
	
	public WebButton getButtonUndo() {
		
		return this.buttonUndo;
	}
	
	public WebButton getButtonRedo() {
		
		return this.buttonRedo;
	}
	
	public WebButton getButtonPrint() {
		
		return this.buttonPrint;
	}
	
	public WebButton getButtonCopy() {
		
		return this.buttonCopy;
	}
	
	public WebButton getButtonCut() {
		
		return this.buttonCut;
	}
	
	public WebButton getButtonPaste() {
		
		return this.buttonPaste;
	}
	
	public WebButton getButtonDelete() {
		
		return this.buttonDelete;
	}
	
	public WebButton getButtonHelp() {
		
		return this.buttonHelp;
	}
	
	private final WebSeparator _createSeparator() {
		
		WebSeparator separator = new WebSeparator(WebSeparator.VERTICAL);
		separator.setMargin(0, 10, 0, 10);
		return separator;
	}
	
	private final class Adapter extends AbstractAdapter<MenuBarButtons> {

		public Adapter(MenuBarButtons motherClassInstance) {
			
			super(motherClassInstance);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			
			Object source = event.getSource();
			
			if(source == this.getMotherClassInstance().buttonNew) {
				
				new WizardNewProject();
			
			} else if(source == this.getMotherClassInstance().buttonHelp) {

				Utils.openNativeBrowser("https://github.com/RalleYTN/Factorio-Mod-Creator-Studio/wiki");
			}
		}
	}
}
