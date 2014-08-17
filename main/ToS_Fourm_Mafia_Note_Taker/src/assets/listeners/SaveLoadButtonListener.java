package assets.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.Main;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import assets.LoadingHandler;
import assets.SavingHandler;
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
}

