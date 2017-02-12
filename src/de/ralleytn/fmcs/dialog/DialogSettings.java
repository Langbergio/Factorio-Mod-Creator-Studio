package de.ralleytn.fmcs.dialog;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;

import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Program;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class DialogSettings extends WebDialog {

	private static final long serialVersionUID = -5629705894151421316L;
	
	/**
	 * 
	 * @since 0.1.0
	 */
	public DialogSettings() {
		
		super(Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getFrame());

		this.setSize(800, 600);
		this.setLocationRelativeTo(this.getOwner());
		this.setResizable(false);
		this.setIconImage(Icons.get("settings").getImage());
		this.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setContentPane(new TabbedPane());
		this.setTitle("Settings");
		this.setVisible(true);
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	private final class TabbedPane extends WebTabbedPane {

		private static final long serialVersionUID = 5684484180248735094L;

		/**
		 * 
		 * @since 0.1.0
		 */
		public TabbedPane() {
			
			this.addTab("General", new WebScrollPane(new SettingsGeneral()));
			this.addTab("Lua Editor", new WebScrollPane(new SettingsLuaEditor()));
			this.addTab("JavaScript Editor", new WebScrollPane(new SettingsJavaScriptEditor()));
			
			this.setTabLayoutPolicy(WebTabbedPane.WRAP_TAB_LAYOUT);
			this.setTabPlacement(WebTabbedPane.LEFT);
		}
		
		/**
		 * 
		 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
		 * @version 0.1.0
		 * @since 0.1.0
		 */
		private final class SettingsGeneral extends WebPanel {

			private static final long serialVersionUID = 3851414624753986080L;
			
			/**
			 * 
			 * @since 0.1.0
			 */
			public SettingsGeneral() {
				
				
			}
		}
		
		/**
		 * 
		 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
		 * @version 0.1.0
		 * @since 0.1.0
		 */
		private final class SettingsLuaEditor extends WebPanel {

			private static final long serialVersionUID = 2539867214306380571L;

			/**
			 * 
			 * @since 0.1.0
			 */
			public SettingsLuaEditor() {
				
				
			}
		}
		
		/**
		 * 
		 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
		 * @version 0.1.0
		 * @since 0.1.0
		 */
		private final class SettingsJavaScriptEditor extends WebPanel {

			private static final long serialVersionUID = 8877341791303747287L;
			
			/**
			 * 
			 * @since 0.1.0
			 */
			public SettingsJavaScriptEditor() {
				
				
			}
		}
	}
}
