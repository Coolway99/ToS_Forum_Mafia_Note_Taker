package assets;

import main.Main;
import assets.listeners.ButtonMouseListener;


public class ClassOfButtons {
	public static ButtonMouseListener clickyButton = new ButtonMouseListener();
	public static Buttons1 testbutton1, testbutton2;
	@SuppressWarnings("serial")
	public static void init(){
		testbutton1 = new Buttons1(){
			@Override 
			public void pressed(int button){
				Main.playersLabel.setText("("+Integer.toString(button)+")Button1 Has been pressed");
				Main.frame.pack();
			}
		};
		testbutton2 = new Buttons1(){
			@Override 
			public void pressed(int button){
				Main.playersLabel.setText("("+Integer.toString(button)+")Button2 Has been pressed");
				Main.frame.repaint();
			}
		};
		testbutton1.addMouseListener(clickyButton);
		testbutton2.addMouseListener(clickyButton);
	}
}
