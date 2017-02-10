package de.ralleytn.fmcs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Super class of all adapters for the user interface. Only reason for this class to exist is to make
 * the adapters less ugly with all the unused but implemented methods and direct mother
 * class instance calls.
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 * @param <T> Class which wraps an Adapter
 */
public abstract class AbstractAdapter<T> implements KeyListener,
												 MouseListener,
												 MouseWheelListener,
												 MouseMotionListener,
												 ChangeListener,
												 WindowListener,
												 WindowFocusListener,
												 FocusListener,
												 ListSelectionListener,
												 ActionListener {
	
	private final T motherClassInstance;
	
	/**
	 * @param motherClassInstance Instance of the mother class
	 * @since 0.1.0
	 */
	public AbstractAdapter(T motherClassInstance) {
		
		this.motherClassInstance = motherClassInstance;
	}
	
	/**
	 * @return Instance of the mother class
	 * @since 0.1.0
	 */
	public final T getMotherClassInstance() {
		
		return this.motherClassInstance;
	}

	@Override public void actionPerformed(ActionEvent event) {}
	@Override public void valueChanged(ListSelectionEvent event) {}
	@Override public void focusGained(FocusEvent event) {}
	@Override public void focusLost(FocusEvent event) {}
	@Override public void windowGainedFocus(WindowEvent event) {}
	@Override public void windowLostFocus(WindowEvent event) {}
	@Override public void windowActivated(WindowEvent event) {}
	@Override public void windowClosed(WindowEvent event) {}
	@Override public void windowClosing(WindowEvent event) {}
	@Override public void windowDeactivated(WindowEvent event) {}
	@Override public void windowDeiconified(WindowEvent event) {}
	@Override public void windowIconified(WindowEvent event) {}
	@Override public void windowOpened(WindowEvent event) {}
	@Override public void stateChanged(ChangeEvent event) {}
	@Override public void mouseDragged(MouseEvent event) {}
	@Override public void mouseMoved(MouseEvent event) {}
	@Override public void mouseWheelMoved(MouseWheelEvent event) {}
	@Override public void mouseClicked(MouseEvent event) {}
	@Override public void mouseEntered(MouseEvent event) {}
	@Override public void mouseExited(MouseEvent event) {}
	@Override public void mousePressed(MouseEvent event) {}
	@Override public void mouseReleased(MouseEvent event) {}
	@Override public void keyPressed(KeyEvent event) {}
	@Override public void keyReleased(KeyEvent event) {}
	@Override public void keyTyped(KeyEvent event) {}
}
