package assets;

import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Buttons1 extends JButton{
	public Buttons1(){}
	public Buttons1(String string){super(string);}
	public Buttons1(Icon icon){super(icon);}
	public Buttons1(String string, Icon icon){super(string, icon);}
	public void pressed(int button){}
	public void released(int button){}
}