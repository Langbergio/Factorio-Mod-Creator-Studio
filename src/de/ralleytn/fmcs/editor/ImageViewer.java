package de.ralleytn.fmcs.editor;


import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

public class ImageViewer extends WebScrollPane {

	private static final long serialVersionUID = -1710048890649126587L;

	public ImageViewer(BufferedImage image) {
		
		super(ImageViewer._createView(image));
		
		this.getHorizontalScrollBar().setUnitIncrement(20);
		this.getVerticalScrollBar().setUnitIncrement(20);
	}

	private static final WebPanel _createView(BufferedImage image) {
		
		WebLabel label = new WebLabel(new ImageIcon(image));
		label.setHorizontalAlignment(WebLabel.CENTER);
		label.setVerticalAlignment(WebLabel.CENTER);
		
		WebPanel panel = new WebPanel();
		panel.setLayout(new BorderLayout());
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
}
