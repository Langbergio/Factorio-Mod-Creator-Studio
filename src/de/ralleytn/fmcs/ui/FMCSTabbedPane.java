package de.ralleytn.fmcs.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.WebTabbedPane;

import de.ralleytn.fmcs.Icons;

public class FMCSTabbedPane extends WebTabbedPane {

	private static final long serialVersionUID = -5835240693922873922L;

	@Override
	public void addTab(String title, Icon icon, Component component) {

		// http://stackoverflow.com/questions/11553112/how-to-add-close-button-to-a-jtabbedpane-tab
		
		super.addTab(title, icon, component);
		
		int index = this.indexOfTab(title);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		
		WebLabel label = icon != null ? new WebLabel(title + "  ", icon) : new WebLabel(title + "  ");
		
		final WebButton buttonClose = new WebButton(Icons.get("close"));
		buttonClose.addActionListener(event -> {
			
			FMCSTabbedPane.this.removeTabAt(FMCSTabbedPane.this.indexOfTab(title));
		});
		
		WebPanel panel = new WebPanel();
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(false);
		panel.add(label, constraints);
		
		constraints.gridx++;
		constraints.weightx = 0;
		
		panel.add(buttonClose, constraints);
		
		this.setTabComponentAt(index, panel);
		this.setSelectedIndex(index);
	}
}
