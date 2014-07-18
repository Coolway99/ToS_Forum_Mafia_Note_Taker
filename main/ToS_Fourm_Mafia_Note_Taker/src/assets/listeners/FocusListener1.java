package assets.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;

public class FocusListener1 implements FocusListener{

	@Override
	public void focusGained(FocusEvent arg0) {
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		((JTextArea) arg0.getSource()).setEditable(false);
	}
	
}
