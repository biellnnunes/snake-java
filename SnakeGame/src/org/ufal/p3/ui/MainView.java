package org.ufal.p3.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class MainView {

    static Controller m;
	
	public static void main(String[] args) {
		m = new Controller();
	}

	public void ajudaTeclas() {
		String ajuda = "<html><u><b>Up arrow :</b></u> Go up <br><br> <u><b>Down arrow :</b></u> Go down <br><br> "
				+ "<u><b>Left arrow :</b></u>Yes, Go left <br><br> <u><b>The other arrow :</b></u> Yep, you guessed "
				+ "<br><br> <u><b> 'P'  Key :</b></u> Pause / Resume</html>";
		JOptionPane.showMessageDialog(null, ajuda, "Snake - Keys help", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void menssagem(String strg) {
		JOptionPane.showMessageDialog(null, strg, "Snake", JOptionPane.PLAIN_MESSAGE);
	}

	public void instrucoes() {
		String strCobras = "<html> <u><b>Cobras do utilizador e automática:</b></u><br><br><br><br><b>1-</b>Movem-se para a esquerda, "
				+ "direita, cima e baixo;<br><br><b>2-</b> A cobra automática tem movimentos aleatórios automáticamente criados pelo PC;"
				+ "<br><br><b>3-</b>A cobra do utilizador tem movimentos controlados pelo utilizador;<br><br><b>4-</b>As cobras comem entes "
				+ "que não gostam de as comer (ovos e escaravelho);<br><br><b>5-</b>Movem-se de 2 em 2 segundos na posição para que estão "
				+ "viradas ou a cobra do utilizador move-se de acordo com a vontade do utilizador;<br><br><b>6-</b>Ocupa 4 posições incialmente;"
				+ "<br><br><b>7-</b>Se bater contra si própria, outras cobras ou contra a parede morre;</html>";
		
		String strEscaravelho = "<html> <u><b>Scarab:</b></u><br><br><br><br><b>1-</b>Tem movimentos lentos na horizontal, verical e diagonal;"
				+ "<br><br><b>2-</b>Demora 3 passos de simulação a movimentar-se;<br><br><b>3-</b>Quando é comido por uma cobra, a mesma ganha "
				+ "3 pontos;<br><br><b>4-</b>Morre quando atinge 50 passos de simlação ou quando se encontra com qualquer outro ente;<br></html>";
	
		String strMangusto = "<html> <u><b>Mongoose:</b></u><br><br><br><br><b>1-</b>Tem movimentos lentos na horizontal, verical e diagonal;"
				+ "<br><br><b>2-</b>Morre quando atinge 20 passos de simlação;<br><br><b>3-</b>Caso coma alguma cobra fica com mais 10 passos "
				+ "de simulação;<br><br><b>4-</b>Caso encontre qualquer outro ente apenas o come;<br></html>";
		
		String strOvos = "<html> <u><b>Eggs:</b></u><br><br><br><br><b>1-</b>Três tipos: diurético, indegesto e psicadélico;<br><br><b>2-</b>"
				+ "Cada um vale 1 ponto caso seja comido;<br><br><b>3-</b>O diurético encurta a cobra 9 posições, se a cobra não tiver 9 posições "
				+ "fica apenas com 2 posições;<br><br><b>4-</b>O indegesto a cobra aumenta 3 posições;<br><br><b>5-</b>O psicadélico a cobra fica "
				+ "tonta, tomando a cabeça a posição da cauda e movimenta-se para o lado contrário ao qual se estava a movimentar, isto durante 5 "
				+ "passos de simulação;</html>";
		
		String strOutras = "<html> <u><b>Other rules:</b></u><br><br><br><br><b>1-</b>A cada 100 passos de simulação é aumentado um nivel ao jogo, "
				+ "sendo o primeiro nivel o 0;<br><br><b>2-</b>Ao aumentar o nivel aumenta as probabilidades de surgir mangusto e escaravelhos;<br>"
				+ "<br><b>3-</b>A velocidade de jogo também aumenta;</html>";

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

}
