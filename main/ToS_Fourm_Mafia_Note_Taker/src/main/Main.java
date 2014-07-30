package main;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import assets.DayButtons;
import assets.LoadingHandler;
import assets.MainRightClickMenu;
import assets.SavingHandler;
import assets.listeners.FocusListener1;

public class Main {
	private static int Width;
	private static int Hight;
	public static final JFileChooser fc = new JFileChooser();
	private static JPanel mainPanel;
	public static JFrame frame;
	public static final JButton save = new JButton("Save");
	public static final JButton saveAs = new JButton("<html>Save<br />&nbsp;&nbsp;&nbsp;&nbsp;As...</html>");
	public static final JButton load = new JButton("Load");
	private static final JTextField dayLabel = new JTextField();
	public static final JTextField playersLabel = new JTextField();
	public static final JTextField graveyardLabel = new JTextField();
	public static final JTextField roleListLabel = new JTextField();
	public static JTextArea playerNumbers = new JTextArea();
	public static JTextArea players = new JTextArea();
	public static JTextArea graveyard = new JTextArea();
	public static JTextArea roleList = new JTextArea();
	public static JTextArea notes = new JTextArea();
	public static JScrollPane notesPane = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public static DayButtons dayButtons = new DayButtons();
	private static FocusListener1 focusListener = new FocusListener1();
	private static FontMetrics metrics;
	public static Mouse mouse = new Mouse();
	private static GroupLayout layout;
	public static int selectedDay = 1;
	public static boolean isDay = true;
	public static boolean fileSelected = false;
	public static final String title = "Forum Mafia Note Taker Alpha V-In Dev-";
	
	public static void main(String[] Args){
		frame = new JFrame(title + " - new");
		frame.setVisible(true);
		metrics = frame.getGraphics().getFontMetrics();
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
		{
			SaveLoadButtonActionListener listener = new SaveLoadButtonActionListener();
			save.addActionListener(listener);
			saveAs.addActionListener(listener);
			load.addActionListener(listener);
			fc.setFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					return "FMNT Files";
				}
				
				@Override
				public boolean accept(File f) {
					if(f.getName().toUpperCase().endsWith(".FMNT") || f.isDirectory()){
						return true;
					} else {
						return false;
					}
				}
			});
		}
		MainRightClickMenu.initPopup();
		dayButtons.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		mainPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(false);
		layout.setAutoCreateGaps(false);
		playerNumbers.setEditable(false);
		playerNumbers.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20");
		roleList.setLineWrap(true);
		roleList.addFocusListener(focusListener);
		roleList.setEditable(false);
		graveyard.setLineWrap(true);
		graveyard.addFocusListener(focusListener);
		graveyard.setEditable(false);
		roleList.addMouseListener(MainRightClickMenu.mouse);
		graveyard.addMouseListener(MainRightClickMenu.mouse);
		notes.setLineWrap(true);
		
		initLayout();
		
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
		dayLabel.setText("Day 1");
		dayLabel.setEditable(false);
		dayLabel.setHorizontalAlignment(JTextField.CENTER);
		frame.setAlwaysOnTop(false); //Here for testing purposes only
		frame.pack();
	}
	public static void initLayout(){
		/*Math and Layout below this line, pass at your own risk
		----------------------------------------------------------*/
		int fontHeight = metrics.getHeight() + metrics.getDescent();
		int longestRole = metrics.stringWidth("Town Investigative");
		int longestConfirmedRole = metrics.stringWidth("(Executioner)");
		int playerListHeight = (fontHeight * 20) - (metrics.getDescent() * 20);
		int playerListAWidth = metrics.stringWidth("20");
		int playerListBWidth = metrics.stringWidth("ABCDEFGHIJKLMNP")-1;
		int graveyardWidth = playerListBWidth + longestConfirmedRole;
		int dayButtonAWidth = playerListAWidth + playerListBWidth + graveyardWidth + (fontHeight/8);
		int dayButtonBWidth = longestRole + (fontHeight/4);
		int mainBoxWidth = ((Width - ((fontHeight/4)+(playerListAWidth + playerListBWidth) + (fontHeight/2) + (fontHeight/8) + longestRole + graveyardWidth - (fontHeight/16))))/2;
		int additionalButtonWidth = (mainBoxWidth/3)-(fontHeight/2);
		int additionalButtonHeight = (5*fontHeight) - (fontHeight/2);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		vGroup.addGap(fontHeight/4)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(playersLabel, fontHeight, fontHeight, fontHeight)
										.addComponent(graveyardLabel, fontHeight, fontHeight, fontHeight)
										.addComponent(roleListLabel, fontHeight, fontHeight, fontHeight)
										.addComponent(dayLabel, fontHeight, fontHeight, fontHeight))
								.addGroup(layout.createParallelGroup()
										.addComponent(playerNumbers, playerListHeight, playerListHeight, playerListHeight)
										.addComponent(players, playerListHeight, playerListHeight, playerListHeight)
										.addComponent(graveyard, playerListHeight, playerListHeight, playerListHeight)
										.addComponent(roleList, playerListHeight, playerListHeight, playerListHeight)))
						.addComponent(notesPane, playerListHeight+fontHeight, playerListHeight+fontHeight, playerListHeight+fontHeight))
				.addGap(fontHeight/4)
				.addGroup(layout.createParallelGroup()
						.addGroup(dayButtons.setVerticalLocation(layout, 10*fontHeight))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(save, additionalButtonHeight, additionalButtonHeight, additionalButtonHeight)
										.addComponent(load, additionalButtonHeight, additionalButtonHeight, additionalButtonHeight))
								.addGap(fontHeight/2)
								.addGroup(layout.createParallelGroup()
										.addComponent(saveAs, additionalButtonHeight, additionalButtonHeight, additionalButtonHeight))))
		;

		hGroup.addGap(fontHeight/4)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(graveyardLabel, graveyardWidth, graveyardWidth, graveyardWidth)
										.addComponent(graveyard, graveyardWidth, graveyardWidth, graveyardWidth))
								.addGap(fontHeight/16)
								.addGroup(layout.createParallelGroup()
										.addComponent(playersLabel, playerListBWidth+playerListAWidth+1, playerListBWidth+playerListAWidth+1, playerListBWidth+playerListAWidth+1)
										.addGroup(layout.createSequentialGroup()
												.addComponent(playerNumbers, playerListAWidth, playerListAWidth, playerListAWidth)
												.addGap(1)
												.addComponent(players, playerListBWidth, playerListBWidth, playerListBWidth)))
						
								.addGap(fontHeight/8)
								.addGroup(layout.createParallelGroup()
										.addComponent(roleListLabel, longestRole, longestRole, longestRole)
										.addComponent(roleList, longestRole, longestRole, longestRole)))
						.addGroup(dayButtons.setHorizontalLocation(layout, dayButtonAWidth, dayButtonBWidth)))
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(dayLabel, (mainBoxWidth/2)-(fontHeight/4), (mainBoxWidth/2)-(fontHeight/4), (mainBoxWidth/2)-(fontHeight/4)))
								.addGap(fontHeight/4)
						.addGap(mainBoxWidth/2))
				.addGroup(layout.createParallelGroup()
						.addComponent(notesPane, mainBoxWidth+(mainBoxWidth/2), mainBoxWidth+(mainBoxWidth/2), mainBoxWidth+(mainBoxWidth/2))
						.addGroup(layout.createSequentialGroup()
								.addGap(fontHeight/4)
								.addGroup(layout.createParallelGroup()
										.addComponent(saveAs, additionalButtonWidth, additionalButtonWidth, additionalButtonWidth)
										.addComponent(save, additionalButtonWidth, additionalButtonWidth, additionalButtonWidth))
								.addGap(fontHeight/2)
								.addComponent(load, additionalButtonWidth, additionalButtonWidth, additionalButtonWidth)
								.addGap(fontHeight/2)))
				.addGap(fontHeight/4)
		;
		
		layout.setVerticalGroup(vGroup);
		layout.setHorizontalGroup(hGroup);
	}
	public static void writeError(){
		JOptionPane.showMessageDialog(frame, "There was an error writing to the file!", "Write Error", JOptionPane.ERROR_MESSAGE);
	}
	public static boolean notifyOverwrite(String filename){
		JOptionPane.showConfirmDialog(frame, "There is already a file here, do you wish to overwrite?", "Overwrite?", JOptionPane.YES_NO_OPTION);
		return true;
	}
	public static void saveNoteString(){
		if(isDay){
			dayButtons.setDayString(notes.getText(), selectedDay);
		} else {
			dayButtons.setNightString(notes.getText(), selectedDay);
		}
	}
	public static void setNoteString(int day, boolean isDay, String s){
		notes.setText(s);
		Main.isDay = isDay;
		selectedDay = day;
		if(isDay){
			dayLabel.setText("Day "+Integer.toString(day));
		} else {
			dayLabel.setText("Night "+Integer.toString(day));
		}
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
class SaveLoadButtonActionListener implements ActionListener {
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Main.saveAs || e.getSource() == Main.save){
			int value;
			if(!(e.getSource() == Main.save && Main.fileSelected)) {value = Main.fc.showSaveDialog(Main.fc);}else{value = Main.fc.APPROVE_OPTION;}
			if(value == Main.fc.APPROVE_OPTION){
				File file = Main.fc.getSelectedFile();
				if(!file.getName().endsWith(".FMNT")){
					file = new File(file.toString()+".FMNT");
				}
				SavingHandler.save(file);
				Main.fileSelected = true;
			}
		}
		if(e.getSource() == Main.load){
			int value = Main.fc.showOpenDialog(Main.fc);
			if(value == Main.fc.APPROVE_OPTION){
				try {
					XMLReader xr = XMLReaderFactory.createXMLReader();
					LoadingHandler handler = new LoadingHandler();
					xr.setContentHandler(handler);
					xr.parse(new InputSource(new FileReader(Main.fc.getSelectedFile())));
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}