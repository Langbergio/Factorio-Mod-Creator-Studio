package de.ralleytn.fmcs.wizard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import com.alee.extended.layout.FormLayout;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;

import de.ralleytn.fmcs.Dependency;
import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Language;
import de.ralleytn.fmcs.AbstractAdapter;
import de.ralleytn.fmcs.Library;
import de.ralleytn.fmcs.ModInfo;
import de.ralleytn.fmcs.Program;
import de.ralleytn.fmcs.Project;
import de.ralleytn.fmcs.Projects;
import de.ralleytn.fmcs.Utils;
import de.ralleytn.fmcs.dialog.DialogDependency;
import de.ralleytn.fmcs.dialog.FileChooserAddLibrary;
import de.ralleytn.fmcs.ui.FMCSButton;

public class WizardNewProject extends AbstractWizard {

	private static final long serialVersionUID = 1129393069421766825L;
	
	private Page1 page1;
	private Page2 page2;
	private Page3 page3;
	private Page4 page4;
	
	public WizardNewProject() {
		
		super("New Project", Icons.get("project"));

		this.page1 = new Page1();
		this.page2 = new Page2();
		this.page3 = new Page3();
		this.page4 = new Page4();
		
		this.setPages(this.page1, this.page2, this.page3, this.page4);
		this.getButtonFinish().bindTo(this.page1.fieldModName, this.page1.fieldModTitle, this.page1.fieldProjectName, this.page1.fieldAuthor);
		this.setSize(this.getWidth(), this.getHeight() + 15);
	}

	@Override
	public void onFinish() {
		
		ModInfo info = new ModInfo();
		info.setContact(this.page1.fieldContact.getText());
		info.setDescription(this.page2.fieldDescription.getText());
		info.setHomepage(this.page1.fieldHomepage.getText());
		info.setName(this.page1.fieldModName.getText());
		info.setTargetGameVersion(this.page1.fieldTargetGameVersion.getValue().toString());
		info.setTitle(this.page1.fieldModTitle.getText());
		info.setVersion(Utils.getVersion(this.page1.fieldModVersion));
		info.setDependencies(this.page4.getDependencies());
		
		Language language = null;
		
		for(WebRadioButton radioButton : this.page1.fieldLanguage) {
			
			if(radioButton.isSelected()) {
				
				for(Language lang : Language.values()) {
					
					if(lang.getLangName().equals(radioButton.getName())) {
						
						language = lang;
						break;
					}
				}
				
				break;
			}
		}
		
		Project project = new Project(this.page1.fieldProjectName.getText(), language, new File("projects/" + this.page1.fieldModName.getText() + "/project.json"));
		project.save();
		project.attachToTree();
		project.setInfo(info);
		
		for(Library library : this.page3.getLibraries()) {
			
			project.addLibrary(library, true);
		}
		
		Projects.addProject(project);
		Projects.saveRegister();
		project.save();
	}

	private class Page1 extends WebPanel {

		private static final long serialVersionUID = 2132785278876023562L;
		private static final int PREFERED_FIELD_WIDTH = 430;
		
		private WebTextField fieldProjectName;
		private WebTextField fieldModName;
		private WebTextField fieldModTitle;
		private WebSpinner[] fieldModVersion;
		private WebSpinner fieldTargetGameVersion;
		private WebTextField fieldAuthor;
		private WebTextField fieldContact;
		private WebTextField fieldHomepage;
		private WebRadioButton[] fieldLanguage;
		private Adapter adapter;
		
		public Page1() {

			this.adapter = new Adapter(this);
			
			this.fieldProjectName = new WebTextField();
			this.fieldProjectName.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			this.fieldProjectName.addKeyListener(this.adapter);
			
			this.fieldModName = new WebTextField();
			this.fieldModName.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			
			this.fieldModTitle = new WebTextField();
			this.fieldModTitle.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			
			WebPanel panelName = new WebPanel();
			panelName.setLayout(new FormLayout());
			panelName.setBorder(BorderFactory.createTitledBorder("Name"));
			panelName.add(new WebLabel("<html>Project Name: <font color=red>*</font></html>", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelName.add(this.fieldProjectName, FormLayout.RIGHT);
			panelName.add(new WebLabel("<html>Mod Name: <font color=red>*</font></html>", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelName.add(this.fieldModName, FormLayout.RIGHT);
			panelName.add(new WebLabel("<html>Mod Title: <font color=red>*</font></html>", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelName.add(this.fieldModTitle, FormLayout.RIGHT);
			
			WebPanel panelModVersion = new WebPanel();
			panelModVersion.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelModVersion.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			this.fieldModVersion = new WebSpinner[3];
			
			for(int index = 0; index < this.fieldModVersion.length; index++) {
				
				this.fieldModVersion[index] = new WebSpinner(new SpinnerNumberModel(index == 1 ? 1 : 0, 0, 100, 1));
				panelModVersion.add(this.fieldModVersion[index]);
			}
			
			this.fieldTargetGameVersion = new WebSpinner(new SpinnerListModel(Program.ALL_MODDABLE_FACTORIO_VERSIONS));
			this.fieldTargetGameVersion.setValue(Program.CURRENT_FACTORIO_VERSION);
			this.fieldTargetGameVersion.setPreferredSize(new Dimension(195, 32));
			this.fieldTargetGameVersion.addChangeListener(this.adapter);
			
			WebPanel panelTargetGameVersion = new WebPanel();
			panelTargetGameVersion.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelTargetGameVersion.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			panelTargetGameVersion.add(this.fieldTargetGameVersion);
			
			WebPanel panelVersion = new WebPanel();
			panelVersion.setLayout(new FormLayout());
			panelVersion.setBorder(BorderFactory.createTitledBorder("Version"));
			panelVersion.add(new WebLabel("Mod Version: ", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelVersion.add(panelModVersion, FormLayout.RIGHT);
			panelVersion.add(new WebLabel("Target Game Version: ", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelVersion.add(panelTargetGameVersion, FormLayout.RIGHT);
			
			this.fieldAuthor = new WebTextField(System.getProperty("user.name"));
			this.fieldAuthor.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			
			this.fieldContact = new WebTextField();
			this.fieldContact.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			
			this.fieldHomepage = new WebTextField("http://");
			this.fieldHomepage.setPreferredWidth(Page1.PREFERED_FIELD_WIDTH);
			
			WebPanel panelCreator = new WebPanel();
			panelCreator.setLayout(new FormLayout());
			panelCreator.setBorder(BorderFactory.createTitledBorder("Creator"));
			panelCreator.add(new WebLabel("<html>Author: <font color=red>*</font></html>", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelCreator.add(this.fieldAuthor, FormLayout.RIGHT);
			panelCreator.add(new WebLabel("Contact: ", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelCreator.add(this.fieldContact, FormLayout.RIGHT);
			panelCreator.add(new WebLabel("Homepage: ", new Insets(0, 5, 0, 0)), FormLayout.LEFT);
			panelCreator.add(this.fieldHomepage, FormLayout.RIGHT);
			
			WebPanel panelLanguage = new WebPanel();
			panelLanguage.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
			panelLanguage.setBorder(BorderFactory.createTitledBorder("Project Language"));
			
			this.fieldLanguage = new WebRadioButton[Language.values().length];
			
			for(int index = 0; index < this.fieldLanguage.length; index++) {
				
				this.fieldLanguage[index] = new WebRadioButton(Language.values()[index].getName(), index == 0);
				this.fieldLanguage[index].addActionListener(this.adapter);
				this.fieldLanguage[index].setName(Language.values()[index].getLangName());
				panelLanguage.add(this.fieldLanguage[index]);
			}
			
			this.setMargin(5, 5, 5, 5);
			this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
			this.add(panelName);
			this.add(panelVersion);
			this.add(panelCreator);
			this.add(panelLanguage);
		}
		
		private final class Adapter extends AbstractAdapter<Page1> {

			public Adapter(Page1 motherClassInstance) {
				
				super(motherClassInstance);
			}
			
			private void _updateFields() {
				
				String projectName = this.getMotherClassInstance().fieldProjectName.getText();
				this.getMotherClassInstance().fieldModTitle.setText(projectName);
				this.getMotherClassInstance().fieldModName.setText(Utils.replaceInvalidCharacters(projectName));
			}
			
			@Override
			public void stateChanged(ChangeEvent event) {
				
				Object source = event.getSource();
				
				if(source == this.getMotherClassInstance().fieldTargetGameVersion) {
					
					WizardNewProject.this.page4.dependencyBase.setMinimumVersion(this.getMotherClassInstance().fieldTargetGameVersion.getValue().toString());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent event) {
				
				this._updateFields();
			}
			
			@Override
			public void keyReleased(KeyEvent event) {
				
				this._updateFields();
			}
			
			@Override
			public void keyTyped(KeyEvent event) {
				
				this._updateFields();
			}
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				Object source = event.getSource();
				boolean isLanguageSelection = false;
				
				for(int index = 0; index < this.getMotherClassInstance().fieldLanguage.length; index++) {
					
					if(source == this.getMotherClassInstance().fieldLanguage[index]) {
						
						isLanguageSelection = true;
						break;
					}
				}

				for(int index = 0; index < this.getMotherClassInstance().fieldLanguage.length && isLanguageSelection; index++) {
						
					this.getMotherClassInstance().fieldLanguage[index].setSelected(source == this.getMotherClassInstance().fieldLanguage[index]);
				}
			}
		}
	}
	
	private class Page2 extends WebPanel {

		private static final long serialVersionUID = 8153147651808494359L;
		
		private WebTextArea fieldDescription;

		public Page2() {

			WebPanel panelDescription = new WebPanel();
			panelDescription.setLayout(new BorderLayout());
			panelDescription.setBorder(BorderFactory.createTitledBorder("Description"));
			panelDescription.add(new WebScrollPane((this.fieldDescription = new WebTextArea())), BorderLayout.CENTER);
			
			this.setMargin(5, 5, 5, 5);
			this.setLayout(new BorderLayout());
			this.add(panelDescription, BorderLayout.CENTER);
		}
	}
	
	private class Page3 extends WebPanel {

		private static final long serialVersionUID = 2220260294649107757L;
		
		private FMCSButton buttonAdd;
		private FMCSButton buttonRemove;
		private JList<Library> listLibraries;
		private DefaultListModel<Library> listModel;
		private Adapter adapter;

		public Page3() {
			
			this.adapter = new Adapter(this);
			
			this.buttonAdd = new FMCSButton("Add");
			this.buttonAdd.setPreferredWidth(100);
			this.buttonAdd.addActionListener(this.adapter);
			
			this.buttonRemove = new FMCSButton("Remove");
			this.buttonRemove.setPreferredWidth(100);
			this.buttonRemove.setEnabled(false);
			this.buttonRemove.addActionListener(this.adapter);
			
			WebPanel panelButtons = new WebPanel();
			panelButtons.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
			panelButtons.add(this.buttonAdd);
			panelButtons.add(this.buttonRemove);
			
			this.listLibraries = new JList<>((this.listModel = new DefaultListModel<>()));
			this.listLibraries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.listLibraries.addListSelectionListener(this.adapter);
			this.listLibraries.setCellRenderer(new DefaultListCellRenderer() {

				private static final long serialVersionUID = 8155777173599152487L;

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

					return ((Library)value).createLabel();
				}
			});
			
			for(Library lib : Program.DEFAULT_LIBRARIES) {
				
				this.listModel.addElement(lib);
			}
			
			WebPanel panelLibraries = new WebPanel();
			panelLibraries.setLayout(new BorderLayout());
			panelLibraries.setBorder(BorderFactory.createTitledBorder("Libraries"));
			panelLibraries.add(new WebScrollPane(this.listLibraries), BorderLayout.CENTER);
			panelLibraries.add(panelButtons, BorderLayout.EAST);
			
			this.setMargin(5, 5, 5, 5);
			this.setLayout(new BorderLayout());
			this.add(panelLibraries, BorderLayout.CENTER);
		}
		
		public List<Library> getLibraries() {
			
			Object[] array = this.listModel.toArray();
			List<Library> list = new ArrayList<>();
			
			for(Object element : array) {
				
				list.add((Library)element);
			}
			
			return list;
		}
		
		private final class Adapter extends AbstractAdapter<Page3> {

			public Adapter(Page3 motherClassInstance) {
				
				super(motherClassInstance);
			}
			
			@Override
			public void actionPerformed(ActionEvent event) {

				Object source = event.getSource();
				
				if(source == this.getMotherClassInstance().buttonAdd) {
					
					for(Library library : new FileChooserAddLibrary(this.getMotherClassInstance(), this.getMotherClassInstance().getLibraries()).getLibraries()) {
						
						this.getMotherClassInstance().listModel.addElement(library);
					}
					
				} else if(source == this.getMotherClassInstance().buttonRemove) {
					
					this.getMotherClassInstance().listModel.remove(this.getMotherClassInstance().listLibraries.getSelectedIndex());
				}
			}
			
			@Override
			public void valueChanged(ListSelectionEvent event) {

				this.getMotherClassInstance().buttonRemove.setEnabled(this.getMotherClassInstance().listLibraries.getSelectedValue() != null);
			}
		}
	}
	
	private class Page4 extends WebPanel {

		private static final long serialVersionUID = 8153147651808494359L;
		
		private FMCSButton buttonAdd;
		private FMCSButton buttonEdit;
		private FMCSButton buttonRemove;
		private JList<Dependency> listDependencies;
		private DefaultListModel<Dependency> listModel;
		private WebCheckBox fieldDependsOnBase;
		private Dependency dependencyBase;
		private Adapter adapter;

		public Page4() {
			
			this.adapter = new Adapter(this);
			
			this.buttonAdd = new FMCSButton("Add");
			this.buttonAdd.setPreferredWidth(100);
			this.buttonAdd.addActionListener(this.adapter);
			
			this.buttonEdit = new FMCSButton("Edit");
			this.buttonEdit.setPreferredWidth(100);
			this.buttonEdit.setEnabled(false);
			this.buttonEdit.addActionListener(this.adapter);
			
			this.buttonRemove = new FMCSButton("Remove");
			this.buttonRemove.setPreferredWidth(100);
			this.buttonRemove.setEnabled(false);
			this.buttonRemove.addActionListener(this.adapter);
			
			WebPanel panelButtons = new WebPanel();
			panelButtons.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
			panelButtons.add(this.buttonAdd);
			panelButtons.add(this.buttonEdit);
			panelButtons.add(this.buttonRemove);
			
			this.listDependencies = new JList<Dependency>((this.listModel = new DefaultListModel<>()));
			this.listDependencies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.listDependencies.addListSelectionListener(this.adapter);
			this.listDependencies.setCellRenderer(new DefaultListCellRenderer() {

				private static final long serialVersionUID = 8155777173599152487L;

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

					return ((Dependency)value).createLabel();
				}
			});
			
			this.fieldDependsOnBase = new WebCheckBox("Depends on \"base\" mod", true);
			this.fieldDependsOnBase.addActionListener(this.adapter);
			
			this.listModel.addElement((this.dependencyBase = new Dependency("base", true, WizardNewProject.this.page1.fieldTargetGameVersion.getValue().toString())));
			
			WebPanel panelDependencies = new WebPanel();
			panelDependencies.setLayout(new BorderLayout());
			panelDependencies.setBorder(BorderFactory.createTitledBorder("Dependencies"));
			panelDependencies.add(panelButtons, BorderLayout.EAST);
			panelDependencies.add(new WebScrollPane(this.listDependencies), BorderLayout.CENTER);
			panelDependencies.add(this.fieldDependsOnBase, BorderLayout.SOUTH);
			
			this.setMargin(5, 5, 5, 5);
			this.setLayout(new BorderLayout());
			this.add(panelDependencies, BorderLayout.CENTER);
		}
		
		public List<Dependency> getDependencies() {
			
			List<Dependency> dependencies = new ArrayList<>();
			
			for(int index = 0; index < this.listModel.size(); index++) {
				
				dependencies.add(this.listModel.getElementAt(index));
			}
			
			return dependencies;
		}
		
		private final class Adapter extends AbstractAdapter<Page4> {

			public Adapter(Page4 motherClassInstance) {
				
				super(motherClassInstance);
			}
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				boolean editable = this.getMotherClassInstance().listDependencies.getSelectedValue() != null && this.getMotherClassInstance().listDependencies.getSelectedValue() != this.getMotherClassInstance().dependencyBase;
				this.getMotherClassInstance().buttonRemove.setEnabled(editable);
				this.getMotherClassInstance().buttonEdit.setEnabled(editable);
			}
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				Object source = event.getSource();
				
				if(source == this.getMotherClassInstance().buttonAdd) {
					
					new DialogDependency(this.getMotherClassInstance().getDependencies());
					
				} else if(source == this.getMotherClassInstance().buttonEdit) {
					
					
				} else if(source == this.getMotherClassInstance().buttonRemove) {
					
					
				} else if(source == this.getMotherClassInstance().fieldDependsOnBase) {
					
					if(this.getMotherClassInstance().fieldDependsOnBase.isSelected()) {
						
						this.getMotherClassInstance().listModel.addElement(this.getMotherClassInstance().dependencyBase);
						
					} else {
						
						this.getMotherClassInstance().listModel.removeElement(this.getMotherClassInstance().dependencyBase);
					}
				}
			}
		}
	}
}
