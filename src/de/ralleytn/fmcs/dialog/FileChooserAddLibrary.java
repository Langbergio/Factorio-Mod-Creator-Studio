package de.ralleytn.fmcs.dialog;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import com.alee.laf.filechooser.WebFileChooser;

import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Library;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class FileChooserAddLibrary extends WebFileChooser {

	private static final long serialVersionUID = -5452190277420811349L;
	
	private Component parent;

	/**
	 * 
	 * @param parent
	 * @param libraries
	 * @since 0.1.0
	 */
	public FileChooserAddLibrary(Component parent, List<Library> libraries) {
		
		super(new File(System.getProperty("user.home")));
		
		this.parent = parent;
		
		Filter filter = new Filter(libraries);
		
		this.setAcceptAllFileFilterUsed(false);
		this.setDialogIcon(Icons.get("library"));
		this.setDialogTitle("Add Library");
		this.setMultiSelectionEnabled(true);
		this.addChoosableFileFilter(filter);
		this.setFileFilter(filter);
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public List<Library> getLibraries() {

		List<Library> libraries = new ArrayList<>();
		
		if(this.showOpenDialog(this.parent) == WebFileChooser.APPROVE_OPTION) {
			
			for(File file : this.getSelectedFiles()) {
				
				libraries.add(new Library(file));
			}
		}
		
		return libraries;
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	private final class Filter extends FileFilter {
		
		private List<String> libraryNames;
		
		/**
		 * 
		 * @param libraries
		 * @since 0.1.0
		 */
		public Filter(List<Library> libraries) {
			
			this.libraryNames = new ArrayList<>();
			
			for(Library library : libraries) {
				
				this.libraryNames.add(library.getName());
			}
		}
		
		@Override
		public String getDescription() {
			
			return "Library (.zip)";
		}
		
		@Override
		public boolean accept(File file) {

			return file.getName().toUpperCase().endsWith(".ZIP") && file.isFile() && !this.libraryNames.contains(file.getName().substring(0, file.getName().lastIndexOf('.')));
		}
	}
}
