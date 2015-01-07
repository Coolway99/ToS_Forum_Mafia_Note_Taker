package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.omg.CORBA.UserException;

public class SavingHandler{
	/**
	 * This variable is in place so I know what version of the save file I have up<br />
	 * The format is Major.Minor.Mini.Patch<br />
	 * <br />
	 * <b>Major</b> This is like a complete re-make of the file system<br />
	 * <b>Minor</b> Some tags might mean different things than before<br />
	 * <b>Mini</b> Added/renamed/removed some tags, shouldn't make saving/loading harder<br />
	 * <b>Patch</b> Minor fixes/Changes<br />
	 * <br />
	 * The change sets all the numbers lower than it to 0<br />
	 * <br />
	 * <b>EXAMPLES:</b> If its version 1.2.3.4 and Major goes up by 1, then it becomes 2.0.0.0<br />
	 * If it is 1.2.3.4 and Patch goes up by 1, then it is 1.2.3.5<br />
	 * If it is 1.2.3.4 and Minor goes up by 1, then it becomes 1.3.0.0<br />
	 * ect.
	 */
	private static String saveV = "0.0.2";
	/**
	 * @see {@link #saveV}
	 */
	private static String patch = "0";
	@SuppressWarnings("serial")
	public static boolean save(File filepath){
		try{
			if(filepath.exists()){
				if(!Main.notifyOverwrite(filepath.getName())){
					throw new UserException("User canceled overwrite"){};
					}
				filepath.renameTo(new File(filepath.toString() + "_OLD"));
				filepath.createNewFile();
				} else {filepath.createNewFile();}
			
			if(!filepath.canWrite()){Main.writeError("No write permissions"); throw new UserException("Unable to write"){};}
			FileWriter output = new FileWriter(filepath);
			Main.saveNoteString();
			output.write("<?xml version=\""+"1.0"+"\" "+"encoding=\""+output.getEncoding()+"\"?>");
			output.write("<beginSave version=\""+saveV+"\" patch=\""+patch+"\" >");
				output.write("<data>");
					output.write("<totalDays>"+Main.dayButtons.getDay()+"</totalDays>");
					output.write("<players>");
						output.write(parse(Main.playerArea.origString));
					output.write("</players>");
					output.write("<roles>");
						output.write(parse(Main.roleList.origString));
					output.write("</roles>");
					output.write("<generalNotes>");
						output.write(parse(Main.secondaryListener.genNoteArea.getText()));
					output.write("</generalNotes>");
					output.write("<font>");
						output.write(parse(Main.secondaryListener.optionFrame.optionsFont.getText()));
					output.write("</font>");
					output.write("<playerNum>");
						output.write(parse(Main.secondaryListener.optionFrame.optionsNumberPlayers.getText()));
					output.write("</playerNum>");
				output.write("</data>");
				for(int x = 1; x <= Main.dayButtons.getDay(); x++){
					output.write("<number day=\""+Integer.toString(x)+"\" >");
						output.write("<day>");
							output.write("<notes>"+parse(Main.dayButtons.getDayString(x))+"</notes>");
						output.write("</day>");
						output.write("<night>");
							output.write("<notes>"+parse(Main.dayButtons.getNightString(x))+"</notes>");
						output.write("</night>");
						output.write("<whispers>");
							output.write(parse(Main.dayButtons.getWhisperString(x)));
						output.write("</whispers>");
					output.write("</number>");
				}
			output.write("</beginSave>");
			output.close();
			Main.frame.setTitle(Main.title + " - " + Main.fc.getSelectedFile().getName().split(".FMNT")[0]);
			if(new File(filepath.toString() + "_OLD").exists()){new File(filepath.toString() + "_OLD").delete();}
			return true;
		} catch (IOException e){
			Main.writeError("IOException");
			if(filepath.exists()){filepath.delete();}
			new File(filepath.toString() + "_OLD").renameTo(filepath);
			return false;
		} catch (UserException e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	public static String parse(String in){
		String B = in;
		for(int y = 0; y < Main.parseList.length; y++){
			try{
				B = B.replaceAll((Main.parseList[y] != "[" && Main.parseList[y] != "]")? Main.parseList[y] : "\\" + Main.parseList[y]
						,Main.unParseList[y]);
			} catch (NullPointerException e){System.out.print("Found no data");}
		} return B;
	}
}
