package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Options extends JFrame {
	private static final long serialVersionUID = 6276186283926336030L;
	
	private final JTextField optionsFont = new JTextField("arial");
	private final JButton optionsFontSelect = new JButton("Set Font");
	private final JTextField optionsNumberPlayers = new JTextField("20");
	private final JButton optionsSetPlayerNumbers = new JButton("Set # of Players");
	private final JButton optionsNumberToggle = new JButton("Toggle Player Numbers");
	private final JButton optionsDefault = new JButton("Reset to Default");
	
	public Options(){
		super("Options");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(400, 200);
		GridBagLayout layout = new GridBagLayout();
		{
			int[] temp = new int[4];
			for(int x = 0; x < temp.length; x++){
				temp[x] = 200/temp.length;
			}
			layout.rowHeights = temp;
			temp = new int[4];
			for(int x = 0; x < temp.length; x++){
				temp[x] = 400/temp.length;
			}
			layout.columnWidths = temp;
		}
		this.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.insets  = new Insets(0, 0, 0, 5);
		this.add(optionsFont, c.clone());
		c.gridx = 3;
		c.insets = new Insets(0, 5, 0, 0);
		this.add(optionsFontSelect, c.clone());
		c.gridy = 1;
		c.gridx = 0;
		c.insets = new Insets(1, 0, 0, 5);
		this.add(optionsNumberPlayers, c.clone());
		c.gridx = 2;
		c.insets = new Insets(1, 5, 0, 0);
		this.add(optionsSetPlayerNumbers, c.clone());
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 0, 5);
		this.add(optionsNumberToggle, c.clone());
		c.insets = new Insets(0, 5, 0, 0);
		c.gridx = 2;
		this.add(optionsDefault, c);
		
		optionsFont.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				optionsFontSelect.doClick();
			}
		});
		optionsFontSelect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Main.font = optionsFont.getText();
				Main.reparseAll();
			}
		});
		optionsNumberPlayers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				optionsSetPlayerNumbers.doClick();
			}
		});
		optionsSetPlayerNumbers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
					Main.numOfPlayers = Integer.parseInt(optionsNumberPlayers.getText());
					Main.reparseAll();
				} catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(Main.frame, "That is not a valid number!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		optionsNumberToggle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Main.numbersShown = !Main.numbersShown;
				Main.initLayout();
			}
		});
		optionsDefault.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				optionsFont.setText("arial");
				optionsNumberPlayers.setText("20");
				Main.numbersShown = true;
				optionsSetPlayerNumbers.doClick();
				optionsFontSelect.doClick();
			}
		});
	}
	public void setFont(String font){
		this.optionsFont.setText(font);
		this.optionsFontSelect.doClick();
	}
	public void setPlayers(String num){
		if(!num.equals("0")){
			this.optionsNumberPlayers.setText(num);
			this.optionsSetPlayerNumbers.doClick();
			if(!Main.numbersShown){
				Main.numbersShown = true;
				Main.initLayout();
			}
		} else {
			if(Main.numbersShown){
				Main.numbersShown = false;
				Main.initLayout();
			}
		}
	}
}
