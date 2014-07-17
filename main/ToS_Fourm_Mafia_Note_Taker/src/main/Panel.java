package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	private HashMap<Integer, Component> componentArray = new HashMap<Integer, Component>();
	public Panel(){
		super();
	}
	public void update(){
		
	}
	public void input(Component c){
		if(componentArray.isEmpty()){
			componentArray.put(0, c);
		} else {
			componentArray.put((componentArray.values().toArray(new Component[0]).length), c);
		}
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
	public void pressed(){
		System.out.println("yay");
	}
}
