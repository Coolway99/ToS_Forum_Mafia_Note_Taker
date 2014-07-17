package assets.actionListeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import assets.Buttons1;

public class ButtonMouseListener extends MouseInputAdapter{
	private boolean button1, button2;
	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			button1 = true;
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(1);
		} else if (e.getButton() == e.BUTTON2){
			button2 = true;
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(2);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			button1 = false;
			Buttons1 button = (Buttons1)e.getSource();
			button.released(1);
		} else if (e.getButton() == e.BUTTON2){
			button2 = false;
			Buttons1 button = (Buttons1)e.getSource();
			button.released(2);
		}
	}
}
