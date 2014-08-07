package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
import assets.MainTextPane;
import assets.SavingHandler;

public class Main {
	/**
	 * For saving/loading. Saving uses parseList to unParseList, loading unParseList to parseList.
	 * <br /> Must always be the same size as unParseList
	 */
	public static String[] parseList  = {"\n", " ", "<", ">", "&", "\"", "'", "\t"};
	/**@see Main.parseList */
	public static String[] unParseList = {"!NL!","!S!", "!lfBrkt!", "!rtBrkt!", "!ampt!",
		"!dbQuote!", "!snQuote!", "!tab!"};
	private static int Width;
	private static int Height;
	public static final JFileChooser fc = new JFileChooser();
	public static JPanel mainPanel;
	public static JFrame frame;
	public static final JButton save = new JButton("Save");
	public static final JButton saveAs = new JButton("<html>Save<br />&nbsp;&nbsp;&nbsp;&nbsp;As...</html>");
	public static final JButton load = new JButton("Load");
	private static final JTextField dayLabel = new JTextField();
	public static final JTextField playersLabel = new JTextField();
	public static final JTextField graveyardLabel = new JTextField();
	public static final JTextField roleListLabel = new JTextField();
	public static JTextArea playerNumbers = new JTextArea();
	public static MainTextPane playerArea = new MainTextPane();
	public static MainTextPane roleList = new MainTextPane();
	public static JTextArea notes = new JTextArea();
	public static JScrollPane notesPane = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public static DayButtons dayButtons = new DayButtons();
	public static Mouse mouse = new Mouse();
	private static GridBagLayout layout;
	private static GridBagConstraints c;
	public static int selectedDay = 1;
	public static boolean isDay = true;
	public static boolean fileSelected = false;
	public static final String title = "Forum Mafia Note Taker V1.0-Hotfix A";
	
	public static void main(String[] Args){
		frame = new JFrame(title + " - new");
		frame.setVisible(true);
		//roleList.setContentType("text/html");
		int screenWidth;
		int screenHeight;
		{
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			screenWidth = screen.width;
			screenHeight = screen.height;
		}
		Width = screenWidth;
		Height = screenHeight;
		mainPanel = new MainPanel(Width, Height);
		layout = new GridBagLayout();
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
		playerNumbers.setEditable(false);
		playerNumbers.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n");
		/*roleList.addFocusListener(MainRightClickMenu.focus);
		roleList.setEditable(false);
		playerArea.addFocusListener(MainRightClickMenu.focus);
		playerArea.setEditable(false);
		roleList.addMouseListener(MainRightClickMenu.mouse);
		playerArea.addMouseListener(MainRightClickMenu.mouse);*/
		notes.setLineWrap(true);
		mainPanel.setLayout(layout);
		
		initLayout();
		
		frame.add(mainPanel);
		//frame.setResizable(false);
		playersLabel.setText("Players");
		playersLabel.setEditable(false);
		playersLabel.setHorizontalAlignment(JTextField.CENTER);
		roleListLabel.setText("Role List");
		roleListLabel.setEditable(false);
		roleListLabel.setHorizontalAlignment(JTextField.CENTER);
		graveyardLabel.setText("Players \\ Graveyard");
		graveyardLabel.setEditable(false);
		graveyardLabel.setHorizontalAlignment(JTextField.CENTER);
		dayLabel.setText("Day 1");
		dayLabel.setEditable(false);
		dayLabel.setHorizontalAlignment(JTextField.CENTER);
		frame.setAlwaysOnTop(false); //Here for testing purposes only
		frame.setSize(screenWidth/2, screenHeight/2);
	}
	public static void initLayout(){
		/*Math and Layout below this line, pass at your own risk
		----------------------------------------------------------*/
		{
			int rows[] = new int[34];
			for(int x = 0; x < rows.length; x++){
				rows[x] = (int) ((Height/rows.length)/2);
			}
			int columns[] = new int[60];
			for(int x = 0; x < columns.length; x++){
				columns[x] = (int) ((Width/columns.length)/2);
			}
			layout.rowHeights = rows;
			layout.columnWidths = columns;
		}
		resetConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 20;
		c.insets  = new Insets(0, 0, 0, 1);
		mainPanel.add(playerNumbers, c);
		resetConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 20;
		c.gridwidth = 17;
		c.insets  = new Insets(0, 0, 0, 1);
		mainPanel.add(playerArea, c);
		resetConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 18;
		c.insets  = new Insets(0, 0, 0, 1);
		mainPanel.add(graveyardLabel, c);
		resetConstraints();
		dayButtons.setLocation(c, 0, 22, 20, 12, 20, 22, 7, 12);
		resetConstraints();
		c.gridwidth = 9;
		c.gridheight = 20;
		c.gridy = 2;
		c.gridx = 18;
		mainPanel.add(roleList, c);
		resetConstraints();
		c.gridheight = 2;
		c.gridwidth = 9;
		c.gridx = 18;
		c.gridy = 0;
		mainPanel.add(roleListLabel, c);
		resetConstraints();
		c.gridwidth = 8;
		c.gridheight = 2;
		c.gridx = 27;
		c.gridy = 0;
		mainPanel.add(dayLabel, c);
		resetConstraints();
		c.gridwidth = 25;
		c.gridheight = 22;
		c.gridx = 35;
		c.gridy = 0;
		mainPanel.add(notesPane, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 36;
		c.gridy = 22;
		mainPanel.add(save, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 42;
		c.gridy = 22;
		mainPanel.add(load, c);
		/*resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 48;
		c.gridy = 22;
		mainPanel.add(, c);*/
		/*resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 54;
		c.gridy = 22;
		mainPanel.add(, c);*/
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 36;
		c.gridy = 28;
		mainPanel.add(saveAs, c);
		/*resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 42;
		c.gridy = 28;
		mainPanel.add(, c);*/
		/*resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 48;
		c.gridy = 28;
		mainPanel.add(, c);*/
		/*resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 54;
		c.gridy = 28;
		mainPanel.add(, c);*/
	}
	@SuppressWarnings("static-access")
	public static void resetConstraints(){
		c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = c.BOTH;
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
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}