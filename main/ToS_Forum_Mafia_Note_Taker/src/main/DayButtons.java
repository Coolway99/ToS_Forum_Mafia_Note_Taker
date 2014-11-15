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
		buttonArray = new HashMap<Integer, Buttons>();
		buttonArray.put(0, new Buttons());
		buttonArray.put(1, new Buttons(1));
		this.setDayActionListener(new PrivateAddDayActionListener(), 0);
		this.setNightActionListener(new PrivateRemoveDayActionListener(), 0);
		timer.setRepeats(false);
		timer.start();
	}
	public int getDay(){
		return buttonArray.size()-1;
	}
	public void setDay(int day){
		if(buttonArray.size()-1 < day){
			for(int x = 0; x < day+1; x++){
				while(timer.isRunning()){} //This will delay the method until timer has ran out
				if(!buttonArray.containsKey(x)){
					this.createNewDay();
				}
			}
		} else if(buttonArray.size()-1 > day){
			for(int x = buttonArray.size(); x > day+1; x--){
				while(timer.isRunning()){} //This will delay the method until timer has ran out
				if(!buttonArray.containsKey(x)){
					this.removeDays();
				}
			}
		}
	}
	
	public String getDayString(int day){return buttonArray.get(day).getDayString();}
	public String getNightString(int day){return buttonArray.get(day).getNightString();}
	public String getWhisperString(int day){return buttonArray.get(day).getWhisperString();}
	public void setDayString(String s, int day){buttonArray.get(day).setDayString(s);}
	public void setNightString(String s, int day){buttonArray.get(day).setNightString(s);}
	public void setWhisperString(String s, int day){buttonArray.get(day).setWhisperString(s);}
	
	public void setDayActionListener(ActionListener L, int day){
		buttonArray.get(day).setDayActionListener(L);
	}
	public void setNightActionListener(ActionListener L, int day){
		buttonArray.get(day).setNightActionListener(L);
	}
	public void createNewDay(){
		if(!timer.isRunning()){
			if(buttonArray.size()-1 != 20){
				buttonArray.put(buttonArray.size(), new Buttons(buttonArray.size()));
				Main.initLayout();
				Main.frame.repaint();
				timer.restart();
			} else {
				JOptionPane.showMessageDialog(Main.mainPanel, "You cannot have more than 20 days");
			}
		}
	}
	public void removeADay(){
		this.removeDays();
		if(!timer.isRunning()){
			if(buttonArray.size() > 2){
				if(JOptionPane.showConfirmDialog(Main.mainPanel, "Do you want to remove a day?", "Remove a day", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
					if(Main.selectedDay >= buttonArray.size()-1){
						if(Main.isDay){
							buttonArray.get(buttonArray.size()-2).getDay().doClick();
						} else {
							buttonArray.get(buttonArray.size()-2).getNight().doClick();
						}
					}
					this.removeDays();
					timer.restart();
				}
			} else {
				JOptionPane.showMessageDialog(Main.mainPanel, "You cannot have less than 1 day");
			}
		}
	}
	private void removeDays(){
		if(buttonArray.size() > 2){
			Main.mainPanel.remove(buttonArray.get(buttonArray.size()-1).getDay());
			Main.mainPanel.remove(buttonArray.get(buttonArray.size()-1).getNight());
			buttonArray.remove(buttonArray.size()-1);
			Main.initLayout();
			Main.mainPanel.revalidate();
			Main.frame.repaint();
		}
	}
	public void setLocation(GridBagConstraints c, int startX, int startY, int dayButtonsWidth,
			int dayButtonsHeight, int AddRemoveStartX, int AddRemoveStartY,  int AddRemoveWidth,
			int AddRemoveButtonHeight){
		for(int x = 1; x < buttonArray.size(); x++){
			Main.resetConstraints();
			buttonArray.get(x).setLocation(c, (startX + ((x-1)*(dayButtonsWidth/(buttonArray.size()-1)))),
					startY, dayButtonsWidth/(buttonArray.size()-1), dayButtonsHeight);
		}
		buttonArray.get(0).setLocation(c, AddRemoveStartX, AddRemoveStartY, AddRemoveWidth,
				AddRemoveButtonHeight);
		Main.mainPanel.revalidate();
	}
	public void initIcons(){
		for(int x = 1; x < buttonArray.size(); x++){
		//	buttonArray.get(x).initIcons();
		}
	}
}

class Buttons {
	private JButton dayButton;
	private String dayString;
	private JButton nightButton;
	private String nightString;
	private String whisperString;
	private int dayNumber;
	public Buttons(int DayNumber){
		dayNumber = DayNumber;
		dayButton = new JButton("Day "+Integer.toString(dayNumber));
		dayString = new String();
		nightButton = new JButton("Night "+Integer.toString(dayNumber));
		nightString = new String();
		whisperString = new String();
		
		dayButton.setHorizontalTextPosition(SwingConstants.CENTER);
		nightButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		this.setDayActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.saveNoteString();
				Main.setNoteString(dayNumber, true, dayString);
				Main.secondaryListener.whisperArea.setText(whisperString);
			}});
		this.setNightActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.saveNoteString();
				Main.setNoteString(dayNumber, false, nightString);
				Main.secondaryListener.whisperArea.setText(whisperString);
			}});
		
	}
	public Buttons(){
		dayNumber = 0;
		dayButton = new JButton("Add Day");
		nightButton = new JButton("Remove Day");
	}
	public int getDayNumber(){
		return dayNumber;
	}
	public JButton getDay(){
		return dayButton;
	}
	public JButton getNight(){
		return nightButton;
	}
	public void initIcons(){
	Toolkit tk = Toolkit.getDefaultToolkit();
		try {
			Image dayImage = tk.createImage(Main.class.getClassLoader().getResource("assets/images/dayButton.png"));
			Image nightImage = tk.createImage(Main.class.getClassLoader().getResource("assets/images/nightButton.png"));
			
			dayButton.setIcon(new ImageIcon(dayImage.getScaledInstance(dayButton.getWidth(), dayButton.getHeight(), Image.SCALE_FAST)));
			nightButton.setIcon(new ImageIcon(nightImage.getScaledInstance(nightButton.getWidth(), nightButton.getHeight(), Image.SCALE_FAST)));
		} catch (IllegalArgumentException e1){
			e1.printStackTrace();
		}
	}
	
	public String getDayString(){return dayString;}
	public String getNightString(){return nightString;}
	public String getWhisperString(){return whisperString;}
	public void setDayString(String s){dayString = s;}
	public void setNightString(String s){nightString = s;}
	public void setWhisperString(String s){whisperString = s;}
	
	public void setDayActionListener(ActionListener L){
		dayButton.addActionListener(L);
	}
	public void setNightActionListener(ActionListener L){
		nightButton.addActionListener(L);
	}
	public void setLocation(GridBagConstraints c, int startX, int startY, int width, int height){
		Main.resetConstraints();
		c.gridx = startX;
		c.gridy = startY;
		c.gridheight = height/2;
		c.gridwidth = width;
		Main.mainPanel.add(dayButton, c);
		Main.resetConstraints();
		c.gridx = startX;
		c.gridy = startY+(height/2);
		c.gridheight = height/2;
		c.gridwidth = width;
		Main.mainPanel.add(nightButton, c);
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