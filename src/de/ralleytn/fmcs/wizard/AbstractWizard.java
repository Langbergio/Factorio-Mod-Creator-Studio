package de.ralleytn.fmcs.wizard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

import de.ralleytn.fmcs.AbstractAdapter;
import de.ralleytn.fmcs.Program;
import de.ralleytn.fmcs.ui.FMCSButton;

public abstract class AbstractWizard extends WebDialog {

	private static final long serialVersionUID = 8233067293550534174L;
	private static final int PREFERED_WIDTH_OF_BUTTONS = 100;
	
	private String name;
	private ImageIcon icon;
	private List<WebPanel> pages;
	private Adapter adapter;
	private int page;
	
	private FMCSButton buttonCancel;
	private FMCSButton buttonBack;
	private FMCSButton buttonNext;
	private FMCSButton buttonFinish;
	
	public AbstractWizard(String name, ImageIcon icon) {
		
		super(Program.FACTORIO_MOD_CREATOR_STUDIO.getGUI().getFrame());
		
		this.name = name;
		this.icon = icon;
		this.pages = new ArrayList<>();
		this.adapter = new Adapter(this);
		
		this.buttonCancel = new FMCSButton("Cancel");
		this.buttonCancel.addActionListener(this.adapter);
		this.buttonCancel.setPreferredWidth(AbstractWizard.PREFERED_WIDTH_OF_BUTTONS);
		
		this.buttonBack = new FMCSButton("< Back");
		this.buttonBack.addActionListener(this.adapter);
		this.buttonBack.setPreferredWidth(AbstractWizard.PREFERED_WIDTH_OF_BUTTONS);
		
		this.buttonNext = new FMCSButton("Next >");
		this.buttonNext.addActionListener(this.adapter);
		this.buttonNext.setPreferredWidth(AbstractWizard.PREFERED_WIDTH_OF_BUTTONS);
		
		this.buttonFinish = new FMCSButton("Finish");
		this.buttonFinish.addActionListener(this.adapter);
		this.buttonFinish.setPreferredWidth(AbstractWizard.PREFERED_WIDTH_OF_BUTTONS);
		
		WebPanel panelButtons = new WebPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelButtons.add(this.buttonBack);
		panelButtons.add(this.buttonNext);
		panelButtons.add(new WebLabel("   "));
		panelButtons.add(this.buttonFinish);
		panelButtons.add(this.buttonCancel);
		
		WebPanel contentPane = new WebPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelButtons, BorderLayout.SOUTH);
		
		this.setModal(true);
		this.setSize(640, 480);
		this.setLocationRelativeTo(this.getOwner());
		this.setResizable(false);
		this.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		this.setContentPane(contentPane);
		this.setTitle(name);
		this.setName(name);
		this.setIconImage(icon.getImage());
		
		this._resetButtons();
	}
	
	public WebLabel createLabel() {
		
		return new WebLabel(this.getName(), this.getIcon());
	}
	
	public abstract void onFinish();
	
	@Override
	public void dispose() {
		
		super.dispose();
		
		this.page = 0;
	}
	
	private void _resetButtons() {
		
		this.page = 0;
		
		this.buttonBack.setEnabled(true);
		this.buttonCancel.unbindAll();
		
		this.buttonBack.setEnabled(false);
		this.buttonBack.unbindAll();
		
		this.buttonNext.setEnabled(false);
		this.buttonNext.unbindAll();
		
		this.buttonFinish.setEnabled(false);
		this.buttonFinish.unbindAll();
	}
	
	public void setPages(WebPanel... pages) {
		
		this._resetButtons();
		
		if(this.pages.size() > 0) {
			
			this.getContentPane().remove(this.pages.get(this.page));
		}
		
		this.pages.clear();
		
		for(WebPanel page : pages) {
			
			this.pages.add(page);
		}
		
		if(this.pages.size() > 0) {
			
			this.getContentPane().add(this.pages.get(0), BorderLayout.CENTER);
			((WebPanel)this.getContentPane()).updateUI();
		}
		
		if(this.pages.size() > 1) {
			
			this.setTitle(this.getName() + " (1/" + this.pages.size() + ")");
			this.buttonNext.setEnabled(true);
		}
	}
	
	public List<WebPanel> getPages() {
		
		return this.pages;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public Icon getIcon() {
		
		return this.icon;
	}
	
	public FMCSButton getButtonFinish() {
		
		return this.buttonFinish;
	}
	
	public FMCSButton getButtonCancel() {
		
		return this.buttonCancel;
	}
	
	public FMCSButton getButtonNext() {
		
		return this.buttonNext;
	}
	
	public FMCSButton getButtonBack() {
		
		return this.buttonBack;
	}
	
	public int getPage() {
		
		return this.page;
	}
	
	private final class Adapter extends AbstractAdapter<AbstractWizard> {

		public Adapter(AbstractWizard motherClassInstance) {
			
			super(motherClassInstance);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {

			Object source = event.getSource();
			
			if(source == this.getMotherClassInstance().buttonBack) {
				
				this.getMotherClassInstance().getContentPane().remove(this.getMotherClassInstance().pages.get(this.getMotherClassInstance().page));
				this.getMotherClassInstance().page--;
				this.getMotherClassInstance().buttonNext.setEnabled(true);
				this.getMotherClassInstance().setTitle(this.getMotherClassInstance().getName() + " (" + (this.getMotherClassInstance().page + 1) + "/" + this.getMotherClassInstance().pages.size() + ")");
				this.getMotherClassInstance().getContentPane().add(this.getMotherClassInstance().pages.get(this.getMotherClassInstance().page));
				
				if(this.getMotherClassInstance().page == 0) {
					
					this.getMotherClassInstance().buttonBack.setEnabled(false);
				}
				
				((WebPanel)this.getMotherClassInstance().getContentPane()).updateUI();
				
			} else if(source == this.getMotherClassInstance().buttonCancel) {
				
				this.getMotherClassInstance().dispose();
				
			} else if(source == this.getMotherClassInstance().buttonNext) {
				
				this.getMotherClassInstance().getContentPane().remove(this.getMotherClassInstance().pages.get(this.getMotherClassInstance().page));
				this.getMotherClassInstance().page++;
				this.getMotherClassInstance().buttonBack.setEnabled(true);
				this.getMotherClassInstance().setTitle(this.getMotherClassInstance().getName() + " (" + (this.getMotherClassInstance().page + 1) + "/" + this.getMotherClassInstance().pages.size() + ")");
				this.getMotherClassInstance().getContentPane().add(this.getMotherClassInstance().pages.get(this.getMotherClassInstance().page));
				
				if(this.getMotherClassInstance().page == this.getMotherClassInstance().pages.size() - 1) {
					
					this.getMotherClassInstance().buttonNext.setEnabled(false);
				}
				
				((WebPanel)this.getMotherClassInstance().getContentPane()).updateUI();
				
			} else if(source == this.getMotherClassInstance().buttonFinish) {
				
				this.getMotherClassInstance().dispose();
				this.getMotherClassInstance().onFinish();
			}
		}
	}
}
