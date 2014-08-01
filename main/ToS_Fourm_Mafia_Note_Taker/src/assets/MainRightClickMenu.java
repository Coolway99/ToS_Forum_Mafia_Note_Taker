package assets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextPane;


@SuppressWarnings("serial")
public class MainRightClickMenu extends JPopupMenu{
	protected static JPopupMenu pop;
	private static PersonalActionListener listener = new PersonalActionListener();
	public static PersonalMouseListener mouse = new PersonalMouseListener();
	protected static MouseEvent e;
	public static void initPopup(){
		pop = new JPopupMenu();
		MenuInterface menuItem = new MenuInterface("Edit");
	    menuItem.addActionListener(listener);
	    pop.add(menuItem);
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
				MainRightClickMenu.e = e;
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
		((JTextPane)MainRightClickMenu.e.getSource()).setEditable(true);
		((JTextPane)MainRightClickMenu.e.getSource()).requestFocus();
	}
}