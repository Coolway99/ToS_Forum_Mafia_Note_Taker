package assets;

import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class MainTextPane extends JTextPane{
	public String origString;
	public MainTextPane(){
		origString = null;
	}
	@Override
	public void setEditable(boolean in){
		super.setEditable(in);
		if(in){
			try{
				this.setText(origString);
			} catch(NullPointerException e){
				
			}
		} else {
			this.setContentType("text/plain");
			origString = this.getText();
		}
	}
}
