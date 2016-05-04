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
import javax.swing.WindowConstants;

public class Options extends JFrame{
	private static final long serialVersionUID = 6276186283926336030L;

	public final JTextField optionsFont = new JTextField("arial");
	private final JButton optionsFontSelect = new JButton("Set Font");
	public final JTextField optionsNumberPlayers = new JTextField("20");
	private final JButton optionsSetPlayerNumbers = new JButton("Set # of Players");
	private final JButton optionsNumberToggle = new JButton("Toggle Player Numbers");
	private final JButton optionsDefault = new JButton("Reset to Default");

	public Options(){
		super("Options");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setSize(400, 200);
		GridBagLayout layout = new GridBagLayout();

		layout.rowWeights = new double[4];
		layout.columnWeights = new double[4];

		this.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.insets = new Insets(0, 0, 0, 5);
		this.add(this.optionsFont, c);
		c.gridx = 3;
		c.insets = new Insets(0, 5, 0, 0);
		this.add(this.optionsFontSelect, c);
		c.gridy = 1;
		c.gridx = 0;
		c.insets = new Insets(1, 0, 0, 5);
		this.add(this.optionsNumberPlayers, c);
		c.gridx = 2;
		c.insets = new Insets(1, 5, 0, 0);
		this.add(this.optionsSetPlayerNumbers, c);
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 0, 5);
		this.add(this.optionsNumberToggle, c);
		c.insets = new Insets(0, 5, 0, 0);
		c.gridx = 2;
		this.add(this.optionsDefault, c);

		this.optionsFont.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Options.this.optionsFontSelect.doClick();
			}
		});
		this.optionsFontSelect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Main.font = Options.this.optionsFont.getText();
				Main.reparseAll();
			}
		});
		this.optionsNumberPlayers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Options.this.optionsSetPlayerNumbers.doClick();
			}
		});
		this.optionsSetPlayerNumbers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try {
					Main.numOfPlayers = Integer
							.parseInt(Options.this.optionsNumberPlayers.getText());
					Main.reparseAll();
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(Main.frame, "That is not a valid number!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.optionsNumberToggle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Main.numbersShown = !Main.numbersShown;
				if(!Main.numbersShown){
					Options.this.optionsNumberPlayers.setText("0");
				} else {
					Options.this.optionsNumberPlayers.setText("20");
					Options.this.optionsSetPlayerNumbers.doClick();
				}
				Main.initLayout();
			}
		});
		this.optionsDefault.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Options.this.optionsFont.setText("arial");
				Options.this.optionsNumberPlayers.setText("20");
				Main.numbersShown = true;
				Options.this.optionsSetPlayerNumbers.doClick();
				Options.this.optionsFontSelect.doClick();
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
		} else if(Main.numbersShown){
			Main.numbersShown = false;
			Main.initLayout();
		}
	}
}