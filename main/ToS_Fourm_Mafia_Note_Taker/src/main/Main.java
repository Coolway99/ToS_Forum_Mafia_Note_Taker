package main;

import java.awt.Point;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import assets.ClassOfButtons;
import assets.MainRightClickMenu;
import assets.listeners.FocusListener1;

public class Main {
	private static JPanel mainPanel = new MainPanel();
	public static JFrame frame;
	public static JTextField textField1 = new JTextField();
	public static JTextField textField2 = new JTextField();
	public static JTextField textFieldNotes = new JTextField();
	public static JTextArea textArea1A = new JTextArea();
	public static JTextArea textArea1B = new JTextArea();
	public static JTextArea textArea2 = new JTextArea();
	public static JTextArea textAreaMain = new JTextArea();
	private static FocusListener1 focusListener = new FocusListener1();
	public static Mouse mouse = new Mouse();
	private static GroupLayout layout = new GroupLayout(mainPanel);
	
	public static void main(String[] Args) throws InterruptedException{
		MainRightClickMenu.initPopup();
		ClassOfButtons.init();
		frame = new JFrame("Test Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		mainPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(false);
		layout.setAutoCreateGaps(false);
		textArea1A.setEditable(false);
		textArea1A.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20");
		textArea1B.setLineWrap(true);
		textArea1B.setEditable(false);
		textArea1B.addFocusListener(focusListener);
		textArea2.setLineWrap(true);
		textArea2.addFocusListener(focusListener);
		textArea2.setEditable(false);
		textArea1B.addMouseListener(MainRightClickMenu.mouse);
		textArea2.addMouseListener(MainRightClickMenu.mouse);
		textFieldNotes.setEditable(false);
		textFieldNotes.setText("Notes");
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		vGroup.addGap(10)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(textField1, 20, 20, 20)
								.addComponent(textField2, 20, 20, 20))
						.addGroup(layout.createParallelGroup()
								.addComponent(textArea1A, 320, 320, 320)
								.addComponent(textArea1B, 320, 320, 320)
								.addComponent(textArea2, 320, 320, 320)))
				.addComponent(textAreaMain, 340, 340, 340))
		.addGap(250);

		hGroup.addGap(10)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap(53)
						.addComponent(textField1, 45, 45, 45)
						.addGap(52))
				.addGroup(layout.createSequentialGroup()
						.addComponent(textArea1A, 15, 15, 15)
						.addComponent(textArea1B, 135, 135, 135)))
		.addGap(5)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap(6)
						.addComponent(textField2, 54, 54, 54)
						.addGap(6))
				.addComponent(textArea2, 66, 66, 66))
		.addGap(10)
		.addComponent(textAreaMain, 549, 549, 549)
		.addGap(10)
		;
		
		layout.setVerticalGroup(vGroup);
		layout.setHorizontalGroup(hGroup);
		frame.add(mainPanel);
		frame.setResizable(false);
		frame.setVisible(true);
		textField1.setText("Players");
		textField1.setEditable(false);
		textField2.setText("Role List");
		textField2.setEditable(false);
		frame.setAlwaysOnTop(false); //Here for testing purposes only
		frame.pack();
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