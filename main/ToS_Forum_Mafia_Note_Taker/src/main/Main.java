package main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileFilter;

import main.listeners.SaveLoadButtonListener;
import main.listeners.SecondaryButtonListener;

@SuppressWarnings("serial")
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
	
	private static int Width;
	private static int Height;
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
	public static final JScrollPane notesPane = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	public static final DayButtons dayButtons = new DayButtons();
	private static GridBagLayout layout;
	private static GridBagConstraints c;
	public static final SaveLoadButtonListener listener = new SaveLoadButtonListener();
	public static final SecondaryButtonListener secondaryListener = new SecondaryButtonListener();
	public static int selectedDay = 1;
	public static boolean isDay = true;
	public static boolean numbersShown = true;
	public static boolean fileSelected = false;
	public static final String title = "Forum Mafia Note Taker V1.6";
	
	public Main(String s){
		super(s);
	}
	
	public static void main(String[] Args){
		
		if(Args.length > 0){
			System.out.print("Has Args: ");
			for(int x = 0; x < Args.length; x++){
				System.out.println(Args[x]);
			}
		} else {
			System.out.println("No Args");
		}
		frame = new Main(title + " - new");
		frame.setVisible(true);
		frame.setEnabled(false);
		playerArea.setContentType("text/html");
		playerArea.fieldName = "Player \\ Graveyard";
		roleList.setContentType("text/html");
		roleList.fieldName = "Role List";
		notes.fieldName = "Notes";
		notes.setContentType("text/html");
		notes.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
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
			save.addActionListener(listener);
			saveAs.addActionListener(listener);
			load.addActionListener(listener);
			whisper.addActionListener(secondaryListener);
			update.addActionListener(secondaryListener);
			info.addActionListener(secondaryListener);
			generalNotes.addActionListener(secondaryListener);
			options.addActionListener(secondaryListener);
			fc.setFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					return "FMNT Files";
				}
				
				@Override
				public boolean accept(File f) {
					if(f.getName().toUpperCase().endsWith(".FMNT") || f.isDirectory()){
						return true;
					}
					return false;
				}
			});
		}
		HyperlinkListener HPL = new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(Main.frame, "Error opening link", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		MainRightClickMenu.initPopup();
		dayButtons.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerNumbers.setEditable(false);
		playerNumbers.setContentType("text/html");
		playerNumbers.setText("<font face=\"arial\">1<br />2<br />3<br />"
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
		
		initLayout();
		
		frame.add(mainPanel);
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
		frame.setSize((screenWidth/2)+20, (screenHeight/2)+20);
		frame.setEnabled(true);
		UpdateHandler.check(progVers, true);
		if(Args.length == 1){
			fc.setSelectedFile(new File(Args[0]));
			listener.load(new File(Args[0]));
		}
		(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.dayButtons.initIcons();
			}
		}).run();
	}
	/**
	 * A function called to initialize the layout, is in it's own function to support being re-called to re-init
	 * the layout. This is for the daybuttons, which when it is added and removed, must be recalled for the layout
	 * to add them accordingly.
	 */
	public static void initLayout(){
		mainPanel.removeAll();
		/*Math and Layout below this line, pass at your own risk
		----------------------------------------------------------*/
		{
			int rows[] = new int[34];
			for(int x = 0; x < rows.length; x++){
				rows[x] = (Height/rows.length)/2;
			}
			int columns[] = new int[60];
			for(int x = 0; x < columns.length; x++){
				columns[x] = (Width/columns.length)/2;
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
	@SuppressWarnings("static-access")
	public static void resetConstraints(){
		c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = c.BOTH;
	}
	/**
	 * Called whenever the program has problems writing to a file.<br />
	 * TODO Why does the main class handle saving errors, it should be in the save functions.
	 * @param error The error returned.
	 */
	public static void writeError(String error){
		JOptionPane.showMessageDialog(frame, "There was an error writing to the file!\nError:"+error, "Write Error", JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * Called when the file already exsists, and a confirmation to overwrite it.<br />
	 * TODO Why does the mainfile handle this, TO THE SAVE HANDLER WITH YE
	 * @param filename The file name to overwrite
	 * @return If the user clicked OK or not.
	 */
	public static boolean notifyOverwrite(String filename){
		int value = JOptionPane.showConfirmDialog(frame, "There is already a file here, do you wish to overwrite?\nFilename:"+filename, "Overwrite?", JOptionPane.YES_NO_OPTION);
		return (value == JOptionPane.OK_OPTION);
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
			dayLabel.setText("Day "+Integer.toString(day));
		} else {
			dayLabel.setText("Night "+Integer.toString(day));
		}
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
