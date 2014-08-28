package assets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.Main;


@SuppressWarnings("serial")
public class MainRightClickMenu extends JPopupMenu{
	protected static JPopupMenu pop;
	private static PersonalActionListener listener = new PersonalActionListener();
	public static PersonalMouseListener mouse = new PersonalMouseListener();
	public static JFrame editFrame = new JFrame();
	public static JTextArea editArea = new JTextArea();
	public static JButton editSubmit = new JButton();
	public static JButton editCancel = new JButton();
	protected static MouseEvent eBox;
	private static GridBagLayout layout;
	private static GridBagConstraints c;
	private static HashMap<Integer, HashMap<Boolean, String>> CodeList = new HashMap<Integer, HashMap<Boolean, String>>();
	@SuppressWarnings("static-access")
	public static void initPopup(){
		String[] FromList  = {"\\[color=", "\\[/color\\]", "\\[size=", "\\[/size\\]", "\n", "\\[b\\]", "\\[/b\\]",
				"\\[i\\]", "\\[/i\\]", "\\[u\\]", "\\[/u\\]", "\\[s\\]", "\\[/s\\]", "\\[url=", "\\[/url\\]"};
		String[] ToList = {"<font color=", "</font>", "<font size=", "</font>", "<br />", "<b>", "</b>",
				"<i>", "</i>", "<u>", "</u>", "<s>", "</s>", "<a href=\"", "</a>"};
		for(int x = 0; x < FromList.length; x++){
			CodeList.put(x, new HashMap<Boolean, String>());
			CodeList.get(x).put(true, FromList[x]);
			CodeList.get(x).put(false, ToList[x]);
		}
		layout = new GridBagLayout();
		pop = new JPopupMenu();
		MenuInterface menuItem = new MenuInterface("Edit");
		menuItem.addActionListener(listener);
		pop.add(menuItem);
		editFrame.setSize(400, 400);
		editArea.setLineWrap(true);
		editFrame.setLayout(layout);
		int rows[] = new int[10];
		for(int x = 0; x < rows.length; x++){
			rows[x] = (int) ((400/rows.length));
		}
		int columns[] = new int[2];
		for(int x = 0; x < columns.length; x++){
			columns[x] = (int) ((400/columns.length));
		}
		layout.rowHeights = rows;
		layout.columnWidths = columns;
		c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = c.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 9;
		editFrame.add(new JScrollPane(editArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), c);
		c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = c.BOTH;
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		editFrame.add(editSubmit, c);
		c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = c.BOTH;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		editFrame.add(editCancel, c);
		editFrame.setLocationRelativeTo(null);
		editFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		editSubmit.setText("Submit");
		editCancel.setText("Cancel");
		editSubmit.addActionListener(new EditButtonActionListener());
		editCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainRightClickMenu.editFrame.setVisible(false);
				Main.frame.setEnabled(true);
				Main.frame.requestFocus();
			}
		});
	}
	public static String unParse(String in){
		String B = in;
		for(int y = 0; y < CodeList.size(); y++){
			String A[] = B.split(CodeList.get(y).get(true));
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += CodeList.get(y).get(false);
				B += (CodeList.get(y).get(false).endsWith(">")) ? A[x] : (CodeList.get(y).get(false).equals("<a href=\"") ? A[x].replaceFirst("\\]", "\">") :A[x].replaceFirst("\\]", ">"));
			}
		}
		return "<font face=\"arial\">"+B+"</font>";
	}
}
class PersonalMouseListener extends MouseAdapter{
	@Override
	public void mousePressed(MouseEvent e){popup(e);}
	@Override
	public void mouseReleased(MouseEvent e){popup(e);}
	
	public void popup(MouseEvent e){
		if(e.isPopupTrigger()){
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
		if(!MainRightClickMenu.editFrame.isVisible()){
			MainRightClickMenu.editFrame.setTitle(((MainTextPane)MainRightClickMenu.eBox.getSource()).fieldName);
			MainRightClickMenu.editArea.setText(((MainTextPane)MainRightClickMenu.eBox.getSource()).origString);
			MainRightClickMenu.editFrame.setVisible(true);
			MainRightClickMenu.editArea.requestFocus();
			Main.frame.setEnabled(false);
		}
	}
}
class EditButtonActionListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		MainRightClickMenu.editFrame.setVisible(false);
		((MainTextPane)MainRightClickMenu.eBox.getSource()).origString = MainRightClickMenu.editArea.getText();
		((MainTextPane)MainRightClickMenu.eBox.getSource()).setText(MainRightClickMenu.unParse(MainRightClickMenu.editArea.getText()));
		Main.frame.setEnabled(true);
		Main.frame.requestFocus();
	}
}