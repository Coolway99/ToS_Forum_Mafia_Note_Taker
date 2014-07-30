package assets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import main.Main;

import org.omg.CORBA.UserException;

public class SavingHandler{
	/**
	 * This variable is in place so I know what version of the save file I have up<br />
	 * The format is Major.Minor.Mini.Patch<br />
	 * <br />
	 * <b>Major</b> This is like a complete re-make of the file system<br />
	 * <b>Minor</b> Some tags might mean different things than before<br />
	 * <b>Mini</b> Added/renamed/removed some tags, shouldn't make saving/loading harder<br />
	 * <b>Patch</b> Minor fixes/Changes
	 */
	private static String saveV = "0.0.0";
	/**
	 * @see {@link assets.SavingHandler.saveV}
	 */
	private static String patch = "1";
	@SuppressWarnings("serial")
	public static boolean save(File filepath){
		try{
			if(filepath.exists()){
				if(!Main.notifyOverwrite(filepath.getName())){
					throw new UserException(){};
					} else {
						filepath.delete();
						filepath.createNewFile();
					}
				} else {filepath.createNewFile();}
			
			if(!filepath.canWrite()){Main.writeError(); throw new UserException() {};}
			FileWriter output = new FileWriter(filepath);
			Main.saveNoteString();
			output.write("<?xml version=\""+"1.0"+"\" "+"encoding=\""+output.getEncoding()+"\"?>");
			output.write("<beginSave version=\""+saveV+"\" patch=\""+patch+"\" >");
				output.write("<data>");
					output.write("<totalDays>"+Main.dayButtons.getDay()+"</totalDays>");
					output.write("<players>");
						output.write(parse(Main.players.getText()));
					output.write("</players>");
					output.write("<graveyard>");
						output.write(parse(Main.graveyard.getText()));
					output.write("</graveyard>");
					output.write("<roles>");
						output.write(parse(Main.roleList.getText()));
					output.write("</roles>");
				output.write("</data>");
				for(int x = 1; x <= Main.dayButtons.getDay(); x++){
					output.write("<number day=\""+Integer.toString(x)+"\" >");
						output.write("<day>");
							output.write("<notes>"+parse(Main.dayButtons.getDayString(x))+"</notes>");
						output.write("</day>");
						output.write("<night>");
							output.write("<notes>"+parse(Main.dayButtons.getNightString(x))+"</notes>");
						output.write("</night>");
					output.write("</number>");
				}
			output.write("</beginSave>");
			output.close();
			Main.frame.setTitle(Main.title + " - " + Main.fc.getSelectedFile().getName().split(".FMNT")[0]);
			return true;
		} catch (IOException e){
			Main.writeError();
			return false;
		} catch (UserException e){
			return false;
		}
	}
	public static String parse(String in){
		{
			String A[] = in.split("\n");
			String B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!NL!";
				B += A[x];
			}
			A = null;
			A = B.split(" ");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!S!";
				B += A[x];
			}
			A = null;
			A = B.split("<");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!lfBrkt!";
				B += A[x];
			}
			A = null;
			A = B.split(">");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!rtBrkt!";
				B += A[x];
			}
			A = null;
			A = B.split("&");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!ampt!";
				B += A[x];
			}
			A = null;
			A = B.split("\"");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!dbQuote!";
				B += A[x];
			}
			A = null;
			A = B.split("'");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!snQuote!";
				B += A[x];
			}
			A = null;
			A = B.split("\t");
			B = A[0];
			for(int x = 1; x < A.length; x++){
				B += "!tab!";
				B += A[x];
			}
			return B;
		}
	}
}
