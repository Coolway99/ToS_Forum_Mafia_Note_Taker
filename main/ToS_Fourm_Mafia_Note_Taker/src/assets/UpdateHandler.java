package assets;

import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.Main;

public class UpdateHandler {
	private static final JEditorPane ep = new JEditorPane();
	private static StringBuffer style;
	public static void check(String oldVer, boolean silent){
		// for copying style
		JLabel label = new JLabel();
		Font font = label.getFont();
		ep.setContentType("text/html");
		// create some css from the label's font
		style = new StringBuffer("font-family:" + font.getFamily() + ";");
		style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
		style.append("font-size:" + font.getSize() + "pt;");
		ep.addHyperlinkListener(new HyperlinkListener()
		{
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e){
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		ep.setBackground(label.getBackground());
		ep.setEditable(false);
		try {
			URL url = new URL("https://raw.githubusercontent.com/Coolway99/ToS_Forum_Mafia_Note_Taker/master/version.txt");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String newVer = in.readLine();
			if(VersionParsingHandler.isVersionGreaterThan(newVer+".0.0", oldVer.split("\n")[0]+".0.0")){
				String ver = newVer;
				newVer = in.readLine();
				if(newVer == null){
					ep.setText("<html><body style=\"" + style + "\">"
							+ "New Version found, it is strongly recommended that you download it "
							+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
							ver+"\">here</a>"
							+ "</body></html>");
					JOptionPane.showConfirmDialog(Main.frame, ep, "Update Found", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					ep.setText("<html><body style=\"" + style + "\">"
							+ "New Version found, it is strongly recommended that you download it "
							+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
							ver+"-Hotfix-"+newVer+"\">here</a>"
							+ "</body></html>");
					JOptionPane.showConfirmDialog(Main.frame, ep, "Update Found", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}
			} else if(VersionParsingHandler.isVersionEqualToo(newVer+".0.0", oldVer.split("\n")[0]+".0.0")){
				if(oldVer.split("\n").length > 1){
					newVer = in.readLine();
					if(newVer != null){
						if(oldVer.split("\n")[1].toCharArray()[0] < newVer.toCharArray()[0]){
							ep.setText("<html><body style=\"" + style + "\">"
									+ "New Hotfix found, it is strongly recommended that you download it "
									+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
									oldVer.split("\n")[0]+"-Hotfix-"+newVer+"\">here</a>"
									+ "</body></html>");
							JOptionPane.showConfirmDialog(Main.frame, ep, "New Hotfix Found", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						} else if((oldVer.split("\n")[1].toCharArray()[0] == newVer.toCharArray()[0]) && !silent){
							JOptionPane.showConfirmDialog(Main.frame, "Up to date!", "Up to date", JOptionPane.DEFAULT_OPTION);
						}
					}
				} else {
					newVer = in.readLine();
					if(newVer != null){
						ep.setText("<html><body style=\"" + style + "\">"
								+ "New Hotfix found, it is SUPER strongly recommended that you download it "
								+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
								oldVer.split("\n")[0]+"-Hotfix-"+newVer+"\">here</a>"
								+ "</body></html>");
						JOptionPane.showConfirmDialog(Main.frame, ep);
					} else if(!silent){
						JOptionPane.showConfirmDialog(Main.frame, "Up to date!", "Up to date", JOptionPane.DEFAULT_OPTION);
					}
				}
			} else {
				if(!silent){
					JOptionPane.showConfirmDialog(Main.frame, "Up to date!", "Up to date", JOptionPane.DEFAULT_OPTION);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(!silent){
				ep.setText("<html><body style=\"" + style + "\">"
						+ "<center>ERROR WHILE UPDATING<br /><br />"
						+ "Newest version can be found manually <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases\">here</a></center><br />"
						+ "Note: This might open up two tabs. sorry, glitch, if you're here then there is something wrong anyways so >.>"
						+ "</body></html>");
				JOptionPane.showConfirmDialog(Main.frame, ep, "UPDATE CHECKING ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
