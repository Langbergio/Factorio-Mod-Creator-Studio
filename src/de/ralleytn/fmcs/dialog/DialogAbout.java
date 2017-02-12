package de.ralleytn.fmcs.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;

import de.ralleytn.fmcs.Icons;
import de.ralleytn.fmcs.Utils;

/**
 * The "About" dialog which is accessible through the "Help"-menu.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public class DialogAbout extends WebDialog {

	private static final long serialVersionUID = 7911138322648264865L;
	
	// Load up the "about.json" file.
	// It's static because it only has to be done once
	private static JSONObject JSON_DATA;
	
	static {
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(DialogAbout.class.getClassLoader().getResourceAsStream("about.json"), "UTF-8"))) {
			
			DialogAbout.JSON_DATA = (JSONObject)new JSONParser().parse(reader);
			
		} catch(IOException | ParseException exception) {
			
			Utils.handleException(exception);
		}
	}

	/**
	 * 
	 * @param owner
	 * @since 0.1.0
	 */
	public DialogAbout(WebFrame owner) {
		
		super(owner);
		
		WebLabel labelDevelopedBy = new WebLabel("<html>" + DialogAbout.JSON_DATA.get("developer"), WebLabel.CENTER);
		labelDevelopedBy.setBorder(BorderFactory.createTitledBorder("Developed by"));
		
		WebPanel panelContributors = new WebPanel();
		panelContributors.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
		panelContributors.setBorder(BorderFactory.createTitledBorder("Contributors"));
		
		for(Object element : (JSONArray)DialogAbout.JSON_DATA.get("contributors")) {
			
			panelContributors.add(new WebLabel("<html>" + (String)element));
		}
		
		WebPanel panelResources = new WebPanel();
		panelResources.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
		panelResources.setBorder(BorderFactory.createTitledBorder("Resources & Libraries"));
		
		for(Object element : (JSONArray)DialogAbout.JSON_DATA.get("resources")) {
			
			String href = (String)element;
			
			WebLinkLabel label = new WebLinkLabel(href);
			label.setLink(href);
			label.setIcon(Icons.get("link"));
			
			panelResources.add(label);
		}
		
		WebPanel contentPane = new WebPanel();
		contentPane.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
		contentPane.add(labelDevelopedBy);
		contentPane.add(panelContributors);
		contentPane.add(panelResources);
		
		this.setTitle("About");
		this.setSize(640, 480);
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
		this.setModal(true);
		this.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		this.setContentPane(contentPane);
		this.setIconImage(Icons.get("about").getImage());
		this.setVisible(true);
	}
}
