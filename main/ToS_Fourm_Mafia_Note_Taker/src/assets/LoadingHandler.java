package assets;

import java.util.HashMap;

import main.Main;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class LoadingHandler implements ContentHandler{
	private HashMap<String, Boolean> tags = new HashMap<>();
	private String saveV;
	private String patch;
	private int day;
	@Override
	public void startDocument() throws SAXException {
		String theTagList[] = {"beginSave", "data", "totalDays", "players", "graveyard"
				, "roles", "number", "day", "notes", "night"};
		for(int x = 0; x < theTagList.length; x++){
			tags.put(theTagList[x], false);
		}
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if(tags.containsKey(qName)){
			tags.put(qName, true);
			if(qName.equals("beginSave")){
				saveV = atts.getValue("version");
				patch = atts.getValue("patch");
			} else if(qName.equals("number")){
				day = Integer.parseInt(atts.getValue("day"));
			}
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String string = "";
		for(int x = start; x < start+length; x++){
			string += ch[x];
		}
		if(tags.get("totalDays")){
			Main.dayButtons.setDay(Integer.parseInt(string));
		} else if(tags.get("players")){
			Main.players.setText(unParse(string));
		} else if(tags.get("roles")){
			Main.roleList.setText(unParse(string));
		} else if(tags.get("graveyard")){
			Main.graveyard.setText(unParse(string));
		} else if(tags.get("day")){
			if(tags.get("notes")){
				if(day == Main.selectedDay && Main.isDay){
					Main.setNoteString(day, true, string);
				} else {
					Main.dayButtons.setDayString(string, day);
				}
			}
		} else if(tags.get("night")){
			if(tags.get("notes")){
				if(day == Main.selectedDay && !Main.isDay){
					Main.setNoteString(day, false, string);
				} else {
					Main.dayButtons.setNightString(string, day);
				}
			}
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(tags.containsKey(qName)){
			tags.put(qName, false);
		}
	}
	@Override
	public void endDocument() throws SAXException {
		Main.frame.setTitle(Main.title + " - " + Main.fc.getSelectedFile().getName().split(".FMNT")[0]);
	}
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	private String unParse(String in){
		{
			String A[] = in.split("!NL!");
			String B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "\n";
				B += A[x];
			}
			A = null;
			A = B.split("!S!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += " ";
				B += A[x];
			}
			A = null;
			A = B.split("!lfBrkt!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "<";
				B += A[x];
			}
			A = null;
			A = B.split("!rtBrkt!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += ">";
				B += A[x];
			}
			A = null;
			A = B.split("!ampt!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "&";
				B += A[x];
			}
			A = null;
			A = B.split("!dbQuote!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "\"";
				B += A[x];
			}
			A = null;
			A = B.split("!snQuote!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "'";
				B += A[x];
			}
			A = null;
			A = B.split("!tab!");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "\t";
				B += A[x];
			}
			return B;
		}
	}
}
