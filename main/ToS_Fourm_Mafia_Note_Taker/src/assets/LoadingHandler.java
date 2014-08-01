package assets;

import java.util.HashMap;

import javax.swing.JOptionPane;

import main.Main;

import org.omg.CORBA.UserException;
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
	@Override
	public void startDocument() throws SAXException {
		String theTagList[] = {"beginSave", "data", "totalDays", "players", "allignment", "graveyard"
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
				Version = saveV+"."+patch;
				if(VersionParsingHandler.isVersionLessThan(Version, "0.0.1.0")){
					int value = JOptionPane.showConfirmDialog(Main.frame, "WARNING! The save you are"
							+ "loading is from a previous version, between then and now the "
							+ "playerlist and graveyard have merged.\n\n"
							+ "If you want to keep the graveyard and scrap the playerlist, push yes.\n"
							+ "If you want to keep the playerlist and scrap the graveyard, push no\n"
							+ "If you want to cancel the operation, please press \"Cancel\"","WARNING!", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if(value == JOptionPane.YES_OPTION){
						
					} else if(value == JOptionPane.NO_OPTION){
						
					} else {
						throw new SAXException(){};
					}
				}
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
		Main.fileSelected = true;
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
class VersionParsingHandler{
	public static boolean isVersionLessThan(String isThis, String lessThanThis){
		int A[] = parseVersion(isThis);
		int B[] = parseVersion(lessThanThis);
		if(A[0] < B[0]){
			return true;
		} else {
			if(A[0] == B[0]){
				if(A[1] < B[1]){
					return true;
				} else {
					if(A[1] == B[1]){
						if(A[2] < B[2]){
							return true;
						} else {
							if(A[2] == B[2]){
								if(A[3] < B[3]){
									return true;
								} else {
									return false;
								}
							} else {
								return false;
							}
						}
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
	}
	public static boolean isVersionEqualToo(String isThis, String equalToThis){
		int A[] = parseVersion(isThis);
		int B[] = parseVersion(equalToThis);
		if(A[0] == B[0]){
			if(A[1] == B[1]){
				if(A[2] == B[2]){
					if(A[3] == B[3]){
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public static boolean isVersionGreaterThan(String isThis, String greaterThanThis){
		int A[] = parseVersion(isThis);
		int B[] = parseVersion(greaterThanThis);
		if(A[0] > B[0]){
			return true;
		} else {
			if(A[0] == B[0]){
				if(A[1] > B[1]){
					return true;
				} else {
					if(A[1] == B[1]){
						if(A[2] > B[2]){
							return true;
						} else {
							if(A[2] == B[2]){
								if(A[3] > B[3]){
									return true;
								} else {
									return false;
								}
							} else {
								return false;
							}
						}
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
	}
	private static int[] parseVersion(String Version){
		String unParsed[] = Version.split("\\.");
		int version[] = new int[unParsed.length];
		for(int x = 0; x < version.length; x++){
			version[x] = Integer.parseInt(unParsed[x]);
		}
		return version;
	}
}