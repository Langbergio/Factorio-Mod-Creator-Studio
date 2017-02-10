package de.ralleytn.fmcs.ui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.JTextComponent;

import com.alee.laf.button.WebButton;

import de.ralleytn.fmcs.AbstractAdapter;
import de.ralleytn.fmcs.Icons;

public class FMCSButton extends WebButton {

	private static final long serialVersionUID = 6226428111060902831L;
	
	private List<JTextComponent> boundComponents;
	private Adapter adapter;
	
	public FMCSButton(String text, String iconName) {
		
		super(text, Icons.get(iconName));
		this.boundComponents = new ArrayList<>();
		this.adapter = new Adapter(this);
	}
	
	public FMCSButton(String text) {
		
		this(text, null);
	}
	
	public void unbindAll() {
		
		for(JTextComponent component : this.boundComponents) {
			
			component.removeKeyListener(this.adapter);
		}
		
		this.boundComponents.clear();
	}
	
	public void bindTo(JTextComponent... components) {
		
		this.unbindAll();
		
		for(JTextComponent component : components) {

			component.addKeyListener(this.adapter);
			this.boundComponents.add(component);
		}
	}
	
	public List<JTextComponent> getBoundComponents() {
		
		return this.boundComponents;
	}
	
	private final class Adapter extends AbstractAdapter<FMCSButton> {

		public Adapter(FMCSButton motherClassInstance) {
			
			super(motherClassInstance);
		}
		
		@Override
		public void keyReleased(KeyEvent event) {
			
			this._checkComponents();
		}
		
		@Override
		public void keyPressed(KeyEvent event) {
			
			this._checkComponents();
		}
		
		@Override
		public void keyTyped(KeyEvent event) {
			
			this._checkComponents();
		}
		
		private void _checkComponents() {
			
			boolean missing = false;
			
			for(JTextComponent comp : this.getMotherClassInstance().boundComponents) {
				
				if(comp.getText().trim().isEmpty()) {
					
					missing = true;
					break;
				}
			}

			this.getMotherClassInstance().setEnabled(!missing);
		}
	}
}
