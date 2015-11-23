package org.ufal.p3.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.ismaelga.snake.game.CobraAuto;
import org.ismaelga.snake.game.Escaravelho;
import org.ismaelga.snake.game.Facade;
import org.ismaelga.snake.game.Jogo;
import org.ismaelga.snake.game.Mangusto;
import org.ismaelga.snake.game.Terreno;

import naomexer.Atores;
import naomexer.CobraHuman;
import naomexer.CorpoCobra;
import naomexer.Ovo;

/**
 * Main class for the game Classe responsavel por toda interface, por certas
 * componentes do jogo em si, de toda a configuração possivel e pelas utilidades
 * disponibilizadas.
 * 
 * @Ismael @06-2010
 * @Gabriel @11-2015
 */

@SuppressWarnings("serial")
public class Controller extends JFrame {

	MainView m = new MainView();
	private static final int ALTURA = 50;
	private static final int LARGURA = 50;
	private static final Color EMPTY_COLOR = Color.white;
	private static final Color UNKNOWN_COLOR = Color.gray;
	private Model fieldView;
	private HashMap<Class<?>, Color> colors;
	private Timer timer;
	private Jogo jogo;
	private boolean pausa;
	private JLabel label1;
	private int altTerreno;
	private int larTerreno;
	
	public Controller() {
		// initialise instance variables
		super("Snake");
		// criar uma grelha com o tamnhado predefenido
		fieldView = new Model(51, 51);

		// Tamanho predefenido
		altTerreno = ALTURA;
		larTerreno = LARGURA;

		colors = new HashMap<Class<?>, Color>();
		setColor(CobraHuman.class, Color.blue);
		setColor(CobraAuto.class, Color.red);
		setColor(Ovo.class, Color.green);
		setColor(Mangusto.class, Color.black);
		setColor(Escaravelho.class, Color.yellow);

		Container c = getContentPane();
		c.add(fieldView, BorderLayout.CENTER);

		label1 = new JLabel("Start a new game");

		Panel sul = new Panel(new FlowLayout());
		sul.add(label1);
		
		
		
		c.add(sul, BorderLayout.SOUTH);
		criaMenu();

		Teclado teclado = new Teclado();
		this.addKeyListener(teclado);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		pausa = true;
		setVisible(true);
	}

	public void criaMenu() {
		
		JMenuBar barra = new JMenuBar();
		JMenu menuJogador = new JMenu("Player");
		JMenu menuConfigurar = new JMenu("Settings");
		JMenu menuAjuda = new JMenu("Help");
		JMenuItem JogoNovo = new JMenuItem("Novo Jogo");
		JMenuItem JogoGravar = new JMenuItem("Gravar Jogo");
		JMenuItem JogoCarregar = new JMenuItem("Carregar Jogo");
		JMenuItem JogoParar = new JMenuItem("Stop the game");
		JMenuItem JogoContinuar = new JMenuItem("Resume the game");
		JMenuItem JogoSair = new JMenuItem("Exit");
		JMenu menuJogo = new JMenu("Game");
		
		menuJogo.add(JogoNovo);
		menuJogo.add(JogoGravar);
		menuJogo.add(JogoCarregar);
		menuJogo.add(JogoParar);
		menuJogo.add(JogoContinuar);
		menuJogo.add(JogoSair);
		// adiciona menu de jogo á barra
		barra.add(menuJogo);
		// Acções de cada item do menu Jogo
		// Novo jogo
		JogoNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				novoJogo();
			}
		});
		// Gravar Jogo
		JogoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				guardarJogo();
			}
		});
		// Carregar Jogo
		JogoCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				try {
					carregaJogo();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		// Parar Jogo
		JogoParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
			}
		});
		// Continuar Jogo
		JogoContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = false;
				if (jogo != null)
					proximoPasso();
			}
		});
		// Sair
		JogoSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// Items de Jogador
		JMenuItem JogadorVer = new JMenuItem("Player name");
		JMenuItem JogadorVerP = new JMenuItem("Player points");
		// adiciona items ao menu Jogador
		menuJogador.add(JogadorVer);
		menuJogador.add(JogadorVerP);
		// adiciona á barra o menu Jogador
		barra.add(menuJogador);
		// Acções de cada item do menu Jogador
		// Nome Jogador
		JogadorVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				if (jogo != null)
					m.menssagem("Player name:  " + jogo.getNomeJogador()/*Facade.getInstancia().getNome()*/);
				else
					m.menssagem("Game not yet started!");
			}
		});
		// Pontos
		JogadorVerP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				if (jogo != null)
					m.menssagem("You have  " + /*Facade.getInstancia().getPontos()*/ jogo.getPontos() + "  points");
				else
					m.menssagem("Game not yet started!");
			}
		});
	
		// Items de Configurador
		JMenuItem altLargura = new JMenuItem("Change widht");
		JMenuItem altAltura = new JMenuItem("Change hight");
		JMenuItem altCorEnte = new JMenuItem("Change color theme");
		menuConfigurar.add(altLargura);
		menuConfigurar.add(altAltura);
		menuConfigurar.add(altCorEnte);
		// adiciona á barra o menu Configurador
		barra.add(menuConfigurar);
		// Acções de cada item do menu Jogador

		// Alterar altura
		altAltura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				alterarAltura();
			}
		});
		// Alterar largura
		altLargura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				alterarLargura();
			}
		});
		// Alterar cores
		altCorEnte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				Facade.getInstancia().alterarCores();
			}
		});
		
		// Items de Ajuda
		JMenuItem AjudaTeclas = new JMenuItem("Keys help");
		JMenuItem Instruc = new JMenuItem("Game Instructions");
		// adiciona items ao menu Ajuda
		menuAjuda.add(AjudaTeclas);
		menuAjuda.add(Instruc);
		// adiciona á barra o menu Ajuda
		barra.add(menuAjuda);
		// Acções de cada item do menu Jogador
		// Quais as teclas a usar no jogo
		AjudaTeclas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				m.ajudaTeclas();
			}
		});
		// Quais as teclas a usar no jogo
		Instruc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				m.instrucoes();
			}
		});

		this.setJMenuBar(barra);
	}
	
	public void alterarAltura() {
		Object[] possibilidades = { "40", "50", "60", "70" };
		String actual = " " + altTerreno;
		String strAlt = (String) JOptionPane.showInputDialog(this, "New hight:\n" + "default: 50", "Change!",
				JOptionPane.PLAIN_MESSAGE, null, possibilidades, actual.trim());
		if (strAlt != null) {
			altTerreno = Integer.parseInt(strAlt);
			m.menssagem("Alterado para: " + altTerreno + "\nInicie um novo jogo!");
		}
	}

	public void alterarLargura() {
		Object[] possibilidades = { "40", "50", "60", "70" };
		String actual = " " + larTerreno;
		String strLar = (String) JOptionPane.showInputDialog(this, "New widht:\n" + "default: 50", "Change!",
				JOptionPane.PLAIN_MESSAGE, null, possibilidades, actual.trim());
		if (strLar != null) {
			larTerreno = Integer.parseInt(strLar);
			m.menssagem("Changed to: " + larTerreno + "\nStart a new game!");
		}
	}
	
	/**
	 * Classe para agendar tarefas. É responsável por gerar o tempo entre os
	 * passos
	 */
	class TempTask extends TimerTask {
		/**
		 * Manda mensagens de x em x tempo para executar um novo passo
		 */
		public void run() {
			timer.cancel();
		
			if (jogo.isFim())
				fim();
			else if (pausa == false) {
				//Facade.getInstancia().mandaActuar();
				jogo.mandaActuar();
				showStatus();
				proximoPasso();
			}
		}
	}

	/**
	 * Classe responsável pela leitura do teclado
	 */
	class Teclado implements KeyListener {

		public void keyPressed(KeyEvent e) {
			leitorDeTecla(e);
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
	}
	
	private void leitorDeTecla(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		case 37:
			jogo.moveCobraEsquerda();
			break;
		case 38:
			jogo.moveCobraCima();
			break;
		case 39:
			jogo.moveCobraDireita();
			break;
		case 40:
			jogo.moveCobraBaixo();
			break;
		case 80:
			pausa_continuar();
			break;
		}
	}

	public void setColor(Class<?> actorClass, Color color) {
		colors.put(actorClass, color);
	}

	private Color getColor(Atores actor) {
		Color col = (Color) colors.get(actor.getClass());
		if (actor instanceof CorpoCobra)
			col = (Color) colors.get(((CorpoCobra) actor).getCobraPertencente().getClass());

		if (col == null) {
			return UNKNOWN_COLOR;
		} else {
			return col;
		}
	}

	public void showStatus() {
		showStatus(jogo.getTerreno());
	}

	private void showStatus(Terreno terreno) {
		if (!isVisible())
			setVisible(true);
	
		label1.setText("<html><b>" + jogo.getNomeJogador() + "</b> -&nbsp;&nbsp; Points: <b>" + jogo.getPontos() +
				"</b>&nbsp;&nbsp;Level: <b>" + jogo.getNivel() + "</b>&nbsp;&nbsp;Lifes: <b>" + jogo.getVidas() + 
				"</b>&nbsp;&nbsp;</b></html>");
		
		fieldView.preparePaint();
		
		for (int lin = 0; lin <= terreno.getAltura(); lin++) {
			for (int col = 0; col <= terreno.getLargura(); col++) {
				Object actor = terreno.getObjectAt(lin, col);
				if (actor != null) {
					fieldView.drawMark(col, lin, getColor((Atores) actor));
				} else {
					fieldView.drawMark(col, lin, EMPTY_COLOR);
				}
			}
		}
		
		fieldView.repaint();
	}

	public void proximoPasso() {
		timer = new Timer();
		timer.schedule(new TempTask(), jogo.getTempoPasso());
	}

	/**
	 * Pausa o jogo no passo actual.
	 */
	private void pausa_continuar() {
		pausa = !pausa;
		proximoPasso();
	}

	public void novoJogo() {
		String nome = (String) JOptionPane.showInputDialog(this, "New game\n" + "Name:", "Ismael's Snake",
				JOptionPane.PLAIN_MESSAGE, null, null, "here your name");

		if ((nome != null) && !nome.equals("") && !nome.equals("here your name")) {
			jogo = new Jogo(altTerreno, larTerreno, nome);
			fieldView.setTamanho(larTerreno + 1, altTerreno + 1);
			this.pack();
			showStatus();
			pausa = true;
		}
	}

	public void guardarJogo() {
		pausa = true;
		String nomeFicheiro = (String) JOptionPane.showInputDialog(this, "Save Game\n" + "Name this game:", "SnakeGame",
				JOptionPane.PLAIN_MESSAGE, null, null, jogo.getNomeJogador());
		if ((nomeFicheiro != null) && !nomeFicheiro.equals(""))
			try {
				FileOutputStream fil = new FileOutputStream(nomeFicheiro);
				ObjectOutputStream out = new ObjectOutputStream(fil);
				out.writeObject(jogo);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void carregaJogo() throws IOException {
		JFileChooser escolhedor = new JFileChooser();
		int estado = escolhedor.showOpenDialog(null);
		if (estado == 0) {
			try {
				FileInputStream fil = new FileInputStream(escolhedor.getSelectedFile());
				ObjectInputStream in = new ObjectInputStream(fil);
				jogo = (Jogo) in.readObject();
				in.close();
			} catch (ClassNotFoundException e) {
				throw new IOException("Problem des-serializing items!");
			}
			showStatus(jogo.getTerreno());
		}

		this.pack();
		showStatus();
	}

	public void fim() {
		JOptionPane.showConfirmDialog(this, "Game Over", "Snake", JOptionPane.CLOSED_OPTION);
	}

}