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
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.Main;
import main.UpdateHandler;

public class SecondaryButtonListener implements ActionListener{
	private final JFrame whisperFrame;
	public final JTextArea whisperArea;
	private final JFrame genNoteFrame;
	public final JTextArea genNoteArea;
	@SuppressWarnings("static-access")
	public SecondaryButtonListener() {
		super();
		{
			whisperArea = new JTextArea();
			whisperArea.setLineWrap(true);
			whisperFrame = new JFrame("Whispers");
			whisperFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			whisperFrame.setSize(400, 400);
			genNoteArea = new JTextArea();
			genNoteArea.setLineWrap(true);
			genNoteFrame = new JFrame("General Notes");
			genNoteFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			genNoteFrame.setSize(400, 400);
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
			whisperFrame.setLayout(layout);
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 5;
			c.gridwidth = 1;
			c.fill = c.BOTH;
			whisperFrame.add(new JScrollPane(whisperArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
			layout = new GridBagLayout();
			layout.rowHeights = rows;
			layout.columnWidths = columns;
			genNoteFrame.setLayout(layout);
			c = new GridBagConstraints();
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 5;
			c.gridwidth = 1;
			c.fill = c.BOTH;
			genNoteFrame.add(new JScrollPane(genNoteArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Main.whisper){
			whisperFrame.setLocationRelativeTo(null);
			whisperFrame.setVisible(true);
		}else if(e.getSource() == Main.generalNotes){
			genNoteFrame.setLocationRelativeTo(null);
			genNoteFrame.setVisible(true);
		}else if(e.getSource() == Main.update){
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
					+ "Report issues <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/issues\">here</a><br /><br />"
					+ "Newest version can be found manually <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases\">here</a><br />"
					+ "Email:<a href=\"mailto:xxcoolwayxx@gmail.com\" >xxcoolwayxx@gmail.com</a><br /><br />"
					+ "Copy-whatever 2014 Coolway99<br />"
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
							JOptionPane.showMessageDialog(Main.frame, "Error opening link", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			ep.setEditable(false);
			ep.setBackground(label.getBackground());
			JOptionPane.showMessageDialog(Main.frame, ep, "Contact Info",
					JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getSource() == Main.help){
			JOptionPane.showMessageDialog(Main.frame, "Make sure to RIGHT CLICK the notes/playerlist/rolelist\n"
					+ "Also remember to resize the window if it is too small", "Help", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}