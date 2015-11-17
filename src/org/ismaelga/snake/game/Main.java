package org.ismaelga.snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javax.swing.JTabbedPane;
import naomexer.Atores;
import naomexer.CobraHuman;
import naomexer.CorpoCobra;
import naomexer.Ovo;
import naomexer.Recorde;

/**
 * Main class for the game Classe responsavel por toda interface, por certas
 * componentes do jogo em si, de toda a configura��o possivel e pelas utilidades
 * disponibilizadas.
 * 
 * @Ismael @06-2010
 */

public class Main extends JFrame {

	private static final Color EMPTY_COLOR = Color.white;
	private static final Color UNKNOWN_COLOR = Color.gray;
	private static final int ALTURA = 50;
	private static final int LARGURA = 50;
	private FieldView fieldView;
	// A map for storing colors for participants in the simulation
	private HashMap<Class<?>, Color> colors;
	// A statistics object computing and storing simulation information
	// -----private FieldStats stats;

	private Timer timer;
	private Jogo jogo;
	private boolean pausa;
	private JLabel label1;
	private int altTerreno;
	private int larTerreno;

	public Main() {
		// initialise instance variables
		super("Ismael's Snake");
		// criar uma grelha com o tamnhado predefenido
		fieldView = new FieldView(51, 51);

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

	/**
	 * Classe para agendar tarefas. � respons�vel por gerar o tempo entre os
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
				jogo.mandaActuar();
				showStatus();
				proximoPasso();
			}
		}
	}

	/**
	 * Classe respons�vel pela leitura do teclado
	 */
	class Teclado implements KeyListener {

		public void keyPressed(KeyEvent e) {
			// JOptionPane.showMessageDialog(null, "Uma tecla foi
			// pressionada!");
			leitorDeTecla(e);
		}

		public void keyReleased(KeyEvent e) {
			// c�digo aqui
		}

		public void keyTyped(KeyEvent e) {
			// c�digo aqui
		}
	}

	private void leitorDeTecla(KeyEvent e) {
		// if(jogo != null)
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

	private void setColor(Class<?> actorClass, Color color) {
		colors.put(actorClass, color);
	}

	private Color getColor(Atores actor) {
		Color col = (Color) colors.get(actor.getClass());
		if (actor instanceof CorpoCobra)
			col = (Color) colors.get(((CorpoCobra) actor).getCobraPertencente().getClass());

		if (col == null) {
			// no color defined for this class
			return UNKNOWN_COLOR;
		} else {
			return col;
		}
	}

	private void showStatus() {
		showStatus(jogo.getTerreno());
	}

	private void showStatus(Terreno terreno) {
		if (!isVisible())
			setVisible(true);

		label1.setText("<html><b>" + jogo.getNomeJogador() + "</b> -&nbsp;&nbsp; Points: <b>" + jogo.getPontos()
				+ "</b>&nbsp;&nbsp;Level: <b>" + jogo.getNivel() + "</b>&nbsp;&nbsp;Lifes: <b>" + jogo.getVidas()
				+ "</b>&nbsp;&nbsp;Eggs: <b>" + jogo.getTipoOvo() + "</b></html>");
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

	private void criaMenu() {
		// Declarar barra
		JMenuBar barra = new JMenuBar();
		// Declara barra de menus[nomes]
		JMenu menuJogo = new JMenu("Game");
		JMenu menuJogador = new JMenu("Player");
		JMenu menuConfigurar = new JMenu("Settings");
		JMenu menuAjuda = new JMenu("Help");
		// -----------------------------------------------------
		// Items de Jogo
		JMenuItem JogoNovo = new JMenuItem("Novo Jogo");
		JMenuItem JogoGravar = new JMenuItem("Gravar Jogo");
		JMenuItem JogoCarregar = new JMenuItem("Carregar Jogo");
		JMenuItem JogoParar = new JMenuItem("Stop the game");
		JMenuItem JogoContinuar = new JMenuItem("Resume the game");
		JMenuItem JogoSair = new JMenuItem("Exit");
		// adiciona os items ao menu Jogo
		menuJogo.add(JogoNovo);
		menuJogo.add(JogoGravar);
		menuJogo.add(JogoCarregar);
		menuJogo.add(JogoParar);
		menuJogo.add(JogoContinuar);
		menuJogo.add(JogoSair);
		// adiciona menu de jogo � barra
		barra.add(menuJogo);
		// Ac��es de cada item do menu Jogo
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
		// -------------------------------------------------------------------
		// Items de Jogador
		JMenuItem JogadorVer = new JMenuItem("Player name");
		JMenuItem JogadorVerP = new JMenuItem("Player points");
		JMenuItem Ranking = new JMenuItem("Ranking");
		// adiciona items ao menu Jogador
		menuJogador.add(JogadorVer);
		menuJogador.add(JogadorVerP);
		menuJogador.add(Ranking);
		// adiciona � barra o menu Jogador
		barra.add(menuJogador);
		// Ac��es de cada item do menu Jogador
		// Nome Jogador
		JogadorVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				if (jogo != null)
					menssagem("Player name:  " + jogo.getNomeJogador());
				else
					menssagem("Game not yet started!");
			}
		});
		// Pontos
		JogadorVerP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				if (jogo != null)
					menssagem("You have  " + jogo.getPontos() + "  points");
				else
					menssagem("Game not yet started!");
			}
		});
		// Ranking
		Ranking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				try {
					lerRecordes();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		// ------------------------------------------------------------------
		// Items de Configurador
		JMenuItem altLargura = new JMenuItem("Change widht");
		JMenuItem altAltura = new JMenuItem("Change hight");
		JMenuItem altCorEnte = new JMenuItem("Change color theme");
		menuConfigurar.add(altLargura);
		menuConfigurar.add(altAltura);
		menuConfigurar.add(altCorEnte);
		// adiciona � barra o menu Configurador
		barra.add(menuConfigurar);
		// Ac��es de cada item do menu Jogador

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
				alterarCores();
			}
		});
		// ------------------------------------------------------------------
		// Items de Ajuda
		JMenuItem AjudaTeclas = new JMenuItem("Keys help");
		JMenuItem Instruc = new JMenuItem("Game Instructions");
		// adiciona items ao menu Ajuda
		menuAjuda.add(AjudaTeclas);
		menuAjuda.add(Instruc);
		// adiciona � barra o menu Ajuda
		barra.add(menuAjuda);
		// Ac��es de cada item do menu Jogador
		// Quais as teclas a usar no jogo
		AjudaTeclas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				ajudaTeclas();
			}
		});
		// Quais as teclas a usar no jogo
		Instruc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausa = true;
				instrucoes();
			}
		});

		this.setJMenuBar(barra);
	}

	private void proximoPasso() {
		timer = new Timer();
		timer.schedule(new TempTask(), jogo.getTempoPasso());
	}

	private void fim() {
		int n = JOptionPane.showConfirmDialog(this, "Save record?", "Ismael's Snake", JOptionPane.YES_NO_OPTION);

		if (n == JOptionPane.YES_OPTION) {
			try {
				guardarRecorde();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void guardarRecorde() throws IOException {
		List<Recorde> recordes = new ArrayList<Recorde>();
		List<Recorde> ordrec = new ArrayList<Recorde>();
		try {
			File f = new File("ranking");
			if (f.exists()) {
				FileInputStream fil = new FileInputStream("ranking");

				// se o numero de bytes no ficheiro � maior q zero
				if (fil.available() > 0) {
					ObjectInputStream in = new ObjectInputStream(fil);
					recordes = (List<Recorde>) in.readObject();
					in.close();

					if (recordes.size() > 0) {
						boolean adicionado = false;
						for (int i = 0; i < recordes.size(); i++) {
							if (((Recorde) recordes.get(i)).getPontos() < jogo.getPontos() && !adicionado) {
								ordrec.add(new Recorde(jogo.getNomeJogador(), jogo.getPontos(), jogo.getNivel()));
								adicionado = true;
								i--;
							} else if (ordrec.size() < 10) {
								ordrec.add(recordes.get(i));
							}
						}
						if (!adicionado && (ordrec.size() < 10)) {
							ordrec.add(new Recorde(jogo.getNomeJogador(), jogo.getPontos(), jogo.getNivel()));
							adicionado = true;
						}
						if (!adicionado) {
							menssagem("N�o entrou no ranking!");
						}

					} else {
						recordes.add(new Recorde(jogo.getNomeJogador(), jogo.getPontos(), jogo.getNivel()));
					}
					try {
						FileOutputStream filo = new FileOutputStream("ranking");
						ObjectOutputStream out = new ObjectOutputStream(filo);
						out.writeObject(ordrec);
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					recordes.add(new Recorde(jogo.getNomeJogador(), jogo.getPontos(), jogo.getNivel()));
					try {
						FileOutputStream filo = new FileOutputStream("ranking");
						ObjectOutputStream out = new ObjectOutputStream(filo);
						out.writeObject(recordes);
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				recordes.add(new Recorde(jogo.getNomeJogador(), jogo.getPontos(), jogo.getNivel()));
				try {
					FileOutputStream filo = new FileOutputStream("ranking");
					ObjectOutputStream out = new ObjectOutputStream(filo);
					out.writeObject(recordes);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			lerRecordes();
		} catch (ClassNotFoundException e) {
			throw new IOException("Problema a des-serializar os items!");
		}
	}

	private void lerRecordes() throws IOException {
		List<?> recordes = new ArrayList<Object>();
		try {
			File f = new File("ranking");
			if (f.exists()) {
				FileInputStream fil = new FileInputStream("ranking");

				// se o numero de bytes no ficheiro � maior q zero
				if (fil.available() > 0) {
					ObjectInputStream in = new ObjectInputStream(fil);
					recordes = (List<?>) in.readObject();
					in.close();

					Recorde rec;
					String strg = "<html><table border =0><tr><td>&nbsp;NOME&nbsp;<td>&nbsp;NIVEL&nbsp;<td>&nbsp;PONTOS&nbsp;</tr>";
					for (int i = 0; i < recordes.size(); i++) {
						rec = (Recorde) recordes.get(i);
						if ((i % 2) == 0)
							strg = strg + "<tr bgcolor=\"gray\"><td>";
						else
							strg = strg + "<tr bgcolor=\"white\"><td>";

						strg = strg + rec.getNome() + "&nbsp;<td>&nbsp;&nbsp;&nbsp;&nbsp;" + rec.getNivel()
								+ "<td>&nbsp;&nbsp;&nbsp;&nbsp;" + rec.getPontos() + "</tr>";
					}
					strg = strg + "</table></html>";
					JOptionPane.showMessageDialog(this, strg, "Ismael's Snake", JOptionPane.PLAIN_MESSAGE);
				} else {
					menssagem("No record saved");
				}
			} else {
				menssagem("No record saved");
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Problem des-serializing os items!");
		}
	}

	/**
	 * Pausa o jogo no passo actual.
	 */
	private void pausa_continuar() {
		pausa = !pausa;
		proximoPasso();
	}

	private void novoJogo() {
		String nome = (String) JOptionPane.showInputDialog(this, "New game\n" + "Name:", "Ismael's Snake",
				JOptionPane.PLAIN_MESSAGE, null, null, "here your name");

		if ((nome != null) && !nome.equals("") && !nome.equals("here your name")) {
			jogo = new Jogo(altTerreno, larTerreno, nome);
			// fieldView = new FieldView(altTerreno + 1, larTerreno + 1);
			fieldView.setTamanho(larTerreno + 1, altTerreno + 1);
			this.pack();
			showStatus();
			pausa = true;
		}
	}

	private void alterarAltura() {
		Object[] possibilidades = { "40", "50", "60", "70" };
		String actual = " " + altTerreno;
		String strAlt = (String) JOptionPane.showInputDialog(this, "New hight:\n" + "default: 50", "Change!",
				JOptionPane.PLAIN_MESSAGE, null, possibilidades, actual.trim());
		if (strAlt != null) {
			altTerreno = Integer.parseInt(strAlt);
			menssagem("Alterado para: " + altTerreno + "\nInicie um novo jogo!");
		}
	}

	private void alterarLargura() {
		Object[] possibilidades = { "40", "50", "60", "70" };
		String actual = " " + larTerreno;
		String strLar = (String) JOptionPane.showInputDialog(this, "New widht:\n" + "default: 50", "Change!",
				JOptionPane.PLAIN_MESSAGE, null, possibilidades, actual.trim());
		if (strLar != null) {
			larTerreno = Integer.parseInt(strLar);
			menssagem("Changed to: " + larTerreno + "\nStart a new game!");
		}
	}

	private void alterarCores() {
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
				if (jogo != null)
					showStatus();

				menssagem("Color theme changed");
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

	private void instrucoes() {
		String strCobras = "<html> <u><b>Cobras do utilizador e autom�tica:</b></u><br><br><br><br><b>1-</b>Movem-se para a esquerda, direita, cima e baixo;<br><br><b>2-</b> A cobra autom�tica tem movimentos aleat�rios autom�ticamente criados pelo PC;<br><br><b>3-</b>A cobra do utilizador tem movimentos controlados pelo utilizador;<br><br><b>4-</b>As cobras comem entes que n�o gostam de as comer (ovos e escaravelho);<br><br><b>5-</b>Movem-se de 2 em 2 segundos na posi��o para que est�o viradas ou a cobra do utilizador move-se de acordo com a vontade do utilizador;<br><br><b>6-</b>Ocupa 4 posi��es incialmente;<br><br><b>7-</b>Se bater contra si pr�pria, outras cobras ou contra a parede morre;</html>";
		String strEscaravelho = "<html> <u><b>Scarab:</b></u><br><br><br><br><b>1-</b>Tem movimentos lentos na horizontal, verical e diagonal;<br><br><b>2-</b>Demora 3 passos de simula��o a movimentar-se;<br><br><b>3-</b>Quando � comido por uma cobra, a mesma ganha 3 pontos;<br><br><b>4-</b>Morre quando atinge 50 passos de simla��o ou quando se encontra com qualquer outro ente;<br></html>";
		String strMangusto = "<html> <u><b>Mongoose:</b></u><br><br><br><br><b>1-</b>Tem movimentos lentos na horizontal, verical e diagonal;<br><br><b>2-</b>Morre quando atinge 20 passos de simla��o;<br><br><b>3-</b>Caso coma alguma cobra fica com mais 10 passos de simula��o;<br><br><b>4-</b>Caso encontre qualquer outro ente apenas o come;<br></html>";
		String strOvos = "<html> <u><b>Eggs:</b></u><br><br><br><br><b>1-</b>Tr�s tipos: diur�tico, indegesto e psicad�lico;<br><br><b>2-</b>Cada um vale 1 ponto caso seja comido;<br><br><b>3-</b>O diur�tico encurta a cobra 9 posi��es, se a cobra n�o tiver 9 posi��es fica apenas com 2 posi��es;<br><br><b>4-</b>O indegesto a cobra aumenta 3 posi��es;<br><br><b>5-</b>O psicad�lico a cobra fica tonta, tomando a cabe�a a posi��o da cauda e movimenta-se para o lado contr�rio ao qual se estava a movimentar, isto durante 5 passos de simula��o;</html>";
		String strOutras = "<html> <u><b>Other rules:</b></u><br><br><br><br><b>1-</b>A cada 100 passos de simula��o � aumentado um nivel ao jogo, sendo o primeiro nivel o 0;<br><br><b>2-</b>Ao aumentar o nivel aumenta as probabilidades de surgir mangusto e escaravelhos;<br><br><b>3-</b>A velocidade de jogo tamb�m aumenta;</html>";

		JLabel Cobras = new JLabel(strCobras);
		JLabel Escaravelho = new JLabel(strEscaravelho);
		JLabel Mangusto = new JLabel(strMangusto);
		JLabel Ovos = new JLabel(strOvos);
		JLabel Outras = new JLabel(strOutras);

		JFrame instruc = new JFrame();
		instruc.setTitle("Game Instructions");
		instruc.setSize(500, 500);

		JTabbedPane abas = new JTabbedPane();
		abas.addTab("Snakes", Cobras);
		abas.addTab("Scarab", Escaravelho);
		abas.addTab("Mongoose", Mangusto);
		abas.addTab("Eggs", Ovos);
		abas.addTab("Other rules", Outras);

		instruc.add(abas);
		instruc.setVisible(true);
	}

	private void ajudaTeclas() {
		String ajuda = "<html><u><b>Up arrow :</b></u> Go up <br><br> <u><b>Down arrow :</b></u> Go down <br><br> <u><b>Left arrow :</b></u>Yes, Go left <br><br> <u><b>The other arrow :</b></u> Yep, you guessed <br><br> <u><b> 'P'  Key :</b></u> Pause / Resume</html>";
		JOptionPane.showMessageDialog(this, ajuda, "Ismael's Snake - Keys help", JOptionPane.PLAIN_MESSAGE);
	}

	private void guardarJogo() {
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

	private void carregaJogo() throws IOException {
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

	private void menssagem(String strg) {
		JOptionPane.showMessageDialog(this, strg, "Ismael's Snake", JOptionPane.PLAIN_MESSAGE);
	}
}