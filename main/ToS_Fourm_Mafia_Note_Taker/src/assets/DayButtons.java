package assets;

import interfaces.SaveLoadListener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.Timer;

import main.Main;

@SuppressWarnings("serial")
public class DayButtons extends Component{
	private HashMap<Integer, Buttons> buttonArray = new HashMap<Integer, Buttons>();
	private Timer timer = new Timer(100, null);
	public void init(){
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
		for(int x = 0; x < day+1; x++){
			while(timer.isRunning()){} //This will delay the call until timer has ran out
			if(!buttonArray.containsKey(x)){
				this.createNewDay();
			}
		}
	}
	public void setDayActionListener(ActionListener L, int day){
		buttonArray.get(day).setDayActionListener(L);
	}
	public void setNightActionListener(ActionListener L, int day){
		buttonArray.get(day).setNightActionListener(L);
	}
	public void createNewDay(){
		if(!timer.isRunning()){
			buttonArray.put(buttonArray.size(), new Buttons(buttonArray.size()));
			Main.initLayout();
			Main.frame.repaint();
			timer.restart();
		}
	}
	public void removeADay(){
		if(!timer.isRunning()){
			if(buttonArray.size() > 2){
				buttonArray.remove(buttonArray.size()-1);
				Main.initLayout();
				Main.frame.repaint();
				timer.restart();
			}
		}
	}
	public SequentialGroup setVerticalLocation(GroupLayout layout, int size){
		SequentialGroup group = layout.createSequentialGroup();
		ParallelGroup pGroup = layout.createParallelGroup();
		for(int x = 1; x < buttonArray.size(); x++){
			pGroup.addGroup(buttonArray.get(x).setVerticalLocation(layout.createSequentialGroup(), size, buttonArray.size()-1));
		}
		pGroup.addGroup(buttonArray.get(0).setVerticalLocation(layout.createSequentialGroup(), size));
		return group.addGroup(pGroup);
	}
	public SequentialGroup setHorizontalLocation(GroupLayout layout, int sizeA, int sizeB){
		SequentialGroup group = layout.createSequentialGroup();
		for(int x = 1; x < buttonArray.size(); x++){
			group.addGroup(layout.createParallelGroup()
					.addGroup(buttonArray.get(x).setHorizontalLocation(layout.createSequentialGroup(), layout.createParallelGroup(), (sizeA/(buttonArray.size()-1)))));
		}
		group.addGroup(buttonArray.get(0).setHorizontalLocation(layout.createSequentialGroup(), layout.createParallelGroup(), sizeB));
		return group;
	}
}

class Buttons {
	private JButton dayButton;
	private JButton nightButton;
	private int dayNumber;
	public Buttons(int DayNumber){
		dayNumber = DayNumber;
		dayButton = new JButton("Day "+Integer.toString(dayNumber));
		nightButton = new JButton("Night "+Integer.toString(dayNumber));
	}
	public Buttons(){
		dayNumber = 0;
		dayButton = new JButton("Add Day");
		nightButton = new JButton("Remove Day");
	}
	public int getDayNumber(){
		return dayNumber;
	}
	public void setDayActionListener(ActionListener L){
		dayButton.addActionListener(L);
	}
	public void setNightActionListener(ActionListener L){
		nightButton.addActionListener(L);
	}
	public SequentialGroup setVerticalLocation(SequentialGroup group, int size){
		group.addComponent(dayButton, size/2, size/2, size/2)
		.addComponent(nightButton, size/2, size/2, size/2);
		return group;
	}
	public SequentialGroup setHorizontalLocation(SequentialGroup group, ParallelGroup pGroup, int size){
		group.addGroup(pGroup
				.addComponent(dayButton, size, size, size)
				.addComponent(nightButton, size, size, size));
		return group;
	}
	public SequentialGroup setVerticalLocation(SequentialGroup group, int size, int Amount){
		if(Amount > 4){
			if(Amount > 6){
				dayButton.setText(Integer.toString(this.getDayNumber()));
				dayButton.setFont(dayButton.getFont().deriveFont(8F));
				nightButton.setText(Integer.toString(this.getDayNumber()));
				nightButton.setFont(nightButton.getFont().deriveFont(8F));
			} else {
				dayButton.setText("D-"+this.getDayNumber());
				nightButton.setText("N-"+this.getDayNumber());
				dayButton.setFont(dayButton.getFont().deriveFont(12F));
				nightButton.setFont(nightButton.getFont().deriveFont(12F));
			}
		} else {
			dayButton.setText("Day "+this.getDayNumber());
			nightButton.setText("Night "+this.getDayNumber());
		}
		return this.setVerticalLocation(group, size);
	}
}
class SaveLoadHandler implements SaveLoadListener<Integer>{
	@Override
	public Integer gatherSaveData() {
		return Main.dayButtons.getDay();
	}
	@Override
	public void inputLoadData(Integer input) {
		Main.dayButtons.setDay(input);
	}
}
class PrivateTimerListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){}
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