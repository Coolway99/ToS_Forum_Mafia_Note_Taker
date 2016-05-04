package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileFilter;

import main.listeners.SaveLoadButtonListener;
import main.listeners.SecondaryButtonListener;

public class Main extends JFrame{
	/**
	 * For saving/loading. Saving uses parseList to unParseList, loading unParseList to parseList.
	 * <br /> Must always be the same size as unParseList
	 */
	public static String[] parseList  = {"\n", " ", "<", ">", "&", "\"", "'", "\t", "#", "[", "]"};
	/**@see Main.parseList */
	public static String[] unParseList = {"-!NL!-","-!S!-", "-!lfBrkt!-", "-!rtBrkt!-", "-!ampt!-",
		"-!dbQuote!-", "-!snQuote!-", "-!tab!-", "-!numb!-", "-!leftsqrt!-", "-!rightsqrt!-"};
	/**
	 * Will check this against 
	 * <a href="https://raw.githubusercontent.com/Coolway99/ToS_Forum_Mafia_Note_Taker/master/version.txt">
	 * https://raw.githubusercontent.com.../master/version.txt<a/><br />
	 * First line: Version<br />
	 * Optional Second Line: Hotfix letter
	 */
	public static final String progVers = "1.6";
	private static final int Width = 960;
	private static final int Height = 580;

	public static final JFileChooser fc = new JFileChooser();
	public static JPanel mainPanel;
	public static JFrame frame;
	public static final JButton save = new JButton("Save");
	public static final JButton load = new JButton("Load");
	public static final JButton whisper = new JButton("Whisper");
	public static final JButton update = new JButton("<html>&nbsp;Check<br />&nbsp;&nbsp;&nbsp;&nbsp;for<br />updates</html>");
	public static final JButton saveAs = new JButton("<html>Save<br />&nbsp;&nbsp;&nbsp;&nbsp;As...</html>");
	public static final JButton options = new JButton("Options");
	public static final JButton generalNotes = new JButton("<html>General<br />&nbsp;Notes</html>");
	public static final JButton info = new JButton("<html>Info\\<br />Help</html>");
	public static final JTextField dayLabel = new JTextField();
	public static final JTextField playersLabel = new JTextField();
	public static final JTextField graveyardLabel = new JTextField();
	public static final JTextField roleListLabel = new JTextField();
	public static final JTextPane playerNumbers = new JTextPane();
	public static final MainTextPane playerArea = new MainTextPane();
	public static final MainTextPane roleList = new MainTextPane();
	public static final MainTextPane notes = new MainTextPane();
	public static final JScrollPane notesPane = new JScrollPane(notes, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	public static final JScrollPane roleScrollPane = new JScrollPane(roleList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	public static DayButtons dayButtons = new DayButtons();
	private static final GridBagLayout layout = new GridBagLayout();
	private static GridBagConstraints c;
	private static final GridBagLayout jointAreaLayout = new GridBagLayout();
	private static final JPanel playerJointArea = new JPanel(jointAreaLayout);
	public static final SaveLoadButtonListener listener = new SaveLoadButtonListener();
	public static final SecondaryButtonListener secondaryListener = new SecondaryButtonListener();
	public static int selectedDay = 1;
	public static boolean isDay = true;
	public static boolean fileSelected = false;
	public static boolean numbersShown = true;
	public static int numOfPlayers = 20;
	public static String font = "arial";
	public static final String title = "Forum Mafia Note Taker Beta V2.0";

	public Main(String s){
		super(s);
	}

	public static void main(String[] args) throws Exception{
		if(args.length > 0){
			System.out.print("Has Args: ");
			for(int x = 0; x < args.length; x++){
				System.out.println(args[x]);
			}
		} else {
			System.out.println("No Args");
		}
		frame = new Main(title + " - new");
		try{
		frame.setIconImage(ImageIO.read(Main.class.getClassLoader().getResource("assets/images/logo.png")));
		} catch(IOException e){
			System.out.println("Error loading icon");
			System.out.println(e.getMessage());
		}
		frame.setVisible(true);
		frame.setEnabled(false);
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}*/
		playerArea.setContentType("text/html");
		playerArea.fieldName = "Player \\ Graveyard";
		roleList.setContentType("text/html");
		roleList.fieldName = "Role List";
		notes.fieldName = "Notes";
		notes.setContentType("text/html");
		notes.addHyperlinkListener(new HyperlinkListener(){
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch(IOException | URISyntaxException e1){
						e1.printStackTrace();
					}
				}
			}
		});
		mainPanel = new MainPanel(Integer.MAX_VALUE, Integer.MAX_VALUE);
		dayButtons.init();
		save.addActionListener(listener);
		saveAs.addActionListener(listener);
		load.addActionListener(listener);
		whisper.addActionListener(secondaryListener);
		update.addActionListener(secondaryListener);
		info.addActionListener(secondaryListener);
		options.addActionListener(secondaryListener);
		generalNotes.addActionListener(secondaryListener);
		fc.setFileFilter(new FileFilter(){
			@Override
			public String getDescription(){
				return "FMNT Files";
			}

			@Override
			public boolean accept(File f){
				if(f.getName().toUpperCase().endsWith(".FMNT") || f.isDirectory()){
					return true;
				}
				return false;
			}
		});
		
		HyperlinkListener HPL = new HyperlinkListener(){
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e){
				if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch(IOException | URISyntaxException e1){
						e1.printStackTrace();
						JOptionPane.showMessageDialog(Main.frame, "Error opening link", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		MainRightClickMenu.initPopup();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerNumbers.setEditable(false);
		playerNumbers.setContentType("text/html");
		playerNumbers.setText("<font face=\""+font+"\">1<br />2<br />3<br />"
				+ "4<br />5<br />6<br />7<br />8<br />9<br />10<br />11<br />12<br />13<br />14<br />"
				+ "15<br />16<br />17<br />18<br />19<br />20</font>");
		roleList.setEditable(false);
		roleList.addHyperlinkListener(HPL);
		playerArea.setEditable(false);
		playerArea.addHyperlinkListener(HPL);
		notes.setEditable(false);
		roleList.addMouseListener(MainRightClickMenu.mouse);
		playerArea.addMouseListener(MainRightClickMenu.mouse);
		notes.addMouseListener(MainRightClickMenu.mouse);
		mainPanel.setLayout(layout);
		playerJointArea.setBackground(new Color(0x333333));

		initLayout();

		frame.add(mainPanel);
		playersLabel.setText("Players");
		playersLabel.setEditable(false);
		playersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roleListLabel.setText("Role List");
		roleListLabel.setEditable(false);
		roleListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		graveyardLabel.setText("Players \\ Graveyard");
		graveyardLabel.setEditable(false);
		graveyardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setText("Day 1");
		dayLabel.setEditable(false);
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.setAlwaysOnTop(false); //Here for testing purposes only
		frame.setSize(Width, Height);
		frame.setEnabled(true);
		UpdateHandler.check(progVers, true);
		if(args.length == 1){
			fc.setSelectedFile(new File(args[0]));
			listener.load(new File(args[0]));
		}

		(new Thread(){
			@Override
			public void run(){
				try {
					Thread.sleep(100);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}).start();
		Preferences prefs = Preferences.userRoot();

		if( !prefs.getBoolean("hasShown", false)) {
			JOptionPane.showMessageDialog(frame,
					"Hey, thanks for using this program\nThe next version will be a complete re-write, so be sure\n to input what you want to see in the program!\n\nNote: This message will only show once");

			prefs.putBoolean("hasShown", true);
		}
	}

	/**
	 * Called for reparsing the notes, numbers, players, and rolelist.
	 */
	public static void reparseAll(){
		playerArea.setText(MainRightClickMenu.unParse(playerArea.origString));
		roleList.setText(MainRightClickMenu.unParse(roleList.origString));
		notes.setText(MainRightClickMenu.unParse(notes.origString));
		String playernum = "<font face=\""+font+"\">";
		for(int x = 1; x <= numOfPlayers; x++){
			playernum += x + "<br />";
		}
		playerNumbers.setText(playernum+"</font>");
	}
	
	/**
	 * A function called to initialize the layout, is in it's own function to support being re-called to re-init
	 * the layout. This is for the daybuttons, which when it is added and removed, must be recalled for the layout
	 * to add them accordingly.
	 */
	public static void initLayout(){
		mainPanel.removeAll();
		playerJointArea.removeAll();
		/*Math and Layout below this line, pass at your own risk
		----------------------------------------------------------*/
		{
			int[] rows = new int[34];
			for(int x = 0; x < rows.length; x++){
				rows[x] = (Height/rows.length);
			}
			int[] columns = new int[60];
			for(int x = 0; x < columns.length; x++){
				columns[x] = (Width/columns.length);
			}
			layout.rowHeights = rows;
			layout.columnWidths = columns;
			
			int y = columns[0];
			rows = new int[]{rows[0]};
			columns = new int[16];
			for(int x = 0; x < columns.length; x++){
				columns[x] = y;
			}
			jointAreaLayout.rowHeights = rows;
			jointAreaLayout.columnWidths = columns;
		}
		/*		
		layout.rowWeights = new double[34];
		layout.columnWeights = new double[60];
		
		jointAreaLayout.rowWeights = new double[1];
		jointAreaLayout.columnWeights = new double[16];
		*/
		resetConstraints();
		if(numbersShown){
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.insets = new Insets(0, 0, 0, 1);
			playerJointArea.add(playerNumbers, c);
			resetConstraints();
			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 15;
			playerJointArea.add(playerArea, c);
		} else {
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 16;
			c.insets  = new Insets(0, 0, 0, 1);
			playerJointArea.add(playerArea, c);
		}
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 20;
		c.gridwidth = 18;
		c.insets = new Insets(0, 0, 0, 1);
		mainPanel.add(new JScrollPane(playerJointArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
		resetConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 18;
		c.insets = new Insets(0, 0, 0, 1);
		mainPanel.add(graveyardLabel, c);
		resetConstraints();
		c.gridx = 0;
		c.gridy = 22;
		c.gridwidth = 27;
		c.gridheight = 12;
		mainPanel.add(dayButtons.panel, c);
		dayButtons.drawButtons();
		resetConstraints();
		c.gridwidth = (roleScrollPane.getVerticalScrollBar().isVisible() ? 10 : 9);
		c.gridheight = 20;
		c.gridy = 2;
		c.gridx = 18;
		mainPanel.add(roleScrollPane, c);
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
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 48;
		c.gridy = 22;
		mainPanel.add(whisper, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 54;
		c.gridy = 22;
		mainPanel.add(update, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 36;
		c.gridy = 28;
		mainPanel.add(saveAs, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 42;
		c.gridy = 28;
		mainPanel.add(options, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 48;
		c.gridy = 28;
		mainPanel.add(generalNotes, c);
		resetConstraints();
		c.gridwidth = 5;
		c.gridheight = 5;
		c.gridx = 54;
		c.gridy = 28;
		mainPanel.add(info, c);
	}
	
	/**
	 * A function called to reset constraints, just a convenience 
	 */
	public static void resetConstraints(){
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
	}
	
	/**
	 * Prompts the program to save whatever is in the notes pane to the day/night, this is not automatic
	 */
	public static void saveNoteString(){
		if(isDay){
			dayButtons.setDayString(notes.origString, selectedDay);
		} else {
			dayButtons.setNightString(notes.origString, selectedDay);
		}
		dayButtons.setWhisperString(secondaryListener.whisperArea.getText(), selectedDay);
	}
	
	/**
	 * Manually override the string that is associated with the day/night, for loading purposes.
	 * @param day The day number to overwrite
	 * @param isDay Is it the day or the night that we are overwriting?
	 * @param s The string to replace it with
	 */
	public static void setNoteString(int day, boolean isDay, String s){
		notes.setText(MainRightClickMenu.unParse(s));
		notes.origString = s;
		Main.isDay = isDay;
		selectedDay = day;
		if(isDay){
			dayLabel.setText("Day "+day);
		} else {
			dayLabel.setText("Night "+day);
		}
	}

	private static String unParse(String s){
		return MainRightClickMenu.unParse(s);
	}

	public static void setNoteString(int day, boolean isDay){
		String s;
		if(isDay) {
			s = dayButtons.getDayString(day);
			dayLabel.setText("Day " + day);
		} else {
			s = dayButtons.getNightString(day);
			dayLabel.setText("Night " + day);
		}
		notes.setText(unParse(s));
		notes.origString = s;
		secondaryListener.whisperArea.setText(dayButtons.getWhisperString(day));
		Main.isDay = isDay;
		selectedDay = day;
	}

	public static void update(){
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * To intercept the close button being pressed to present a warning
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			if(JOptionPane.showConfirmDialog(this, "Are you sure you want to close? Unsaved data will be lost",
					"WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)
					== JOptionPane.OK_OPTION){
				super.processWindowEvent(e);
			}
		} else {
			super.processWindowEvent(e);
		}
	}
}