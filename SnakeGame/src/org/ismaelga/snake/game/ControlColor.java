package org.ismaelga.snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.ufal.p3.ui.Controller;
import org.ufal.p3.ui.MainView;
import naomexer.Atores;
import naomexer.CobraHuman;
import naomexer.CorpoCobra;
import naomexer.Ovo;

public class ControlColor extends Controller {
	
	MainView m = new MainView();
	private static final Color UNKNOWN_COLOR = Color.gray;
	private HashMap<Class<?>, Color> colors;

	private static ControlColor instance = null;

	public static ControlColor getInstance() {

		if (instance == null) {
			instance = new ControlColor();
		}
		return instance;
	}
	
	public ControlColor(){
		super();
	}


	public void alterarCores() {
		final int numButtons = 4;
		final JFrame frameC = new JFrame("Config. colors");
		JRadioButton[] radioButtons = new JRadioButton[numButtons];
		final ButtonGroup group = new ButtonGroup();

		// Cobra Jogador
		radioButtons[0] = new JRadioButton("Player: Blue, Computer: Red, Egg: Green, Mongoose: Preto, Scarab: Yellow");
		radioButtons[0].setActionCommand("1");
		radioButtons[1] = new JRadioButton("Player: Red, Computer: Green, Egg: Blue, Mongoose: Gray, Scarab: Black");
		radioButtons[1].setActionCommand("2");
		radioButtons[2] = new JRadioButton("Player: Blue, Computer: Gray, Egg: Green, Mongoose: Gray, Scarab: Orange");
		radioButtons[2].setActionCommand("3");
		radioButtons[3] = new JRadioButton("All GRAY!!!");
		radioButtons[3].setActionCommand("4");

		for (int i = 0; i < numButtons; i++) {
			group.add(radioButtons[i]);
		}
		radioButtons[0].setSelected(true);

		JButton btn = new JButton("Alterar");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command = group.getSelection().getActionCommand();

				// Cobra Jogador
				if (command == "1") {
					setColor(CobraHuman.class, Color.blue);
					setColor(CobraAuto.class, Color.red);
					setColor(Ovo.class, Color.green);
					setColor(Mangusto.class, Color.black);
					setColor(Escaravelho.class, Color.yellow);
				} else if (command == "2") {
					setColor(CobraHuman.class, Color.red);
					setColor(CobraAuto.class, Color.green);
					setColor(Ovo.class, Color.blue);
					setColor(Mangusto.class, Color.gray);
					setColor(Escaravelho.class, Color.black);
				} else if (command == "3") {
					setColor(CobraHuman.class, Color.blue);
					setColor(CobraAuto.class, Color.gray);
					setColor(Ovo.class, Color.green);
					setColor(Mangusto.class, Color.gray);
					setColor(Escaravelho.class, Color.orange);
				} else if (command == "4") {
					setColor(CobraHuman.class, Color.gray);
					setColor(CobraAuto.class, Color.gray);
					setColor(Ovo.class, Color.gray);
					setColor(Mangusto.class, Color.gray);
					setColor(Escaravelho.class, Color.gray);
				}
				 
				//showStatus();

				m.menssagem("Color theme changed");
				return;
			}
		});

		JPanel box = new JPanel();
		JLabel label = new JLabel("Themes:");
		JPanel pane = new JPanel(new BorderLayout());
		box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
		box.add(label);

		for (int i = 0; i < numButtons; i++) {
			box.add(radioButtons[i]);
		}

		pane.add(box, BorderLayout.PAGE_START);
		pane.add(btn, BorderLayout.PAGE_END);

		frameC.add(pane);
		frameC.pack();
		frameC.setVisible(true);
	}

}
