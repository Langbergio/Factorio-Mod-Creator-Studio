package de.ralleytn.fmcs.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Program;
import de.ralleytn.fmcs.Project;
import de.ralleytn.fmcs.Utils;
import de.ralleytn.fmcs.editor.EditorJavaScript;
import de.ralleytn.fmcs.editor.EditorLua;
import de.ralleytn.fmcs.editor.ImageViewer;

public class FMCSMutableProjectsTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 5755292419324646475L;
	
	private String iconName;
	private Project project;
	
	public FMCSMutableProjectsTreeNode(String text) {
		
		super(text);
	}
	
	public FMCSMutableProjectsTreeNode(String text, boolean allowChildren) {
		
		super(text, allowChildren);
	}

	public FMCSMutableProjectsTreeNode(String text, boolean allowChildren, String iconName) {
		
		super(text, allowChildren);
		
		this.iconName = iconName;
	}
	
	public void bindToProject(Project project) {
		
		this.project = project;
	}
	
	public Project getBoundProject() {
		
		return this.project;
	}
	
	public void bindToZipEntry(final File zip, final String entryName) {
		
		FMCSProjectsTree tree = Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getProjectsTree();
		tree.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent event) {
				
				int selRow = tree.getRowForLocation(event.getX(), event.getY());
		        TreePath selPath = tree.getPathForLocation(event.getX(), event.getY());
			
		        if(selRow != -1 && event.getClickCount() == 2) {
		        	
		        	FMCSMutableProjectsTreeNode selectedNode = (FMCSMutableProjectsTreeNode)selPath.getLastPathComponent();
		        
		        	if(selectedNode == FMCSMutableProjectsTreeNode.this) {
		        		
		        		String name = (String)selectedNode.getUserObject();
		        		RSyntaxTextArea textArea = null;
		        		boolean isTextEditor = false;
		        		
		        		if(name.toUpperCase().endsWith(".LUA")) {
		        			
		        			EditorLua editor = new EditorLua(selectedNode.getBoundProject());
		        			Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getEditorTabs().addTab(name, Icons.get(selectedNode.getIconName()), editor);
		        			textArea = (RSyntaxTextArea)editor.getTextArea();
		        			isTextEditor = true;
		        			
		        		} else if(name.toUpperCase().endsWith(".JS")) {
		        			
		        			EditorJavaScript editor = new EditorJavaScript(selectedNode.getBoundProject());
		        			Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getEditorTabs().addTab(name, Icons.get(selectedNode.getIconName()), editor);
		        			textArea = (RSyntaxTextArea)editor.getTextArea();
		        			isTextEditor = false;
		        			
		        		} else if(name.toUpperCase().endsWith(".PNG") || name.toUpperCase().endsWith(".JPG") || name.toUpperCase().endsWith(".JPEG") || name.toUpperCase().endsWith(".BMP") || name.toUpperCase().endsWith(".GIF")) {
		        			
		        			BufferedImage image = null;
		        			
		        			try(ZipFile zipFile = new ZipFile(zip)) {
		        				
		        				ZipEntry entry = zipFile.getEntry(entryName);
		        				image = ImageIO.read(zipFile.getInputStream(entry));
		        				
		        			} catch(IOException exception) {
		        				
		        				Utils.handleException(exception);
		        			}
		        			
		        			ImageViewer viewer = new ImageViewer(image);
		        			Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getEditorTabs().addTab(name, Icons.get(selectedNode.getIconName()), viewer);
		        		}
		        		
		        		if(isTextEditor) {
		        			
		        			textArea.setEditable(false);
		        			textArea.removeParser(Program.SPELL_CHECKER);
			        		
			        		try(ZipFile zipFile = new ZipFile(zip)) {
			        			
			        			ZipEntry entry = zipFile.getEntry(entryName);
			        			
			        			try(BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry), "UTF-8"))) {
			        				
			        				String line = null;
			        				
			        				while((line = reader.readLine()) != null) {
			        					
			        					textArea.append(line + "\n");
			        				}
			        			}
			        			
			        		} catch(IOException exception) {
								
			        			Utils.handleException(exception);
							}
			        		
			        		textArea.setCaretPosition(0);
		        		}
		        	}
		        }
			}
			
			@Override public void mouseReleased(MouseEvent event) {}
			@Override public void mouseExited(MouseEvent event) {}
			@Override public void mouseEntered(MouseEvent event) {}
			@Override public void mouseClicked(MouseEvent event) {}
		});
	}
	
	public void setIconName(String iconName) {
		
		this.iconName = iconName;
	}
	
	public String getIconName() {
		
		return this.iconName;
	}
}
