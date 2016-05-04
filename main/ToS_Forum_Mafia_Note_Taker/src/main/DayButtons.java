package main;

import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class DayButtons{
	private HashMap<Integer, Buttons> buttonArray;
	private Timer timer = new Timer(50, new TimerActionListener());
	public void init(){
		this.buttonArray = new HashMap<>();
		this.buttonArray.put(0, new Buttons());
		this.buttonArray.put(1, new Buttons(1));
		this.setDayActionListener(new PrivateAddDayActionListener(), 0);
		this.setNightActionListener(new PrivateRemoveDayActionListener(), 0);
		this.timer.setRepeats(false);
		this.timer.start();
	}
	public int getDay(){
		return this.buttonArray.size()-1;
	}
	public void setDay(int day){
		this.buttonArray.get(1).getDay().doClick();
		if(this.buttonArray.size()-1 < day){
			for(int x = 0; x < day+1; x++){
				while(this.timer.isRunning()){/*This will delay the method until timer has ran out*/}
				if(!this.buttonArray.containsKey(x)){
					this.createNewDay();
				}
			}
		} else if(this.buttonArray.size()-1 > day){
			for(int x = this.buttonArray.size(); x > day+1; x--){
				while(this.timer.isRunning()){/*This will delay the method until timer has ran out*/}
				if(!this.buttonArray.containsKey(x)){
					this.removeDays();
				}
			}
		}
	}
	
	public String getDayString(int day){return this.buttonArray.get(day).getDayString();}
	public String getNightString(int day){return this.buttonArray.get(day).getNightString();}
	public String getWhisperString(int day){return this.buttonArray.get(day).getWhisperString();}
	public void setDayString(String s, int day){this.buttonArray.get(day).setDayString(s);}
	public void setNightString(String s, int day){this.buttonArray.get(day).setNightString(s);}
	public void setWhisperString(String s, int day){this.buttonArray.get(day).setWhisperString(s);}
	
	public void setDayActionListener(ActionListener L, int day){
		this.buttonArray.get(day).setDayActionListener(L);
	}
	public void setNightActionListener(ActionListener L, int day){
		this.buttonArray.get(day).setNightActionListener(L);
	}
	public void createNewDay(){
		if(!this.timer.isRunning()){
			if(this.buttonArray.size()-1 != 20){
				this.buttonArray.put(this.buttonArray.size(), new Buttons(this.buttonArray.size()));
				Main.initLayout();
				Main.frame.repaint();
				this.timer.restart();
			} else {
				JOptionPane.showMessageDialog(Main.mainPanel, "You cannot have more than 20 days");
			}
		}
	}
	public void removeADay(){
		if(!this.timer.isRunning()){
			if(this.buttonArray.size() > 2){
				if(JOptionPane.showConfirmDialog(Main.mainPanel, "Do you want to remove a day?",
						"Remove a day", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
					if(Main.selectedDay >= this.buttonArray.size()-1){
						if(Main.isDay){
							this.buttonArray.get(this.buttonArray.size()-2).getDay().doClick();
						} else {
							this.buttonArray.get(this.buttonArray.size()-2).getNight().doClick();
						}
					}
					this.removeDays();
					this.timer.restart();
				}
			} else {
				JOptionPane.showMessageDialog(Main.mainPanel, "You cannot have less than 1 day");
			}
		}
	}
	private void removeDays(){
		if(this.buttonArray.size() > 2){
			Main.mainPanel.remove(this.buttonArray.get(this.buttonArray.size()-1).getDay());
			Main.mainPanel.remove(this.buttonArray.get(this.buttonArray.size()-1).getNight());
			this.buttonArray.remove(this.buttonArray.size()-1);
			Main.mainPanel.revalidate();
			Main.frame.repaint();
		}
	}
	public void setLocation(GridBagConstraints c, int startX, int startY, int dayButtonsWidth,
			int dayButtonsHeight, int AddRemoveStartX, int AddRemoveStartY,  int AddRemoveWidth,
			int AddRemoveButtonHeight){
		for(int x = 1; x < this.buttonArray.size(); x++){
			Main.resetConstraints();
			this.buttonArray.get(x).setLocation(c, (startX + ((x-1)*(dayButtonsWidth/(this.buttonArray.size()-1)))),
					startY, dayButtonsWidth/(this.buttonArray.size()-1), dayButtonsHeight);
		}
		this.buttonArray.get(0).setLocation(c, AddRemoveStartX, AddRemoveStartY, AddRemoveWidth,
				AddRemoveButtonHeight);
		Main.mainPanel.revalidate();
	}
	public void initIcons(){
		for(int x = 1; x < this.buttonArray.size(); x++){
		//	buttonArray.get(x).initIcons();
		}
	}
}

class Buttons {
	private JButton dayButton;
	protected String dayString;
	private JButton nightButton;
	protected String nightString;
	protected String whisperString;
	protected int dayNumber;
	public Buttons(int DayNumber){
		this.dayNumber = DayNumber;
		this.dayButton = new JButton("Day "+Integer.toString(this.dayNumber));
		this.dayString = new String();
		this.nightButton = new JButton("Night "+Integer.toString(this.dayNumber));
		this.nightString = new String();
		this.whisperString = new String();
		
		this.dayButton.setHorizontalTextPosition(SwingConstants.CENTER);
		this.nightButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		this.setDayActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.saveNoteString();
				Main.setNoteString(Buttons.this.dayNumber, true, Buttons.this.dayString);
				Main.secondaryListener.whisperArea.setText(Buttons.this.whisperString);
			}});
		this.setNightActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.saveNoteString();
				Main.setNoteString(Buttons.this.dayNumber, false, Buttons.this.nightString);
				Main.secondaryListener.whisperArea.setText(Buttons.this.whisperString);
			}});
		
	}
	public Buttons(){
		this.dayNumber = 0;
		this.dayButton = new JButton("Add Day");
		this.nightButton = new JButton("Remove Day");
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
	public void initIcons(){
	Toolkit tk = Toolkit.getDefaultToolkit();
		try {
			Image dayImage = tk.createImage(Main.class.getClassLoader().getResource("assets/images/dayButton.png"));
			Image nightImage = tk.createImage(Main.class.getClassLoader().getResource("assets/images/nightButton.png"));
			
			this.dayButton.setIcon(new ImageIcon(dayImage.getScaledInstance(this.dayButton.getWidth(), this.dayButton.getHeight(), Image.SCALE_FAST)));
			this.nightButton.setIcon(new ImageIcon(nightImage.getScaledInstance(this.nightButton.getWidth(), this.nightButton.getHeight(), Image.SCALE_FAST)));
		} catch (IllegalArgumentException e1){
			e1.printStackTrace();
		}
	}
	
	public String getDayString(){return this.dayString;}
	public String getNightString(){return this.nightString;}
	public String getWhisperString(){return this.whisperString;}
	public void setDayString(String s){this.dayString = s;}
	public void setNightString(String s){this.nightString = s;}
	public void setWhisperString(String s){this.whisperString = s;}
	
	public void setDayActionListener(ActionListener L){
		this.dayButton.addActionListener(L);
	}
	public void setNightActionListener(ActionListener L){
		this.nightButton.addActionListener(L);
	}
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
}
class PrivateAddDayActionListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		Main.dayButtons.createNewDay();
	}
}
class PrivateRemoveDayActionListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		Main.dayButtons.removeADay();
	}
}
class TimerActionListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			Main.frame.revalidate();
			Main.initLayout();
			Main.dayButtons.initIcons();
		} catch(Exception err){
			System.out.println(err.getMessage() + " In the timer thread");
		}
	}
}