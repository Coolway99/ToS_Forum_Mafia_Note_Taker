package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{
	public MainPanel(){
		super();
	}
	public void update(){
		
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.fillRect(0, 0, 850, 650);
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800, 600);	
	}
	@Override
	public Dimension getMaximumSize(){
		return new Dimension(800, 600);
	}
}
