package de.ralleytn.fmcs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;

import de.ralleytn.fmcs.editor.EditorJavaScript;
import de.ralleytn.fmcs.editor.EditorLua;
import de.ralleytn.fmcs.ui.FMCSFrameMenus;
import de.ralleytn.fmcs.ui.FMCSTabbedPane;
import de.ralleytn.fmcs.ui.FMCSProjectsTree;
import de.ralleytn.fmcs.ui.FMCSToolBar;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class GUI {

	private WebFrame frame;
	private WebPanel contentPane;
	private WebPanel projectsPanel;
	private WebPanel mainContentPanel;
	private WebSplitPane splitPane;
	private FMCSProjectsTree projectsTree;
	private FMCSFrameMenus frameMenus;
	private FMCSToolBar buttonMenuBar;
	private FMCSTabbedPane editorTabs;
	private Adapter adapter;
	
	/**
	 * 
	 * @since 0.1.0
	 */
	public GUI() {
		
		this.adapter = new Adapter(this);
		
		this.projectsTree = new FMCSProjectsTree();
	}
	
	/**
	 * 
	 * @since 0.1.0
	 */
	public void create() {
		
		WebScrollPane scrollPane = new WebScrollPane(this.projectsTree);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		
		this.projectsPanel = new WebPanel();
		this.projectsPanel.setPreferredWidth(300);
		this.projectsPanel.setLayout(new BorderLayout());
		this.projectsPanel.add(scrollPane, BorderLayout.CENTER);
		
		this.editorTabs = new FMCSTabbedPane();
		this.editorTabs.addTab("Lua Editor Test", Icons.get("code"), new EditorLua(null));
		this.editorTabs.addTab("JavaScript Editor Test", Icons.get("code"), new EditorJavaScript(null));
		
		this.mainContentPanel = new WebPanel();
		this.mainContentPanel.setLayout(new BorderLayout());
		this.mainContentPanel.add(this.editorTabs, BorderLayout.CENTER);
		
		this.splitPane = new WebSplitPane();
		this.splitPane.setLeftComponent(this.projectsPanel);
		this.splitPane.setRightComponent(this.mainContentPanel);
		this.splitPane.setOneTouchExpandable(true);
		
		this.buttonMenuBar = new FMCSToolBar();
		
		this.contentPane = new WebPanel();
		this.contentPane.setLayout(new BorderLayout());
		this.contentPane.add(this.buttonMenuBar, BorderLayout.NORTH);
		this.contentPane.add(this.splitPane, BorderLayout.CENTER);
		
		this.frameMenus = new FMCSFrameMenus();
		
		this.frame = new WebFrame("Factorio Mod Creator Studio");
		this.frame.setJMenuBar(this.frameMenus);
		this.frame.setSize(Settings.getInt("window.width"), Settings.getInt("window.height"));
		this.frame.setMinimumSize(new Dimension(800, 600));
		this.frame.setLocationRelativeTo(null);
		this.frame.setContentPane(this.contentPane);
		this.frame.setDefaultCloseOperation(WebFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(this.adapter);
		this.frame.setExtendedState(Settings.getInt("window.state"));
		this.frame.setIconImages(Program.FRAME_ICON);
		this.frame.setVisible(true);
		
		this.projectsTree.reload();
		
		if(Projects.getNumberOfProjects() > 0) {
			
			this.projectsTree.setSelectedNode(Projects.getFirstProject().getTreeNode());
		}
		
		Program.SPLASH.dispose();
		this.frame.setVisible(true);
	}
	
	public FMCSTabbedPane getEditorTabs() {
		
		return this.editorTabs;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public FMCSProjectsTree getProjectsTree() {
		
		return this.projectsTree;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public WebFrame getFrame() {
		
		return this.frame;
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	private final class Adapter extends AbstractAdapter<GUI> {

		/**
		 * 
		 * @param motherClassInstance
		 * @since 0.1.0
		 */
		public Adapter(GUI motherClassInstance) {
			
			super(motherClassInstance);
		}

		@Override
		public void windowClosing(WindowEvent event) {
			
			Program.FACTORIO_MOD_CREATOR_STUDIO.exit();
		}
	}
}
