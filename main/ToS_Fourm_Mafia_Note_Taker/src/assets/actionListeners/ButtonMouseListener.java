package assets.actionListeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import assets.Buttons1;

public class ButtonMouseListener extends MouseInputAdapter{
	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(1);
		} else if (e.getButton() == e.BUTTON2){
			Buttons1 button = (Buttons1)e.getSource();
			button.pressed(2);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			Buttons1 button = (Buttons1)e.getSource();
			button.released(1);
		} else if (e.getButton() == e.BUTTON2){
			Buttons1 button = (Buttons1)e.getSource();
			button.released(2);
		}
	}
}
