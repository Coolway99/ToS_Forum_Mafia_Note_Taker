package main.listeners;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

	public SecondaryButtonListener(){
		this.whisperArea.setLineWrap(true);
		this.whisperFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.whisperFrame.setSize(400, 400);

		this.genNoteArea.setLineWrap(true);
		this.genNoteFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.genNoteFrame.setSize(400, 400);

		this.whisperFrame.add(new JScrollPane(this.whisperArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		this.genNoteFrame.add(new JScrollPane(this.genNoteArea, 20, 30));
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == Main.whisper) {
			this.whisperFrame.setLocationRelativeTo(null);
			this.whisperFrame.setVisible(true);
		} else if(e.getSource() == Main.generalNotes) {
			this.genNoteFrame.setLocationRelativeTo(null);
			this.genNoteFrame.setVisible(true);
		} else if(e.getSource() == Main.update) {
			UpdateHandler.check("1.6", false);
		} else if(e.getSource() == Main.info) {
			JLabel label = new JLabel();
			Font font = label.getFont();

			StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
			style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
			style.append("font-size:" + font.getSize() + "pt;");

			JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
					+ "Make sure to RIGHT CLICK the notes/playerlist/rolelist<br />"
					+ "Also remember to resize the window if it is too small<br />" + "<br />"
					+ "Report issues <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/issues\">here</a><br /><br />"
					+ "Newest version can be found manually <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/latest\">here</a><br />"
					+ "Email:<a href=\"mailto:xxcoolwayxx@gmail.com\" >xxcoolwayxx@gmail.com</a><br />"
					+ "<br />" + "Copy-whatever 2014-2015 Coolway99<br />"
					+ "Licenced under the GNU v2" + "</body></html>");
			ep.addHyperlinkListener(new HyperlinkListener(){
				@Override
				public void hyperlinkUpdate(HyperlinkEvent e){
					if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
						try {
							Desktop.getDesktop().browse(e.getURL().toURI());
						} catch(IOException | java.net.URISyntaxException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(Main.frame, "Error opening link", "ERROR",
									0);
						}
					}
				}
			});
			ep.setEditable(false);
			ep.setBackground(label.getBackground());
			JOptionPane.showMessageDialog(Main.frame, ep, "Help / Contact Info", 1);
		} else if(e.getSource() == Main.options) {
			this.optionFrame.setLocationRelativeTo(null);
			this.optionFrame.setVisible(true);
		}
	}
}