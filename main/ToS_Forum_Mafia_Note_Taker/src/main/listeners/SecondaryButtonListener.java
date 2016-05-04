package main.listeners;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.Main;
import main.Options;
import main.UpdateHandler;

public class SecondaryButtonListener implements ActionListener{
	private final JFrame whisperFrame = new JFrame("Whispers");
	public final JTextArea whisperArea = new JTextArea();
	
	private final JFrame genNoteFrame = new JFrame("General Notes");
	public final JTextArea genNoteArea = new JTextArea();
	
	public final Options optionFrame = new Options();
	
	public SecondaryButtonListener() {
		super();
		this.whisperArea.setLineWrap(true);
		this.whisperFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.whisperFrame.setSize(400, 400);
		
		this.genNoteArea.setLineWrap(true);
		this.genNoteFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.genNoteFrame.setSize(400, 400);
		
		GridBagLayout layout = new GridBagLayout();
		int rows[] = new int[5];
		for(int x = 0; x < rows.length; x++){
			rows[x] = (400/rows.length)/2;
		}
		int columns[] = new int[1];
		for(int x = 0; x < columns.length; x++){
			columns[x] = (400/columns.length)/2;
		}
		layout.rowHeights = rows;
		layout.columnWidths = columns;
		this.whisperFrame.setLayout(layout);
		GridBagConstraints c = resetConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 5;
		c.gridwidth = 1;
		this.whisperFrame.add(new JScrollPane(this.whisperArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
		layout = new GridBagLayout();
		layout.rowHeights = rows;
		layout.columnWidths = columns;
		this.genNoteFrame.setLayout(layout);
		c = resetConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 5;
		c.gridwidth = 1;
		this.genNoteFrame.add(new JScrollPane(this.genNoteArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
	}
	private static GridBagConstraints resetConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Main.whisper){
			this.whisperFrame.setLocationRelativeTo(null);
			this.whisperFrame.setVisible(true);
		} else if(e.getSource() == Main.generalNotes){
			this.genNoteFrame.setLocationRelativeTo(null);
			this.genNoteFrame.setVisible(true);
		} else if(e.getSource() == Main.update){
			UpdateHandler.check(Main.progVers, false);
		} else if(e.getSource() == Main.info){
			// for copying style
			JLabel label = new JLabel();
			Font font = label.getFont();
			
			// create some css from the label's font
			StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
			style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
			style.append("font-size:" + font.getSize() + "pt;");
			
			final JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
					+ "Make sure to RIGHT CLICK the notes/playerlist/rolelist<br />"
					+ "Also remember to resize the window if it is too small<br />"
					+ "<br />"
					+ "Report issues <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/issues\">here</a><br /><br />"
					+ "Newest version can be found manually <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/latest\">here</a><br />"
					+ "Email:<a href=\"mailto:xxcoolwayxx@gmail.com\" >xxcoolwayxx@gmail.com</a><br />"
					+ "<br />"
					+ "Copy-whatever 2014-2016 Coolway99<br />"
					+ "Licenced under the GNU v2"
					+ "</body></html>");
			ep.addHyperlinkListener(new HyperlinkListener()
			{
				@Override
				public void hyperlinkUpdate(HyperlinkEvent e){
					if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
						try {
							Desktop.getDesktop().browse(e.getURL().toURI());
						} catch (IOException | URISyntaxException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(Main.frame, "Error opening link", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			ep.setEditable(false);
			ep.setBackground(label.getBackground());
			JOptionPane.showMessageDialog(Main.frame, ep, "Help / Contact Info",
					JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getSource() == Main.options){
			this.optionFrame.setLocationRelativeTo(null);
			this.optionFrame.setVisible(true);
		}
	}
}