package main;

import java.awt.FontMetrics;
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
	private static int Width = 800;
	private static int Hight = 600;
	private static JPanel mainPanel = new MainPanel(Width, Hight);
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
		textAreaMain.setLineWrap(true);
		textFieldNotes.setEditable(false);
		textFieldNotes.setText("Notes");
		frame.setVisible(true);
		/*Math and Layout below this line, pass at your own risk
		----------------------------------------------------------*/
		FontMetrics metrics = frame.getGraphics().getFontMetrics();
		int fontHight = metrics.getHeight();
		int longestRole = metrics.stringWidth("Town Investigative");
		int players = metrics.stringWidth("Players")+(fontHight/4);
		int roleList = metrics.stringWidth("Role List")+(fontHight/4);
		int playerListHight = fontHight * 20;
		int playerListAWidth = metrics.stringWidth("20");
		int playerListBWidth = metrics.stringWidth("ABCDEFGHIJKLMNP");
		int mainBoxWidth = (Width - ((fontHight/4)+(playerListAWidth + playerListBWidth) + (fontHight/2) + (fontHight/8) + longestRole));
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		vGroup.addGap(fontHight/4)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(textField1, fontHight, fontHight, fontHight)
								.addComponent(textField2, fontHight, fontHight, fontHight))
						.addGroup(layout.createParallelGroup()
								.addComponent(textArea1A, playerListHight, playerListHight, playerListHight)
								.addComponent(textArea1B, playerListHight, playerListHight, playerListHight)
								.addComponent(textArea2, playerListHight, playerListHight, playerListHight)))
				.addComponent(textAreaMain, playerListHight+fontHight, playerListHight+fontHight, playerListHight+fontHight))
		.addGap(250);

		hGroup.addGap(fontHight/4)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap(((playerListAWidth + playerListBWidth) - players)/2)
						.addComponent(textField1, players, players, players)
						.addGap(((playerListAWidth + playerListBWidth) - players)/2))
				.addGroup(layout.createSequentialGroup()
						.addComponent(textArea1A, playerListAWidth, playerListAWidth, playerListAWidth)
						.addComponent(textArea1B, playerListBWidth, playerListBWidth, playerListBWidth)))
		.addGap(fontHight/8)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap((longestRole - roleList)/2)
						.addComponent(textField2, roleList, roleList, roleList)
						.addGap((longestRole - roleList)/2))
				.addComponent(textArea2, longestRole, longestRole, longestRole))
		.addGap(fontHight/4)
		.addComponent(textAreaMain, mainBoxWidth, mainBoxWidth, mainBoxWidth)
		.addGap(fontHight/4)
		;
		
		layout.setVerticalGroup(vGroup);
		layout.setHorizontalGroup(hGroup);
		frame.add(mainPanel);
		frame.setResizable(false);
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