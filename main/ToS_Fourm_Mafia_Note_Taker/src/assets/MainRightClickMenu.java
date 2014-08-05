package assets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.Main;


@SuppressWarnings("serial")
public class MainRightClickMenu extends JPopupMenu{
	protected static JPopupMenu pop;
	private static PersonalActionListener listener = new PersonalActionListener();
	public static PersonalMouseListener mouse = new PersonalMouseListener();
	protected static MouseEvent eBox;
	public static FocusListener1 focus = new FocusListener1();
	public static void initPopup(){
		pop = new JPopupMenu();
		MenuInterface menuItem = new MenuInterface("Edit");
	    menuItem.addActionListener(listener);
	    pop.add(menuItem);
	    {

	    }
	}
}
class PersonalMouseListener extends MouseAdapter{
	@Override
	public void mousePressed(MouseEvent e){popup(e);}
	@Override
	public void mouseReleased(MouseEvent e){popup(e);}
	
	@SuppressWarnings("static-access")
	public void popup(MouseEvent e){
		if(e.getButton() == e.BUTTON3){
			if(e.isPopupTrigger()){
				MainRightClickMenu.pop.show(e.getComponent(), e.getX(), e.getY());
				MainRightClickMenu.eBox = e;
			}
		}
	}
}
class PersonalActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuInterface menu = (MenuInterface)e.getSource();
		menu.doAction(e);
	}
	
}
@SuppressWarnings("serial")
class MenuInterface extends JMenuItem{
	public MenuInterface(String string) {super(string);}

	public void doAction(ActionEvent e){
		((MainTextPane)MainRightClickMenu.eBox.getSource()).setContentType("text/plain");
		((MainTextPane)MainRightClickMenu.eBox.getSource()).setEditable(true);
		((MainTextPane)MainRightClickMenu.eBox.getSource()).requestFocus();
	}
}
class FocusListener1 implements FocusListener{
	private String string;
	@Override
	public void focusGained(FocusEvent e) {
		if(((MainTextPane) e.getSource()).isEditable()){
			string = ((MainTextPane) e.getSource()).getText();
			
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		string = ((MainTextPane) e.getSource()).getText();
		((MainTextPane) e.getSource()).setEditable(false);
		((MainTextPane) e.getSource()).setContentType("text/html");
		((MainTextPane) e.getSource()).setText(unParse(string));
	}
	public String unParse(String in){
		String[] BBCodeList  = {"\\[", "\\]", "color=", "/color", "size=", "/size", "\n"};
		String[] HTMLCodeList = {"<", ">", "font color=", "/font", "font size=", "/font", "<br />"};
		String B = in;
		for(int y = 0; y < BBCodeList.length; y++){
			String A[] = B.split(BBCodeList[y]);
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += HTMLCodeList[y];
				B += A[x];
			}
		}
		return "<body style=\"font-family: " + Main.frame.getFont().getFamily() + "\">"+B+"</style>";
	}
}