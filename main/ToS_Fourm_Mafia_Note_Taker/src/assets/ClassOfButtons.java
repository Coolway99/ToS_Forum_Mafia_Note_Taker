package assets;

import main.Main;

public class ClassOfButtons {
	public static Buttons1 testbutton1, testbutton2;
	@SuppressWarnings("serial")
	public static void init(){
		testbutton1 = new Buttons1(){
			@Override 
			public void pressed(int button){
				Main.textField.setText("("+Integer.toString(button)+")Button1 Has been pressed");
			}
		};
		testbutton2 = new Buttons1(){
			@Override 
			public void pressed(int button){
				Main.textField.setText("("+Integer.toString(button)+")Button2 Has been pressed");
			}
		};
	}
}
