package de.ralleytn.fmcs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;

import de.ralleytn.fmcs.ui.FMCSTabbedPane;
import de.ralleytn.fmcs.ui.FMCSTree;

public class GUI {

	private WebFrame frame;
	private WebPanel contentPane;
	private WebPanel projectsPanel;
	private WebPanel mainContentPanel;
	private WebSplitPane splitPane;
	private FMCSTree projectsTree;
	private MenuBarMenus frameMenuBar;
	private MenuBarButtons buttonMenuBar;
	private FMCSTabbedPane editorTabs;
	private Adapter adapter;
	
	public GUI() {
		
		this.adapter = new Adapter(this);
		
		this.projectsTree = new FMCSTree();
	}
	
	public void create() {
		
		this.projectsPanel = new WebPanel();
		this.projectsPanel.setPreferredWidth(300);
		this.projectsPanel.setLayout(new BorderLayout());
		this.projectsPanel.add(new WebScrollPane(this.projectsTree), BorderLayout.CENTER);
		
		this.editorTabs = new FMCSTabbedPane();
		
		this.mainContentPanel = new WebPanel();
		this.mainContentPanel.setLayout(new BorderLayout());
		this.mainContentPanel.add(this.editorTabs, BorderLayout.CENTER);
		
		this.splitPane = new WebSplitPane();
		this.splitPane.setLeftComponent(this.projectsPanel);
		this.splitPane.setRightComponent(this.mainContentPanel);
		this.splitPane.setOneTouchExpandable(true);
		
		this.buttonMenuBar = new MenuBarButtons();
		
		this.contentPane = new WebPanel();
		this.contentPane.setLayout(new BorderLayout());
		this.contentPane.add(this.buttonMenuBar, BorderLayout.NORTH);
		this.contentPane.add(this.splitPane, BorderLayout.CENTER);
		
		this.frameMenuBar = new MenuBarMenus();
		
		this.frame = new WebFrame("Factorio Mod Creator Studio");
		this.frame.setJMenuBar(this.frameMenuBar);
		this.frame.setSize(Settings.getInt("window.width"), Settings.getInt("window.height"));
		this.frame.setMinimumSize(new Dimension(800, 600));
		this.frame.setLocationRelativeTo(null);
		this.frame.setContentPane(this.contentPane);
		this.frame.setDefaultCloseOperation(WebFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(this.adapter);
		this.frame.setExtendedState(Settings.getInt("window.state"));
		this.frame.setIconImages(this._loadFrameIcon());
		this.frame.setVisible(true);
		
		this.projectsTree.reload();
		
		if(Projects.getNumberOfProjects() > 0) {
			
			this.projectsTree.setSelectedNode(Projects.getFirstProject().getTreeNode());
		}
	}
	
	public WebPanel getProjectsPanel() {
		
		return this.projectsPanel;
	}
	
	public WebPanel getMainContentPanel() {
		
		return this.mainContentPanel;
	}
	
	public WebSplitPane getSplitPane() {
		
		return this.splitPane;
	}
	
	public MenuBarMenus getFrameMenuBar() {
		
		return this.frameMenuBar;
	}
	
	public MenuBarButtons getButtonMenuBar() {
		
		return this.buttonMenuBar;
	}
	
	public FMCSTabbedPane getEditorTabs() {
		
		return this.editorTabs;
	}
	
	public WebPanel getContentPane() {
		
		return this.contentPane;
	}
	
	public FMCSTree getProjectsTree() {
		
		return this.projectsTree;
	}
	
	public WebFrame getFrame() {
		
		return this.frame;
	}
	
	private List<BufferedImage> _loadFrameIcon() {
		
		int[] resolutions = {
				
			16, 24, 32, 48, 64, 128, 256, 512
		};
		
		List<BufferedImage> icons = new ArrayList<>();
		
		for(int index = 0; index < resolutions.length; index++) {
			
			try {
				
				icons.add(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("icon-" + resolutions[index] + ".png")));
			
			} catch(IOException exception) {
				
				Utils.handleException(exception);
			}
		}
		
		return icons;
	}
	
	private final class Adapter extends AbstractAdapter<GUI> {

		public Adapter(GUI motherClassInstance) {
			
			super(motherClassInstance);
		}

		@Override
		public void windowClosing(WindowEvent event) {
			
			Program.FACTORIO_MOD_CREATOR_STUDIO.exit();
		}
	}
}
