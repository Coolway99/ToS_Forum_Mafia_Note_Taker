package main;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class MainRightClickMenu implements ActionListener, MouseListener{
	
	public static final MainRightClickMenu self = new MainRightClickMenu();
	protected static JPopupMenu pop = new JPopupMenu();
	public static JFrame editFrame = new JFrame();
	public static JTextArea editArea = new JTextArea();
	public static JButton editSubmit = new JButton("Submit");
	public static JButton editCancel = new JButton("Cancel");
	public static JPanel bbcPanel = new JPanel();
	public static JButton bbcButtons[] = {new JButton("Colors"), new JButton("Size"),
		new JButton("b"), new JButton("i"),
		new JButton("u"), new JButton("s"),
		new JButton("[url=]") };
	public static JButton colorButtons[][];
	protected static MouseEvent eBox;
	private static GridBagLayout layout = new GridBagLayout();
	public static JFrame colorFrame = new JFrame();
	private static GridBagLayout colorFrameLayout = new GridBagLayout();
	private static GridBagConstraints c;
	private static HashMap<Integer, HashMap<Boolean, String>> CodeList = new HashMap<>();
	
	public static void initPopup(){
		String[] FromList =
			{"\\[color=", "\\[/color\\]", "\\[size=", "\\[/size\\]", "\n", "\\[b\\]",
				"\\[/b\\]", "\\[i\\]", "\\[/i\\]", "\\[u\\]", "\\[/u\\]", "\\[s\\]",
				"\\[/s\\]", "\\[url=", "\\[/url\\]" };
		String[] ToList =
			{"<font color=", "</font>", "<font size=", "</font>", "<br />", "<b>", "</b>",
				"<i>", "</i>", "<u>", "</u>", "<s>", "</s>", "<a href=\"", "</a>" };
		for (int x = 0; x < FromList.length; x++) {
			CodeList.put(x, new HashMap<Boolean, String>());
			CodeList.get(x).put(true, FromList[x]);
			CodeList.get(x).put(false, ToList[x]);
		}
		colorFrame.setLayout(colorFrameLayout);
		JMenuItem menuItem = new JMenuItem("Edit");
		menuItem.addActionListener(self);
		menuItem.setName("Edit");
		pop.add(menuItem);
		editFrame.setSize(600, 400);
		editArea.setLineWrap(true);
		editFrame.setLayout(layout);
		int rows[] = new int[10];
		for (int x = 0; x < rows.length; x++) {
			rows[x] = (400 / rows.length);
		}
		int columns[] = new int[3];
		for (int x = 0; x < columns.length; x++) {
			columns[x] = (600 / columns.length);
		}
		layout.rowHeights = rows;
		layout.columnWidths = columns;
		c = resetConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 9;
		editFrame.add(new JScrollPane(editArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), c);
		c = resetConstraints();
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		editFrame.add(editSubmit, c);
		c = resetConstraints();
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		editFrame.add(editCancel, c);
		c = resetConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 10;
		bbcPanel.setLayout(new BoxLayout(bbcPanel, BoxLayout.Y_AXIS));
		{
			class InsertListener implements ActionListener{
				private final String b;
				private final String a;
				public InsertListener(String before, String after){
					this.b = before;
					this.a = after;
				}
				@Override
				public void actionPerformed(ActionEvent e){
					insertText(this.b, this.a);
				}
			}
			JButton b = bbcButtons[0];
			b.addActionListener(self);
			b.setName("Color");
			b = bbcButtons[1];
			b.addActionListener(new InsertListener("[size=", "[/size]"));
			b = bbcButtons[2];
			b.addActionListener(new InsertListener("[b]", "[/b]"));
			b.setFont(b.getFont().deriveFont(Font.BOLD));
			b = bbcButtons[3];
			b.addActionListener(new InsertListener("[i]", "[/i]"));
			b.setFont(b.getFont().deriveFont(Font.ITALIC));
			b = bbcButtons[4];
			b.addActionListener(new InsertListener("[u]", "[/u]"));
			b = bbcButtons[5];
			b.addActionListener(new InsertListener("[s]", "[/s]"));
			b = bbcButtons[6];
			b.addActionListener(new InsertListener("[url=]", "[url]"));
		}
		for(JButton b : bbcButtons){
			bbcPanel.add(b);
		}
		editFrame.add(bbcPanel, c);
		colorFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		editFrame.setLocationRelativeTo(null);
		editFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		editSubmit.setName("Submit");
		editSubmit.addActionListener(self);
		editCancel.addActionListener(self);
		editCancel.setName("Cancel");
		try {
			final BufferedImage colors =
					ImageIO.read(Main.class.getClassLoader().getResource("assets/images/colors.png"));
					class ButtonListener implements ActionListener {
						private BufferedImage icon;
						
						public ButtonListener(BufferedImage icon){
							this.icon = icon;
						}
						@Override
						public void actionPerformed(ActionEvent e){
							insertText(String.format("[color=#%06X]", this.icon.getRGB(0, 0) - 0xFF000000),
									"[/color]");
						}
					}
					colorButtons = new JButton[colors.getHeight()][colors.getWidth()];
					rows = new int[colors.getHeight()];
					for (int x = 0; x < rows.length; x++) {
						rows[x] = 16;
					}
					columns = new int[colors.getWidth()];
					for (int x = 0; x < columns.length; x++) {
						columns[x] = 16;
					}
					colorFrameLayout.rowHeights = rows;
					colorFrameLayout.columnWidths = columns;
					for (int y = 0; y < colorButtons.length; y++) {
						for (int x = 0; x < colorButtons[0].length; x++) {
							final BufferedImage icon = colors.getSubimage(x, y, 1, 1);
							colorButtons[y][x] = new JButton();
							colorButtons[y][x].addActionListener(new ButtonListener(icon));
							c = resetConstraints();
							c.gridx = x;
							c.gridy = y;
							c.gridheight = 1;
							c.gridwidth = 1;
							colorFrame.add(colorButtons[y][x], c);
						}
					}
					colorFrame.pack();
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run(){
							for (int y = 0; y < colorButtons.length; y++) {
								for (int x = 0; x < colorButtons[0].length; x++) {
									final BufferedImage icon = colors.getSubimage(x, y, 1, 1);
									colorButtons[y][x].setBorder(null);
									colorButtons[y][x].setIcon(new ImageIcon(icon.getScaledInstance(
											colorButtons[y][x].getWidth(), colorButtons[y][x].getHeight(),
											Image.SCALE_FAST)));
								}
							}
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static GridBagConstraints resetConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}
	
	public static void insertText(String before, String after){
		if (editArea.getSelectedText() == null) {
			editArea.replaceSelection(before + after);
			editArea.setCaretPosition(editArea.getCaretPosition() - after.length());
		} else {
			editArea.replaceSelection(before + editArea.getSelectedText() + after);
		}
		editArea.requestFocus();
	}
	
	public static String unParse(String in){
		String B = in;
		for (int y = 0; y < CodeList.size(); y++) {
			String A[] = B.split(CodeList.get(y).get(true));
			B = A[0];
			for (int x = 1; x < A.length; x++) {
				B += CodeList.get(y).get(false);
				B += (CodeList.get(y).get(false).endsWith(">")) ? A[x] : (CodeList.get(y)
						.get(false).equals("<a href=\"") ? A[x].replaceFirst("\\]", "\">")
								: A[x].replaceFirst("\\]", ">"));
			}
		}
		return "<font face=\""+Main.font+"\">" + B + "</font>";
	}

	@Override
	public void actionPerformed(ActionEvent e){
		switch(((Component) e.getSource()).getName()){
			case("Edit"):
				if(!MainRightClickMenu.editFrame.isVisible()){
					MainRightClickMenu.editFrame.setTitle(((MainTextPane) MainRightClickMenu.eBox
							.getSource()).fieldName);
					MainRightClickMenu.editArea
							.setText(((MainTextPane) MainRightClickMenu.eBox.getSource()).origString);
					MainRightClickMenu.editFrame.setVisible(true);
					MainRightClickMenu.editArea.requestFocus();
					
					Main.frame.setEnabled(false);
				}
				break;
			case("Color"):
				MainRightClickMenu.colorFrame.setVisible(true);
				MainRightClickMenu.colorFrame.requestFocus();
				break;
			case("Cancel"):
				MainRightClickMenu.editFrame.setVisible(false);
				MainRightClickMenu.colorFrame.setVisible(false);
				Main.frame.setEnabled(true);
				Main.frame.requestFocus();
				break;
			case("Submit"):
				MainRightClickMenu.editFrame.setVisible(false);
				MainRightClickMenu.colorFrame.setVisible(false);
				((MainTextPane) MainRightClickMenu.eBox.getSource()).origString =
						MainRightClickMenu.editArea.getText();
				((MainTextPane) MainRightClickMenu.eBox.getSource()).setText(MainRightClickMenu
						.unParse(MainRightClickMenu.editArea.getText()));
				Main.frame.setEnabled(true);
				Main.frame.requestFocus();
				Main.initLayout();
			break;
			default:
				break;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		popup(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		popup(e);
	}
	
	public static void popup(MouseEvent e){
		if (e.isPopupTrigger()) {
			pop.show(e.getComponent(), e.getX(), e.getY());
			eBox = e;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){/*UNUSED*/}
	@Override
	public void mouseEntered(MouseEvent e){/*UNUSED*/}
	@Override
	public void mouseExited(MouseEvent e){/*UNUSED*/}
}