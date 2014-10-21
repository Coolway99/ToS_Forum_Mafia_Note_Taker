package main;

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

public class UpdateHandler {
	private static final JEditorPane ep = new JEditorPane();
	private static StringBuffer style;
	/**
	 * Used for checking the version
	 * @param oldVer The current version
	 * @param silent Should it notify if it's up to date or there was a issue
	 */
	public static void check(String oldVer, boolean silent){
		// for copying style
		JLabel label = new JLabel();
		Font font = label.getFont();
		ep.setContentType("text/html");
		// create some css from the label's font
		style = new StringBuffer("font-family:" + font.getFamily() + ";");
		style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
		style.append("font-size:" + font.getSize() + "pt;");
		ep.addHyperlinkListener(new HyperlinkListener(){
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
		ep.setBackground(label.getBackground());
		ep.setEditable(false);
		try /*so the entire program doesn't break when it doesn't get it's way*/{
			URL url = new URL("https://raw.githubusercontent.com/Coolway99/ToS_Forum_Mafia_Note_Taker/master/version.txt");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String newVer = in.readLine();
			//If the new version is greater than the old version, the '+.0.0' is because this re-uses the saving version handler
			if(VersionParsingHandler.isVersionGreaterThan(newVer+".0.0", oldVer.split("\n")[0]+".0.0")){
				String ver = newVer;
				newVer = in.readLine();
				ep.setText("<html><body style=\"" + style + "\">"
						+ "New Version found, it is strongly recommended that you download it "
						+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
						ver+((newVer != null /*If there is a hotfix*/) ? "-Hotfix-"+newVer : "")+"\">here</a>"
						+ "</body></html>");
				JOptionPane.showMessageDialog(Main.frame, ep, "Update Found", JOptionPane.INFORMATION_MESSAGE);
			//Else if the versions are the same (I.E. might be different hotfixes)
			} else if(VersionParsingHandler.isVersionEqualToo(newVer+".0.0", oldVer.split("\n")[0]+".0.0")){
				//If this version is indeed a hotfix
				if(oldVer.split("\n").length > 1){
					newVer = in.readLine();
					//If the newer version is also a hotfix
					if(newVer != null){
						//If the letter is higher than the previous (I.E. A < B)
						if(oldVer.split("\n")[1].toCharArray()[0] < newVer.toCharArray()[0]){
							ep.setText("<html><body style=\"" + style + "\">"
									+ "New Hotfix found, it is strongly recommended that you download it "
									+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
									oldVer.split("\n")[0]+"-Hotfix-"+newVer+"\">here</a>"
									+ "</body></html>");
							JOptionPane.showMessageDialog(Main.frame, ep, "New Hotfix Found", JOptionPane.INFORMATION_MESSAGE);
						//Else if the versions are the same
						} else if((oldVer.split("\n")[1].toCharArray()[0] == newVer.toCharArray()[0]) && !silent){
							JOptionPane.showMessageDialog(Main.frame, "Up to date!", "Up to date", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				// Else if this version is not a hotfix
				} else {
					newVer = in.readLine();
					//If the new version is indeed a hotfix
					if(newVer != null){
						ep.setText("<html><body style=\"" + style + "\">"
								+ "New Hotfix found, it is SUPER strongly recommended that you download it "
								+ "<a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases/V"+
								oldVer.split("\n")[0]+"-Hotfix-"+newVer+"\">here</a>"
								+ "</body></html>");
						JOptionPane.showMessageDialog(Main.frame, ep);
					//Else if the versions are the same
					} else if(!silent){
						JOptionPane.showMessageDialog(Main.frame, "Up to date!", "Up to date", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			//Else if the version is higher
			} else if(!silent){
				JOptionPane.showMessageDialog(Main.frame, "Up to date!", "Up to date", JOptionPane.INFORMATION_MESSAGE);
			}
		//Catch the non-internet error
		} catch (IOException e) {
			e.printStackTrace();
			if(!silent){
				ep.setText("<html><body style=\"" + style + "\">"
						+ "<center>ERROR WHILE UPDATING<br /><br />"
						+ "Newest version can be found manually <a href=\"http://github.com/Coolway99/ToS_Forum_Mafia_Note_Taker/releases\">here</a></center><br />"
						+ "Note: This might open up two tabs. sorry, glitch, if you're here then there is something wrong anyways so >.>"
						+ "</body></html>");
				JOptionPane.showMessageDialog(Main.frame, ep, "UPDATE CHECKING ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
