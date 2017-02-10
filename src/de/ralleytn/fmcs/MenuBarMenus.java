package de.ralleytn.fmcs;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.managers.hotkey.HotkeyData;

import de.ralleytn.fmcs.dialogs.DialogAbout;

public class MenuBarMenus extends WebMenuBar {

	private static final long serialVersionUID = 2605082991259175490L;
	
	private WebMenu menuFile;
	private WebMenu menuEdit;
	private WebMenu menuSource;
	private WebMenu menuSearch;
	private WebMenu menuProject;
	private WebMenu menuHelp;
	private WebMenuItem itemNew;
	private WebMenuItem itemOpen;
	private WebMenuItem itemSave;
	private WebMenuItem itemSaveAs;
	private WebMenuItem itemSaveAll;
	private WebMenuItem itemMove;
	private WebMenuItem itemRename;
	private WebMenuItem itemRefresh;
	private WebMenuItem itemPrint;
	private WebMenuItem itemExport;
	private WebMenuItem itemSettings;
	private WebMenuItem itemExit;
	private WebMenuItem itemLuaDocs;
	private WebMenuItem itemFactorioAPIDocs;
	private WebMenuItem itemFMCSDocs;
	private WebMenuItem itemAbout;
	private Adapter adapter;

	public MenuBarMenus() {
		
		this.adapter = new Adapter(this);
		
		this.itemNew = new WebMenuItem("New Project");
		this.itemNew.setIcon(Icons.get("new"));
		this.itemNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.itemNew.addActionListener(this.adapter);
		
		this.itemOpen = new WebMenuItem("Open Project");
		this.itemOpen.setIcon(Icons.get("open"));
		this.itemOpen.addActionListener(this.adapter);
		
		this.itemSave = new WebMenuItem("Save");
		this.itemSave.setIcon(Icons.get("save"));
		this.itemSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.itemSave.addActionListener(this.adapter);
		this.itemSave.setEnabled(false);
		
		this.itemSaveAs = new WebMenuItem("Save As...");
		this.itemSaveAs.setIcon(Icons.get("save-as"));
		this.itemSaveAs.addActionListener(this.adapter);
		this.itemSaveAs.setEnabled(false);
		
		this.itemRename = new WebMenuItem("Rename");
		this.itemRename.setIcon(Icons.get("rename"));
		this.itemRename.addActionListener(this.adapter);
		this.itemRename.setEnabled(false);
		
		this.itemMove = new WebMenuItem("Move");
		this.itemMove.addActionListener(this.adapter);
		this.itemMove.setEnabled(false);
		
		this.itemRefresh = new WebMenuItem("Refresh");
		this.itemRefresh.setIcon(Icons.get("refresh"));
		this.itemRefresh.setAccelerator(new HotkeyData(KeyEvent.VK_F5));
		this.itemRefresh.addActionListener(this.adapter);
		this.itemRefresh.setEnabled(false);
		
		this.itemSaveAll = new WebMenuItem("Save All");
		this.itemSaveAll.setIcon(Icons.get("save-all"));
		this.itemSaveAll.addActionListener(this.adapter);
		this.itemSaveAll.setEnabled(false);
		
		this.itemPrint = new WebMenuItem("Print");
		this.itemPrint.setIcon(Icons.get("print"));
		this.itemPrint.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.itemPrint.addActionListener(this.adapter);
		this.itemPrint.setEnabled(false);
		
		this.itemExport = new WebMenuItem("Export");
		this.itemExport.setIcon(Icons.get("export"));
		this.itemExport.addActionListener(this.adapter);
		this.itemExport.setEnabled(false);
		
		this.itemSettings = new WebMenuItem("Settings");
		this.itemSettings.setIcon(Icons.get("settings"));
		this.itemSettings.setAccelerator(KeyStroke.getKeyStroke('\n', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.itemSettings.addActionListener(this.adapter);
		
		this.itemExit = new WebMenuItem("Exit");
		this.itemExit.addActionListener(this.adapter);
		
		this.menuFile = new WebMenu("File");
		this.menuFile.add(this.itemNew);
		this.menuFile.add(this.itemOpen);
		this.menuFile.addSeparator();
		this.menuFile.add(this.itemSave);
		this.menuFile.add(this.itemSaveAs);
		this.menuFile.add(this.itemSaveAll);
		this.menuFile.addSeparator();
		this.menuFile.add(this.itemMove);
		this.menuFile.add(this.itemRename);
		this.menuFile.add(this.itemRefresh);
		this.menuFile.addSeparator();
		this.menuFile.add(this.itemPrint);
		this.menuFile.addSeparator();
		this.menuFile.add(this.itemExport);
		this.menuFile.addSeparator();
		this.menuFile.add(this.itemSettings);
		this.menuFile.addSeparator();
		this.menuFile.add(this.itemExit);
		
		this.menuEdit = new WebMenu("Edit");
		this.menuEdit.setEnabled(false);
		
		this.menuSource = new WebMenu("Source");
		this.menuSource.setEnabled(false);
		
		this.menuSearch = new WebMenu("Search");
		this.menuSearch.setEnabled(false);
		
		this.menuProject = new WebMenu("Project");
		this.menuProject.setEnabled(false);
		
		// https://www.lua.org/manual/5.2/
		this.itemLuaDocs = new WebMenuItem("Lua Documentation");
		this.itemLuaDocs.setIcon(Icons.get("lua"));
		this.itemLuaDocs.addActionListener(this.adapter);
		
		// http://lua-api.factorio.com/latest/
		this.itemFactorioAPIDocs = new WebMenuItem("Factorio API Documentation");
		this.itemFactorioAPIDocs.setIcon(Icons.get("factorio"));
		this.itemFactorioAPIDocs.addActionListener(this.adapter);
		
		// https://github.com/RalleYTN/Factorio-Mod-Creator-Studio/wiki
		this.itemFMCSDocs = new WebMenuItem("Factorio Mod Creator Studio Wiki");
		this.itemFMCSDocs.addActionListener(this.adapter);
		
		this.itemAbout = new WebMenuItem("About Factorio Mod Creator Studio");
		this.itemAbout.setIcon(Icons.get("about"));
		this.itemAbout.addActionListener(this.adapter);
		
		this.menuHelp = new WebMenu("Help");
		this.menuHelp.add(this.itemLuaDocs);
		this.menuHelp.add(this.itemFactorioAPIDocs);
		this.menuHelp.add(this.itemFMCSDocs);
		this.menuHelp.addSeparator();
		this.menuHelp.add(this.itemAbout);
		
		this.add(this.menuFile);
		this.add(this.menuEdit);
		this.add(this.menuSource);
		this.add(this.menuSearch);
		this.add(this.menuProject);
		this.add(this.menuHelp);
	}
	
	private final class Adapter extends AbstractAdapter<MenuBarMenus> {

		public Adapter(MenuBarMenus motherClassInstance) {
			
			super(motherClassInstance);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			
			Object source = event.getSource();
			
			if(source == this.getMotherClassInstance().itemExit) {
				
				Program.FACTORIO_MOD_CREATOR_STUDIO.exit();
				
			} else if(source == this.getMotherClassInstance().itemNew) {
				
				
			
			} else if(source == this.getMotherClassInstance().itemLuaDocs) {

				Utils.openNativeBrowser("https://www.lua.org/manual/5.2/");
				
			} else if(source == this.getMotherClassInstance().itemFactorioAPIDocs) {

				Utils.openNativeBrowser("http://lua-api.factorio.com/latest/");

			} else if(source == this.getMotherClassInstance().itemFMCSDocs) {
				
				Utils.openNativeBrowser("https://github.com/RalleYTN/Factorio-Mod-Creator-Studio/wiki");
				
			} else if(source == this.getMotherClassInstance().itemAbout) {
				
				new DialogAbout(Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getFrame());
			}
		}
	}
}
