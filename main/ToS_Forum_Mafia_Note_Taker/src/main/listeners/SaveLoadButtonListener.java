package main.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import main.LoadingHandler;
import main.Main;
import main.SavingHandler;

public class SaveLoadButtonListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == Main.saveAs || e.getSource() == Main.save){
			int value;
			//If this source != Main.save AND we have not selected a file
			if((e.getSource() != Main.save) && (!Main.fileSelected)){
				value = Main.fc.showSaveDialog(Main.frame);
			} else {
				value = JFileChooser.APPROVE_OPTION;
			}
			if(value == JFileChooser.APPROVE_OPTION){
				File file = Main.fc.getSelectedFile();
				if( !file.getName().toUpperCase().endsWith(".FMNT")) {
					file = new File(file.toString() + ".FMNT");
				}
				SavingHandler.save(file);
				Main.fileSelected = true;
			}
		}
		if(e.getSource() == Main.load){
			int value = Main.fc.showOpenDialog(Main.fc);
			if(value == Main.fc.APPROVE_OPTION){
				try {
					XMLReader xr = XMLReaderFactory.createXMLReader();
					LoadingHandler handler = new LoadingHandler();
					xr.setContentHandler(handler);
					FileReader f = new FileReader(Main.fc.getSelectedFile());
					xr.parse(new InputSource(f));
					f.close();
				} catch(SAXException | IOException e1){
					e1.printStackTrace();
				}
			}
		}
	}

	public void load(File filepath){
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			LoadingHandler handler = new LoadingHandler();
			xr.setContentHandler(handler);
			FileReader f = new FileReader(filepath);
			xr.parse(new InputSource(f));
			f.close();
		} catch(FileNotFoundException e1){
			System.out.println("ERROR: "+e1.getMessage());
			JOptionPane.showMessageDialog(Main.frame, "ERROR: Could not find the path specified",
					"ERROR", 0);
		} catch(SAXException | IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(Main.frame, "ERROR WHILE LOADING", "ERROR", 0);
		}
	}
}