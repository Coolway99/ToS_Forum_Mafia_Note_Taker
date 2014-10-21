package main.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import main.Buttons1;

public class ButtonMouseListener extends MouseInputAdapter{
	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(1);
		} else if(e.getButton() == e.BUTTON2){
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(2);
		} else if(e.getButton() == e.BUTTON3){
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(3);
		}
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			Buttons1 button = (Buttons1)e.getSource();
			button.released(1);
		} else if(e.getButton() == e.BUTTON2){
			Buttons1 button = (Buttons1)e.getSource();
			button.released(2);
		} else if(e.getButton() == e.BUTTON3){
			Buttons1 button = (Buttons1)e.getSource();
			button.released(3);
		}
	}
}
