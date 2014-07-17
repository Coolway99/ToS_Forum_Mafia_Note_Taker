package assets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.Main;


@SuppressWarnings("serial")
public class MainRightClickMenu extends JPopupMenu{
	protected static JPopupMenu pop;
	private static PersonalActionListener listener = new PersonalActionListener();
	private static PersonalMouseListener mouse = new PersonalMouseListener();
	public static void initPopup(){
		pop = new JPopupMenu();
		MenuInterface menuItem = new MenuInterface("A popup menu item");
	    menuItem.addActionListener(listener);
	    pop.add(menuItem);
	    menuItem = new MenuInterface("Another popup menu item");
	    menuItem.addActionListener(listener);
	    pop.add(menuItem);
	    Main.textField1.addMouseListener(mouse);
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
			}
		}
	}
}
class PersonalActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuInterface menu = (MenuInterface)e.getSource();
		menu.doAction();
	}
	
}
@SuppressWarnings("serial")
class MenuInterface extends JMenuItem{
	public MenuInterface(String string) {super(string);}

	public void doAction(){}
}