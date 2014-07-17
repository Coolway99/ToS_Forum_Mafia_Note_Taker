package main;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class Mouse extends MouseInputAdapter{
	public int x;
	public int y;
	public boolean isClicked;
	public Mouse(){
		x = 0;
		y = 0;
		isClicked = false;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		this.x = -1;
		this.y = -1;
	}
	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			isClicked = true;
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			isClicked = false;
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	public Point getPoint(){
		return new Point(x, y);
	}

}
