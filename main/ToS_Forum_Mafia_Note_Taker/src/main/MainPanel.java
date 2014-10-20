package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{
	private int Width;
	private int Hight;
	public MainPanel(int Width, int Hight){
		super();
		this.Width = Width;
		this.Hight = Hight;
	}
	public void update(){
		
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.fillRect(0, 0, Width, Hight);
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(Width, Hight);	
	}
	@Override
	public Dimension getMaximumSize(){
		return new Dimension(Width, Hight);
	}
}
