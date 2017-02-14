package de.ralleytn.fmcs.dialog;

import java.awt.BorderLayout;
import java.util.List;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

import de.ralleytn.fmcs.Dependency;
import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Program;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class DialogDependency extends WebDialog {

	private static final long serialVersionUID = 7305166575060072728L;

	public DialogDependency(List<Dependency> dependecies) {
		
		super(Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getFrame());
		
		WebPanel contentPane = new WebPanel();
		contentPane.setLayout(new BorderLayout());
		
		this.setTitle("Dependency");
		this.setSize(640, 280);
		this.setResizable(false);
		this.setLocationRelativeTo(this.getOwner());
		this.setModal(true);
		this.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		this.setContentPane(contentPane);
		this.setIconImage(Icons.get("link").getImage());
		this.setVisible(true);
	}
}
