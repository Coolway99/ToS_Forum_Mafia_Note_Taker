package main;

import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class MainTextPane extends JTextPane{
	public String origString;
	public String fieldName;
	public MainTextPane(){
		origString = "";
		fieldName = "";
	}
}
