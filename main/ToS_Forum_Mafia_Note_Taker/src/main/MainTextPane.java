package main;

import javax.swing.JTextPane;

public class MainTextPane extends JTextPane{
	private static final long serialVersionUID = -8987094032065248053L;
	
	public String origString;
	public String fieldName;

	public MainTextPane(){
		this.origString = "";
		this.fieldName = "";
	}
}