package de.ralleytn.fmcs.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebWindow;

import de.ralleytn.fmcs.Utils;

public class FMCSSplash extends WebWindow {

	private static final long serialVersionUID = 1021558036819993727L;
	
	private Background background;
	
	public FMCSSplash() {
		
		this.background = new Background();
		this.background.setLayout(new BorderLayout());
		
		try {
			
			WebLabel loading = new WebLabel("Loading...", new ImageIcon(new File("res/images/loading.gif").toURI().toURL()), WebLabel.LEFT);
			loading.setPreferredWidth(635);
			loading.setMargin(0, 5, 5, 0);
			
			this.background.add(loading, BorderLayout.SOUTH);
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
		
		this.setContentPane(this.background);
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}

	private class Background extends WebPanel {

		private static final long serialVersionUID = -262321090287843325L;
		
		private BufferedImage image;
		
		public Background() {
			
			try {
				
				this.image = ImageIO.read(new File("res/images/splash.png"));
				
			} catch(Exception exception) {
				
				Utils.handleException(exception);
			}
		}

		@Override
		protected void paintComponent(Graphics graphics) {

			super.paintComponent(graphics);
			
			graphics.drawImage(this.image, 0, 0, null);
		}
	}
}
