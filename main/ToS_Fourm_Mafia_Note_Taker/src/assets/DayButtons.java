package assets;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DayButtons extends Component{
	private JButton dayButton;
	private JButton nightButton;
	private int dayNumber;
	public DayButtons(int DayNumber){
		dayNumber = DayNumber;
		dayButton = new JButton("Day "+Integer.toString(dayNumber));
		nightButton = new JButton("Night "+Integer.toString(dayNumber));
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
	public void setDayFocusListener(FocusListener L){
		dayButton.addFocusListener(L);
	}
	public void setNightFocusListener(FocusListener L){
		nightButton.addFocusListener(L);
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
	@Override
	public void paint(Graphics g){
		dayButton.paint(g);
		nightButton.paint(g);
	}
}
