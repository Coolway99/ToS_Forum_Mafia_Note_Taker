package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class DayButtons{
	protected final JButton leftButton;
	protected final JButton rightButton;
	private final ArrayList<Buttons> buttonArray;
	private Timer delayTimer = new Timer(100, null);
	protected int offset = 0;
	public final JPanel panel;
	private final GridBagLayout layout;

	public DayButtons(){
		this(1);
	}

	public DayButtons(int days){
		this.panel = new JPanel();
		this.layout = new GridBagLayout();
		this.panel.setLayout(this.layout);
		this.layout.rowWeights = new double[2];
		this.layout.columnWeights = new double[10];

		this.leftButton = new JButton("«");
		if(days < 4) {
			this.leftButton.setEnabled(false);
			this.rightButton = new JButton("Add Day");
		} else {
			this.rightButton = new JButton("»");
		}
		this.buttonArray = new ArrayList<>();
		this.buttonArray.add(null);
		for(int x = 0; x < days; x++) {
			this.buttonArray.add(new Buttons(x+1, this));
		}
		this.delayTimer.setRepeats(false);
	}

	public void init(){
		this.delayTimer.start();
		this.leftButton.addActionListener(new ShiftLeft(this));
		this.rightButton.addActionListener(new ShiftRight(this));
	}

	private boolean checkTimer(){
		return this.delayTimer.isRunning();
	}

	public int getDays(){
		return this.buttonArray.size()-1;
	}

	@SuppressWarnings({"static-method", "unused"})
	@Deprecated
	public void setNumberOfDays(int days){
		throw new UnsupportedOperationException("FUNCTION IS DEPRECIATED, USE DayButtons2(int)");
	}

	public String getDayString(int day){
		return this.buttonArray.get(day).getDayString();
	}

	public String getNightString(int day){
		return this.buttonArray.get(day).getNightString();
	}

	public String getWhisperString(int day){
		return this.buttonArray.get(day).getWhisperString();
	}

	public void setDayString(String s, int day){
		this.buttonArray.get(day).setDayString(s);
	}

	public void setNightString(String s, int day){
		this.buttonArray.get(day).setNightString(s);
	}

	public void setWhisperString(String s, int day){
		this.buttonArray.get(day).setWhisperString(s);
	}

	public void addDay(){
		if( !checkTimer()) {
			if(this.buttonArray.size() >= 25) {
				JOptionPane.showMessageDialog(Main.mainPanel, "You cannot have more than 24 days");
			}
			this.buttonArray.add(new Buttons(this.buttonArray.size(), this));
			this.delayTimer.restart();
			updateGraphics();
		}
	}

	public void removeDay(int day){
		if( !checkTimer()) {
			if(this.buttonArray.size() < 3) {
				JOptionPane.showMessageDialog(Main.mainPanel,
						"Error, you cannot have less than 1 day");
			}
			int lastDay = Main.selectedDay;
			this.buttonArray.get(1).getDay().doClick();
			removeDayButtons();
			this.buttonArray.remove(day);
			if(getDays() - this.offset * 3 <= 0) {
				this.offset -= 1;
			}
			addDayButtons();
			this.buttonArray.get(lastDay > getDays() ? getDays() : lastDay).getDay().doClick();
		}
	}

	@Deprecated
	public void removeADay(){
		if(!this.delayTimer.isRunning()) {
			if(this.buttonArray.size() > 2) {
				if(JOptionPane.showConfirmDialog(Main.mainPanel, "Do you want to remove a day?",
						"Remove a day", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
					if(Main.selectedDay >= this.buttonArray.size()-1){
						if(Main.isDay){
							this.buttonArray.get(this.buttonArray.size()-2).getDay().doClick();
						} else {
							this.buttonArray.get(this.buttonArray.size()-2).getNight().doClick();
						}
					}
					removeDays();
					this.delayTimer.restart();
				}
			} else {
				JOptionPane.showMessageDialog(Main.mainPanel, "You cannot have less than 1 day");
			}
		}
	}

	@Deprecated
	private void removeDays(){
		if(this.buttonArray.size() > 2) {
			Main.mainPanel.remove(this.buttonArray.get(this.buttonArray.size()-1).getDay());
			Main.mainPanel.remove(this.buttonArray.get(this.buttonArray.size()-1).getNight());
			this.buttonArray.remove(this.buttonArray.size()-1);
			Main.mainPanel.revalidate();
			Main.frame.repaint();
		}
	}

	@Deprecated
	public void setLocation(GridBagConstraints c, int startX, int startY, int dayButtonsWidth,
			int dayButtonsHeight, int AddRemoveStartX, int AddRemoveStartY, int AddRemoveWidth,
			int AddRemoveButtonHeight){
		for(int x = 1; x < this.buttonArray.size(); x++){
			Main.resetConstraints();
			this.buttonArray.get(x).setLocation(c,
					startX + (x-1)*(dayButtonsWidth/(this.buttonArray.size()-1)),startY,
					dayButtonsWidth/(this.buttonArray.size()-1), dayButtonsHeight);
		}
		this.buttonArray.get(0).setLocation(c, AddRemoveStartX, AddRemoveStartY, AddRemoveWidth,
				AddRemoveButtonHeight);
		Main.mainPanel.revalidate();
	}

	public void drawButtons(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = 1;
		this.panel.add(this.leftButton, c);
		c.gridx = 8;
		this.panel.add(this.rightButton, c);
		removeDayButtons();
		addDayButtons();
	}

	public void addDayButtons(){
		int offset = this.offset*3;
		int numOfDays = Math.abs(offset-getDays());
		numOfDays = (numOfDays > 3)? 3 : numOfDays;
		int width = 6/numOfDays;
		for(int x = 1; x < numOfDays+1; x++) {
			this.buttonArray.get(x+offset).drawLayout(this.panel, x+1+(width-1)*(x-1),
					width);
		}
		Main.update();
	}

	public void removeDayButtons(){
		int offset = this.offset*3;
		for(int x = 1; x < 4; x++){
			if(getDays() < offset + x) {
				break;
			}
			this.buttonArray.get(offset+x).removeButtons(this.panel);
		}
		Main.update();
	}

	public JPanel getPanel(){
		return this.panel;
	}

	public void updateGraphics(){
		removeDayButtons();
		addDayButtons();
	}
}

class Buttons{
	private final int dayNumber;
	private final JButton dayButton;
	private final JButton nightButton;
	private String dayString;
	private String nightString;
	private String whisperString;

	public Buttons(int DayNumber, DayButtons parent){
		this.dayNumber = DayNumber;
		this.dayButton = new JButton("Day "+this.dayNumber);
		this.nightButton = new JButton("Night "+this.dayNumber);

		this.dayButton.setHorizontalTextPosition(SwingConstants.CENTER);
		this.nightButton.setHorizontalTextPosition(SwingConstants.CENTER);

		ButtonRightClick rightClick = new ButtonRightClick(this.dayNumber, parent);
		this.dayButton.addActionListener(new ButtonLeftClick(this.dayNumber, true));
		this.dayButton.addMouseListener(rightClick);
		this.nightButton.addActionListener(new ButtonLeftClick(this.dayNumber, false));
		this.nightButton.addMouseListener(rightClick);
	}

	public int getDayNumber(){
		return this.dayNumber;
	}

	public JButton getDay(){
		return this.dayButton;
	}

	public JButton getNight(){
		return this.nightButton;
	}

	@Deprecated
	public void initIcons(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		try {
			Image dayImage = tk.createImage(
					Main.class.getClassLoader().getResource("assets/images/dayButton.png"));
			Image nightImage = tk.createImage(
					Main.class.getClassLoader().getResource("assets/images/nightButton.png"));

			this.dayButton.setIcon(new ImageIcon(dayImage
					.getScaledInstance(this.dayButton.getWidth(), this.dayButton.getHeight(),
							Image.SCALE_FAST)));
			this.nightButton
					.setIcon(new ImageIcon(nightImage.getScaledInstance(this.nightButton.getWidth(),
							this.nightButton.getHeight(), Image.SCALE_FAST)));
		} catch(IllegalArgumentException e1){
			e1.printStackTrace();
		}
	}

	public String getDayString(){
		return this.dayString;
	}

	public String getNightString(){
		return this.nightString;
	}

	public String getWhisperString(){
		return this.whisperString;
	}

	public void setDayString(String s){
		this.dayString = s;
	}

	public void setNightString(String s){
		this.nightString = s;
	}

	public void setWhisperString(String s){
		this.whisperString = s;
	}

	@Deprecated
	public void setLocation(GridBagConstraints c, int startX, int startY, int width, int height){
		Main.resetConstraints();
		c.gridx = startX;
		c.gridy = startY;
		c.gridheight = height/2;
		c.gridwidth = width;
		Main.mainPanel.add(this.dayButton, c);
		Main.resetConstraints();
		c.gridx = startX;
		c.gridy = startY+(height/2);
		c.gridheight = height/2;
		c.gridwidth = width;
		Main.mainPanel.add(this.nightButton, c);
	}

	public void drawLayout(JPanel panel, int x, int width){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = 0;
		c.gridwidth = width;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = 1;
		panel.add(this.dayButton, c);
		c.gridy = 1;
		panel.add(this.nightButton, c);
	}

	public void removeButtons(JPanel panel){
		panel.remove(this.dayButton);
		panel.remove(this.nightButton);
	}
}

class ButtonRightClick extends JPopupMenu implements MouseListener, ActionListener{
	private static final long serialVersionUID = -8726016794747502561L;
	private final int day;
	private final DayButtons parent;

	public ButtonRightClick(int day, DayButtons parent){
		this.day = day;
		this.parent = parent;
		JMenuItem item = new JMenuItem("Remove Day "+day);
		item.addActionListener(this);
		add(item);
	}

	@Override
	public void mousePressed(MouseEvent e){
		popup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e){
		popup(e);
	}

	private void popup(MouseEvent e){
		if(!SwingUtilities.isRightMouseButton(e)) return;
		show(e.getComponent(), e.getX(), e.getY());
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(JOptionPane.showConfirmDialog(Main.mainPanel,
				"Do you want to remove day " + this.day + "?", "Remove a day",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
			this.parent.removeDay(this.day);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){/* UNUSED */}

	@Override
	public void mouseEntered(MouseEvent e){/* UNUSED */}

	@Override
	public void mouseExited(MouseEvent e){/* UNUSED */}
}

class ButtonLeftClick implements ActionListener{
	private final int day;
	private final boolean isDay;

	public ButtonLeftClick(int day, boolean isDay){
		this.day = day;
		this.isDay = isDay;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Main.setNoteString(this.day, this.isDay);
	}
}

class ShiftRight implements ActionListener{
	private final DayButtons parent;

	public ShiftRight(DayButtons parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		switch(Math.abs(this.parent.offset*3 - this.parent.getDays())){
			case 0:
			case 1:
			case 2:
				this.parent.addDay();
				break;
			case 3:
				this.parent.addDay();
				//$FALL-THROUGH$
			default:
				this.parent.removeDayButtons();
				this.parent.offset += 1;
				this.parent.leftButton.setEnabled(true);
				this.parent.addDayButtons();
				if(this.parent.getDays()-(this.parent.offset*3) < 3) {
					this.parent.rightButton.setText("Add Day");
				}
				break;
		}
	}
}

class ShiftLeft implements ActionListener{
	private final DayButtons parent;

	public ShiftLeft(DayButtons parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		this.parent.removeDayButtons();
		if( --this.parent.offset == 0) {
			this.parent.leftButton.setEnabled(false);
		}
		this.parent.addDayButtons();
		this.parent.rightButton.setText("»");
	}
}