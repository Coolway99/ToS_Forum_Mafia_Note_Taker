package main;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	private static boolean[] keys = new boolean[65585];
	private static JPanel mainPanel = new MainPanel();
	private static JFrame frame;
	private static JTextField textField = new JTextField();
	public static Mouse mouse = new Mouse();
	private static GroupLayout layout = new GroupLayout(mainPanel);
	
	public static void main(String[] Args) throws InterruptedException{
		frame = new JFrame("Test Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		mainPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(false);
		layout.setAutoCreateGaps(false);
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGap(200).addComponent(textField, 25, 25, 25).addGap(375);
		hGroup.addGap(300).addComponent(textField, 200, 200, 200).addGap(300);
		layout.setVerticalGroup(vGroup);
		layout.setHorizontalGroup(hGroup);
		frame.add(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		textField.setText("I am a simple uneditable testbox");
		textField.setEditable(true);
		KeyEventDispatcher keyEventA = new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent e){
				int k = e.getID();
				if(k == KeyEvent.KEY_PRESSED){
					keyPressed(e);
				} else if(k == KeyEvent.KEY_RELEASED){
					keyReleased(e);
				}
				return true;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventA);
		while(true){
			frame.repaint();
			Thread.sleep(10);
		}
	}
	private static void keyPressed(KeyEvent key){
		keys[key.getKeyCode()] = true;
	}
	private static void keyReleased(KeyEvent key){
		keys[key.getKeyCode()] = false;
	}	
	public static boolean isMouseOver(Point xy, Point end){
		Point a = mouse.getPoint();
		if((a.x > xy.x && a.y > xy.y) && (a.x < end.x && a.y < end.y)){
			return true;
		} else {
			return false;
		}
	}
}
