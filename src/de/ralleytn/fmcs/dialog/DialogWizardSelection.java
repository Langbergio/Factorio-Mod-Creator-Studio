package de.ralleytn.fmcs.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;

import de.ralleytn.fmcs.AbstractAdapter;
import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Program;
import de.ralleytn.fmcs.wizard.AbstractWizard;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class DialogWizardSelection extends WebDialog {

	private static final long serialVersionUID = -6606776088750101244L;
	
	private WebButton buttonSelect;
	private WebButton buttonCancel;
	private DefaultListModel<AbstractWizard> listModel;
	private JList<AbstractWizard> listWizardSelection;
	private Adapter adapter;

	/**
	 * 
	 * @param wizards
	 * @since 0.1.0
	 */
	public DialogWizardSelection(AbstractWizard... wizards) {
		
		super(Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getFrame());
		
		this.adapter = new Adapter(this);
		
		this.listModel = new DefaultListModel<>();
		
		for(AbstractWizard wizard : wizards) {
			
			this.listModel.addElement(wizard);
		}
		
		this.listWizardSelection = new JList<>(this.listModel);
		this.listWizardSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listWizardSelection.addListSelectionListener(this.adapter);
		this.listWizardSelection.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 4994342729065079703L;
			
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

				return ((AbstractWizard)value).createLabel();
			}
		});
		
		this.buttonSelect = new WebButton("Select");
		this.buttonSelect.setPreferredWidth(100);
		this.buttonSelect.addActionListener(this.adapter);
		this.buttonSelect.setEnabled(false);
		
		this.buttonCancel = new WebButton("Cancel");
		this.buttonCancel.setPreferredWidth(100);
		this.buttonCancel.addActionListener(this.adapter);
		
		WebPanel panelButtons = new WebPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelButtons.add(this.buttonSelect);
		panelButtons.add(this.buttonCancel);
		
		WebPanel contentPane = new WebPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new WebScrollPane(this.listWizardSelection), BorderLayout.CENTER);
		contentPane.add(panelButtons, BorderLayout.SOUTH);
		
		this.setSize(330, 480);
		this.setLocationRelativeTo(this.getOwner());
		this.setResizable(false);
		this.setIconImage(Icons.get("new").getImage());
		this.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setContentPane(contentPane);
		this.setTitle("Select a Wizard");
		this.setVisible(true);
	}
	
	/**
	 * 
	 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
	 * @version 0.1.0
	 * @since 0.1.0
	 */
	private final class Adapter extends AbstractAdapter<DialogWizardSelection> {

		/**
		 * 
		 * @param motherClassInstance
		 * @since 0.1.0
		 */
		public Adapter(DialogWizardSelection motherClassInstance) {
			
			super(motherClassInstance);
		}
		
		@Override
		public void valueChanged(ListSelectionEvent event) {
			
			this.getMotherClassInstance().buttonSelect.setEnabled(this.getMotherClassInstance().listWizardSelection.getSelectedValue() != null);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			Object source = event.getSource();
			
			if(source == this.getMotherClassInstance().buttonCancel) {
				
				this.getMotherClassInstance().dispose();
				
			} else if(source == this.getMotherClassInstance().buttonSelect) {
				
				this.getMotherClassInstance().dispose();
				this.getMotherClassInstance().listWizardSelection.getSelectedValue().setVisible(true);
			}
		}
	}
}
