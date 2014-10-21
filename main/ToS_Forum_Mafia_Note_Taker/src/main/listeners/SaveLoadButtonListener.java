package main.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import main.LoadingHandler;
import main.Main;
import main.SavingHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
public class SaveLoadButtonListener implements ActionListener {
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Main.saveAs || e.getSource() == Main.save){
			int value;
			if(!(e.getSource() == Main.save && Main.fileSelected)) {value = Main.fc.showSaveDialog(Main.fc);}else{value = Main.fc.APPROVE_OPTION;}
			if(value == Main.fc.APPROVE_OPTION){
				File file = Main.fc.getSelectedFile();
				if(!file.getName().endsWith(".FMNT")){
					file = new File(file.toString()+".FMNT");
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
					xr.parse(new InputSource(new FileReader(Main.fc.getSelectedFile())));
				} catch (SAXException | IOException e1) {
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
			xr.parse(new InputSource(new FileReader(filepath)));
		} catch (FileNotFoundException e1) {
			System.out.println("ERROR: "+e1.getMessage());
			JOptionPane.showMessageDialog(Main.frame, "ERROR: Could not find the path specified", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (SAXException | IOException e1){
			e1.printStackTrace();
			JOptionPane.showMessageDialog(Main.frame, "ERROR WHILE LOADING", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}

