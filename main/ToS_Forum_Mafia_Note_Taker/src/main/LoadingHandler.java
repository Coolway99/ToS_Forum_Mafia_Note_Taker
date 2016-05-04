package main;

import java.util.HashMap;

import javax.swing.JOptionPane;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class LoadingHandler implements ContentHandler{
	private HashMap<String, Boolean> tags = new HashMap<>();
	private String saveV;
	private String patch;
	private String Version;
	private int day;
	private boolean usePlayers;

	@Override
	public void startDocument() throws SAXException{
		String[] theTagList = {"beginSave", "data", "totalDays", "players", "whispers", "graveyard",
				"roles", "generalNotes", "number", "day", "notes", "night", "font", "playerNum"};
		for(int x = 0; x < theTagList.length; x++){
			this.tags.put(theTagList[x], false);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts)
			throws SAXException{
		if(this.tags.containsKey(qName)){
			this.tags.put(qName, true);
			if(qName.equals("beginSave")){
				this.saveV = atts.getValue("version");
				this.patch = atts.getValue("patch");
				this.Version = (this.saveV+"."+ this.patch);
				if(VersionParsingHandler.isVersionLessThan(this.Version, "0.0.1.0")){
					int value = JOptionPane.showConfirmDialog(Main.frame,
							"WARNING! The save you are loading is from an alpha version, between "
					+"then and now the playerlist and graveyard have merged.\n\n"
					+"If you want to keep the graveyard and scrap the playerlist, push yes.\n"
					+"If you want to keep the playerlist and scrap the graveyard, push no\n"
					+"If you want to cancel the operation, please press \"Cancel\"","WARNING!",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
					if(value == JOptionPane.YES_OPTION){
						this.usePlayers = false;
					} else if(value == JOptionPane.NO_OPTION){
						this.usePlayers = true;
					} else {
						throw new SAXException("User canceled load operation");
					}
				} else {
					this.usePlayers = true;
				}
			} else if(qName.equals("number")){
				this.day = Integer.parseInt(atts.getValue("day"));
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		String string = "";
		for(int x = start; x < start+length; x++){
			string += ch[x];
		}
		if(this.tags.get("totalDays")){
			Main.dayButtons = new DayButtons(Integer.parseInt(string));
		} else if(this.tags.get("players")){
			if(this.usePlayers){
				Main.playerArea.setText(MainRightClickMenu.unParse(unParse(string)));
				Main.playerArea.origString = unParse(string);
			}
		} else if(this.tags.get("font")){
			Main.secondaryListener.optionFrame.setFont(string);
		} else if(this.tags.get("playerNum")){
			Main.secondaryListener.optionFrame.setPlayers(string);
		} else if(this.tags.get("roles")){
			Main.roleList.setText(MainRightClickMenu.unParse(unParse(string)));
			Main.roleList.origString = unParse(string);
		} else if(this.tags.get("graveyard")){
			if(!this.usePlayers){
				Main.playerArea.setText(MainRightClickMenu.unParse(unParse(string)));
				Main.playerArea.origString = unParse(string);
			}
		} else if(this.tags.get("day")){
			if(this.tags.get("notes")){
				if(this.day == Main.selectedDay && Main.isDay){
					Main.setNoteString(this.day, true, unParse(string));
				} else {
					Main.dayButtons.setDayString(unParse(string), this.day);
				}
			}
		} else if(this.tags.get("night")){
			if(this.tags.get("notes")){
				if(this.day == Main.selectedDay && !Main.isDay){
					Main.setNoteString(this.day, false, unParse(string));
				} else {
					Main.dayButtons.setNightString(unParse(string), this.day);
				}
			}
		} else if(this.tags.get("whispers")){
			if(this.day == Main.selectedDay){
				Main.secondaryListener.whisperArea.setText(unParse(string));
			} else {
				Main.dayButtons.setWhisperString(unParse(string), this.day);
			}
		} else if(this.tags.get("generalNotes")){
			Main.secondaryListener.genNoteArea.setText(unParse(string));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if(this.tags.containsKey(qName)){
			this.tags.put(qName, false);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		Main.frame.setTitle(Main.title + " - " + Main.fc.getSelectedFile().getName().split(".FMNT")[0]);
		Main.fileSelected = true;
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException{/* UNUSED */}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException{/* UNUSED */}

	@Override
	public void processingInstruction(String target, String data) throws SAXException{/* UNUSED */}

	@Override
	public void setDocumentLocator(Locator locator){/* UNUSED */}

	@Override
	public void skippedEntity(String name) throws SAXException{/* UNUSED */}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException{/* UNUSED */}

	private String unParse(String in){
		String B = in;
		for(int y = 0; y < Main.parseList.length; y ++)
			B = B.replaceAll(Integer.parseInt(this.Version.split("\\.")[2]) >= 2
					? Main.unParseList[y] : Main.unParseList[y].replaceAll("-", ""),
					Main.parseList[y]);
		return B;
	}
}