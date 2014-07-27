package main;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import assets.ClassOfButtons;
import assets.DayButtons;
import assets.MainRightClickMenu;
import assets.listeners.FocusListener1;

public class Main {
	private static int Width;
	private static int Hight;
	private static JPanel mainPanel;
	public static JFrame frame;
	public static JTextField playersLabel = new JTextField();
	public static JTextField graveyardLabel = new JTextField();
	public static JTextField roleListLabel = new JTextField();
	public static JTextArea playerNumbers = new JTextArea();
	public static JTextArea players = new JTextArea();
	public static JTextArea graveyard = new JTextArea();
	public static JTextArea roleList = new JTextArea();
	public static JTextArea notes = new JTextArea();
	public static DayButtons dayButton1 = new DayButtons(1);
	private static FocusListener1 focusListener = new FocusListener1();
	public static Mouse mouse = new Mouse();
	private static GroupLayout layout;
	
	public static void main(String[] Args){
		frame = new JFrame("Test Window");
		frame.setVisible(true);
		FontMetrics metrics = frame.getGraphics().getFontMetrics();
		int screenWidth;
		int screenHight;
		{
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			screenWidth = screen.width;
			screenHight = screen.height;
		}
		Width = screenWidth/2;
		Hight = screenHight/2;
		mainPanel = new MainPanel(Width, Hight);
		layout = new GroupLayout(mainPanel);
		notes.setText(Integer.toString(metrics.getFont().getSize()));
		
		MainRightClickMenu.initPopup();
		ClassOfButtons.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		mainPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(false);
		layout.setAutoCreateGaps(false);
		playerNumbers.setEditable(false);
		playerNumbers.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20");
		players.setLineWrap(true);
		players.setEditable(false);
		players.addFocusListener(focusListener);
		roleList.setLineWrap(true);
		roleList.addFocusListener(focusListener);
		roleList.setEditable(false);
		players.addMouseListener(MainRightClickMenu.mouse);
		roleList.addMouseListener(MainRightClickMenu.mouse);
		notes.setLineWrap(true);
		/*Math and Layout below this line, pass at your own risk
		----------------------------------------------------------*/
		int fontHeight = metrics.getHeight() + metrics.getDescent();
		int longestRole = metrics.stringWidth("Town Investigative");
		int longestConfirmedRole = metrics.stringWidth("(Executioner)");
		int playerListHeight = (fontHeight * 20) - (metrics.getDescent() * 20);
		int playerListAWidth = metrics.stringWidth("20");
		int playerListBWidth = metrics.stringWidth("ABCDEFGHIJKLMNP");
		int graveyardWidth = playerListBWidth + longestConfirmedRole;
		int dayButtonWidth = playerListAWidth + playerListBWidth + graveyardWidth + longestRole + (fontHeight/4) + (fontHeight/8);
		int mainBoxWidth = ((Width - ((fontHeight/4)+(playerListAWidth + playerListBWidth) + (fontHeight/2) + (fontHeight/8) + longestRole + graveyardWidth - (fontHeight/16))))/2;
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		vGroup.addGap(fontHeight/4)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(playersLabel, fontHeight, fontHeight, fontHeight)
								.addComponent(graveyardLabel, fontHeight, fontHeight, fontHeight)
								.addComponent(roleListLabel, fontHeight, fontHeight, fontHeight))
						.addGroup(layout.createParallelGroup()
								.addComponent(playerNumbers, playerListHeight, playerListHeight, playerListHeight)
								.addComponent(players, playerListHeight, playerListHeight, playerListHeight)
								.addComponent(graveyard, playerListHeight, playerListHeight, playerListHeight)
								.addComponent(roleList, playerListHeight, playerListHeight, playerListHeight)))
				.addComponent(notes, playerListHeight+fontHeight, playerListHeight+fontHeight, playerListHeight+fontHeight))
		.addGap(fontHeight/4)
		.addGroup(dayButton1.setVerticalLocation(layout.createSequentialGroup(), 10*fontHeight))
		;

		hGroup.addGap(fontHeight/4)
		.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(graveyardLabel, graveyardWidth, graveyardWidth, graveyardWidth)
								.addComponent(graveyard, graveyardWidth, graveyardWidth, graveyardWidth))
						.addGap(fontHeight/16)
						.addGroup(layout.createParallelGroup()
								.addComponent(playersLabel, playerListBWidth+playerListAWidth, playerListBWidth+playerListAWidth, playerListBWidth+playerListAWidth)
								.addGroup(layout.createSequentialGroup()
										.addComponent(playerNumbers, playerListAWidth, playerListAWidth, playerListAWidth)
										.addComponent(players, playerListBWidth, playerListBWidth, playerListBWidth)))
				
						.addGap(fontHeight/8)
						.addGroup(layout.createParallelGroup()
								.addComponent(roleListLabel, longestRole, longestRole, longestRole)
								.addComponent(roleList, longestRole, longestRole, longestRole)))
		.addGroup(dayButton1.setHorizontalLocation(layout.createSequentialGroup(), layout.createParallelGroup(), dayButtonWidth)))
		.addGap(fontHeight/4)
		.addGap(mainBoxWidth)
		.addComponent(notes, mainBoxWidth, mainBoxWidth, mainBoxWidth)
		.addGap(fontHeight/4)
		;
		
		layout.setVerticalGroup(vGroup);
		layout.setHorizontalGroup(hGroup);
		frame.add(mainPanel);
		frame.setResizable(false);
		playersLabel.setText("Players");
		playersLabel.setEditable(false);
		playersLabel.setHorizontalAlignment(JTextField.CENTER);
		roleListLabel.setText("Role List");
		roleListLabel.setEditable(false);
		roleListLabel.setHorizontalAlignment(JTextField.CENTER);
		graveyardLabel.setText("Graveyard");
		graveyardLabel.setEditable(false);
		graveyardLabel.setHorizontalAlignment(JTextField.CENTER);
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