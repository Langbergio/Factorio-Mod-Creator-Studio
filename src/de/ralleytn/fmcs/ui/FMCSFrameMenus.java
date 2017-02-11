package de.ralleytn.fmcs.ui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.managers.hotkey.HotkeyData;

import de.ralleytn.fmcs.Icons;

public final class FMCSFrameMenus extends WebMenuBar {

	private static final long serialVersionUID = -2879916824679086885L;
	
	private MenuFile menuFile;
	private MenuEdit menuEdit;
	private MenuSource menuSource;
	private MenuHelp menuHelp;
	
	public FMCSFrameMenus() {
		
		this.menuFile = new MenuFile();
		this.menuEdit = new MenuEdit();
		this.menuSource = new MenuSource();
		this.menuHelp = new MenuHelp();
		
		this.add(this.menuFile);
		this.add(this.menuEdit);
		this.add(this.menuSource);
		this.add(this.menuHelp);
	}

	public class MenuFile extends WebMenu {

		private static final long serialVersionUID = -3822607403946820200L;
		
		private MenuNew menuNew;
		private WebMenuItem itemOpen;
		private WebMenuItem itemSave;
		private WebMenuItem itemSaveAll;
		private WebMenuItem itemImport;
		private WebMenuItem itemExport;
		private WebMenuItem itemMove;
		private WebMenuItem itemRename;
		private WebMenuItem itemRefresh;
		private WebMenuItem itemPrint;
		private WebMenuItem itemSettings;
		private WebMenuItem itemExit;

		public MenuFile() {
			
			super("File");
			
			this.menuNew = new MenuNew();
			
			this.itemOpen = new WebMenuItem("Open", Icons.get("open"));
			
			this.itemSave = new WebMenuItem("Save", Icons.get("save"));
			this.itemSave.setEnabled(false);
			this.itemSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			this.itemSaveAll = new WebMenuItem("Save All", Icons.get("save-all"));
			this.itemSaveAll.setEnabled(false);
			this.itemSaveAll.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
			
			this.itemImport = new WebMenuItem("Import", Icons.get("import"));
			
			this.itemExport = new WebMenuItem("Export", Icons.get("export"));
			this.itemExport.setEnabled(false);
			
			this.itemMove = new WebMenuItem("Move");
			this.itemMove.setEnabled(false);
			
			this.itemRename = new WebMenuItem("Rename", Icons.get("rename"));
			this.itemRename.setEnabled(false);
			this.itemRename.setAccelerator(new HotkeyData(KeyEvent.VK_F2));
			
			this.itemRefresh = new WebMenuItem("Refresh", Icons.get("refresh"));
			this.itemRefresh.setEnabled(false);
			this.itemRefresh.setAccelerator(new HotkeyData(KeyEvent.VK_F5));
			
			this.itemPrint = new WebMenuItem("Print", Icons.get("print"));
			this.itemPrint.setEnabled(false);
			this.itemPrint.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			this.itemSettings = new WebMenuItem("Settings", Icons.get("settings"));
			
			this.itemExit = new WebMenuItem("Exit");
			
			this.add(this.menuNew);
			this.add(this.itemOpen);
			this.addSeparator();
			this.add(this.itemSave);
			this.add(this.itemSaveAll);
			this.addSeparator();
			this.add(this.itemImport);
			this.add(this.itemExport);
			this.addSeparator();
			this.add(this.itemMove);
			this.add(this.itemRename);
			this.add(this.itemRefresh);
			this.addSeparator();
			this.add(this.itemPrint);
			this.addSeparator();
			this.add(this.itemSettings);
			this.addSeparator();
			this.add(this.itemExit);
		}
		
		public class MenuNew extends WebMenu {

			private static final long serialVersionUID = -3722717715198536251L;
			
			private WebMenuItem itemProject;
			private WebMenuItem itemLibrary;
			private WebMenuItem itemGraphic;
			private WebMenuItem itemItem;
			private WebMenuItem itemRecipe;
			private WebMenuItem itemEntity;
			private WebMenuItem itemTechnology;
			private WebMenuItem itemFluid;
			private WebMenuItem itemEquipment;
			private WebMenuItem itemSignal;
			private WebMenuItem itemTile;
			
			public MenuNew() {
				
				super("New", Icons.get("new"));
				
				this.itemProject = new WebMenuItem("Project", Icons.merge("sub-plus", "project", 14, 1));
				
				this.itemLibrary = new WebMenuItem("Custom Library", Icons.merge("sub-plus", "library", 14, 1));
				this.itemLibrary.setEnabled(false);
				
				this.itemGraphic = new WebMenuItem("Graphic", Icons.merge("sub-plus", "image", 14, 1));
				this.itemGraphic.setEnabled(false);
				
				this.itemItem = new WebMenuItem("Item", Icons.merge("sub-plus", "item", 14, 1));
				this.itemItem.setEnabled(false);
				
				this.itemRecipe = new WebMenuItem("Recipe", Icons.merge("sub-plus", "recipe", 14, 1));
				this.itemRecipe.setEnabled(false);
				
				this.itemEntity = new WebMenuItem("Entity", Icons.merge("sub-plus", "entity", 14, 1));
				this.itemEntity.setEnabled(false);
				
				this.itemTechnology = new WebMenuItem("Technology", Icons.merge("sub-plus", "technology", 14, 1));
				this.itemTechnology.setEnabled(false);
				
				this.itemFluid = new WebMenuItem("Fluid", Icons.merge("sub-plus", "fluid", 14, 1));
				this.itemFluid.setEnabled(false);
				
				this.itemEquipment = new WebMenuItem("Equipment", Icons.merge("sub-plus", "equipment", 14, 1));
				this.itemEquipment.setEnabled(false);
				
				this.itemSignal = new WebMenuItem("Signal", Icons.merge("sub-plus", "signal", 14, 1));
				this.itemSignal.setEnabled(false);
				
				this.itemTile = new WebMenuItem("Tile");
				this.itemTile.setEnabled(false);
				
				this.add(this.itemProject);
				this.add(this.itemLibrary);
				this.add(this.itemGraphic);
				this.add(this.itemItem);
				this.add(this.itemRecipe);
				this.add(this.itemEntity);
				this.add(this.itemTechnology);
				this.add(this.itemFluid);
				this.add(this.itemEquipment);
				this.add(this.itemSignal);
				this.add(this.itemTile);
			}
		}
	}
	
	public class MenuEdit extends WebMenu {

		private static final long serialVersionUID = 702426690150239706L;

		public MenuEdit() {
			
			super("Edit");
		}
	}
	
	public class MenuSource extends WebMenu {

		private static final long serialVersionUID = -8820835528550578621L;
		
		private WebMenuItem itemToggleComment;
		private WebMenuItem itemAddBlockComment;
		private WebMenuItem itemRemoveBlockComment;
		private WebMenuItem itemShiftRight;
		private WebMenuItem itemShiftLeft;
		private WebMenuItem itemFormat;

		public MenuSource() {
			
			super("Source");
			
			this.itemToggleComment = new WebMenuItem("Toggle Comment");
			this.itemToggleComment.setAccelerator(KeyStroke.getKeyStroke("control 7"));
			
			this.itemAddBlockComment = new WebMenuItem("Add Block Comment");
			this.itemAddBlockComment.setAccelerator(KeyStroke.getKeyStroke('/', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			this.itemRemoveBlockComment = new WebMenuItem("Remove Block Comment");
			this.itemRemoveBlockComment.setAccelerator(KeyStroke.getKeyStroke('\\', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			this.itemShiftRight = new WebMenuItem("Shift Right");
			
			this.itemShiftLeft = new WebMenuItem("Shift Left");
			
			this.itemFormat = new WebMenuItem("Format");
			this.itemFormat.setAccelerator(KeyStroke.getKeyStroke("control shift F"));
			
			this.add(this.itemToggleComment);
			this.add(this.itemAddBlockComment);
			this.add(this.itemRemoveBlockComment);
			this.addSeparator();
			this.add(this.itemShiftRight);
			this.add(this.itemShiftLeft);
			this.add(this.itemFormat);
		}
	}
	
	public class MenuHelp extends WebMenu {

		private static final long serialVersionUID = -3866771881161961005L;
		
		private WebMenuItem itemLuaDocumentation;
		private WebMenuItem itemFactorioApiDocumentation;
		private WebMenuItem itemFMCSWiki;
		private WebMenuItem itemAbout;

		public MenuHelp() {
			
			super("Help");
			
			this.itemLuaDocumentation = new WebMenuItem("Lua 5.2 Documentation", Icons.get("lua"));
			
			this.itemFactorioApiDocumentation = new WebMenuItem("Factorio API Documentation", Icons.get("factorio"));

			this.itemFMCSWiki = new WebMenuItem("Factorio Mod Creator Studio Wiki", Icons.get("fmcs"));
			
			this.itemAbout = new WebMenuItem("About", Icons.get("about"));
			
			this.add(this.itemLuaDocumentation);
			this.add(this.itemFactorioApiDocumentation);
			this.add(this.itemFMCSWiki);
			this.addSeparator();
			this.add(this.itemAbout);
		}
	}
}
