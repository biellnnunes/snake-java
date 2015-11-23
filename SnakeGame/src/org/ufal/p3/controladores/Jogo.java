package org.ufal.p3.controladores;

import java.util.Random;

import org.ufal.p3.atores.Atores;
import org.ufal.p3.atores.Cobra;
import org.ufal.p3.atores.CobraAuto;
import org.ufal.p3.atores.CobraHuman;
import org.ufal.p3.atores.Escaravelho;
import org.ufal.p3.atores.Mangusto;
import org.ufal.p3.atores.Ovo;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe principal do projecto Snake I. É esta classe que fornece todo o
 * interface e controla todas as outras classes.
 * 
 * @Ismael @04-2010
 */
public class Jogo {
	// Probabilidade de aparecer um escaravelho.
	private static final double PROBABILIDADE_CRIAR_ESCARAVELHO = 0.01;
	// Probabilidade de aparecer um mangusto.
	private static final double PROBABILIDADE_CRIAR_MANGUSTO = 0.02;
	// Probabilidade do ovo ser psicadelico.
	private static final double PROBABILIDADE_PSICADELICO = 0.05;
	// Probabilidade de ovo ser diuretico.
	private static final double PROBABILIDADE_DIURETICO = 0.15;
	// Tempo que demora um passo a correr.
	private static final int TEMPO_ENTRE_PASSOS = 500;
	// tempo que vai retirar ao tempo que demora 1 passo. 
	private static final int TEMPO_AUMENTADO_POR_NIVEL = 25;
	// Em cada 100 passos é mudado o nivel
	private static final int PASSOS_POR_NIVEL = 5;
	// contador dos passos no jogo
	private int passo;
	// terreno do jogo
	private Terreno terreno;
	// todos os actores
	private List<Atores> actores;
	private CobraHuman cobraHumana;
	private CobraAuto cobraAuto;
	private Ovo ovo;
	private String nomeJogador;
	private int tempoEntrePassos;
	private boolean fim;
	private int nivel;
		
	/**
	 * Construtor de jogo com altura e largura originais.
	 * 
	 * @param nome
	 *            Nome do jogador.
	 */
	public Jogo(String nome) {
		this(51, 51, nome);
	}

	/**
	 * Cria um jogo com um terreno de dimensoes dadas
	 * 
	 * @param largura
	 *            Largura do campo de jogo ou terreno. Tem de ser maior que 20.
	 * @param altura
	 *            Altura do campo de jogo ou terreno. Tem de ser maior que 20.
	 * @param nome
	 *            Nome do jogador.
	 */
	public Jogo(int largura, int altura, String nome) {
		if (largura <= 21 || altura <= 21) {
			altura = 51;
			largura = 51;
		}
		nomeJogador = nome;
		terreno = new Terreno(largura, altura);

		actores = new ArrayList<Atores>();
		recomecar();
	}

	private void preparaProxima() {
		Random rand = new Random();

		if (!cobraHumana.isAlive() && cobraHumana.getVidas() > 0)
			resetCobraHum();

		if (!cobraAuto.isAlive() && cobraAuto.getVidas() > 0)
			resetCobraAuto();

		if (!ovo.isAlive())
			criaNovoOvo();

		if ((rand.nextDouble() <= nivel * PROBABILIDADE_CRIAR_ESCARAVELHO) && ((passo % 5) == 0))
			criaEscaravelho();

		if ((rand.nextDouble() <= nivel * PROBABILIDADE_CRIAR_MANGUSTO) && ((passo % 5) == 0))
			criaMangusto();
	}

	public void mandaActuar() {
	
		Atores actor;
		for (int i = 0; i < actores.size(); i++) {
			actor = (Atores) actores.get(i);
			actor.templateAtor(terreno);
			this.limpaMortos();
		}

		if (!cobraHumana.isAlive() && cobraHumana.getVidas() <= 0) {
			fim = true;
		} else {
			preparaProxima();
			passo++;
			if (nivel < (passo / (PASSOS_POR_NIVEL + (nivel * (PASSOS_POR_NIVEL / 2)))))
				nivel = (passo / (PASSOS_POR_NIVEL + (nivel * (PASSOS_POR_NIVEL / 2))));
		}
	}

	/**
	 * Move a cobra N vezes em direcção ao lado para que está virada
	 * 
	 * @param vezes
	 *            Vezes para a cobra mover.
	 */
	public void moveEmFrente(int vezes) {
		for (int i = 0; i < vezes; i++) {
			mandaActuar();
		}
	}

	/**
	 * Move a cobra de controlo humano para a esquerda no proximo passo.
	 */
	public void moveCobraEsquerda() {
		cobraHumana.setLadoVirada("esquerda");
	}

	/**
	 * Move a cobra de controlo humano para a direita no proximo passo.
	 */
	public void moveCobraDireita() {
		cobraHumana.setLadoVirada("direita");
	}

	/**
	 * Move a cobra de controlo humano para cima no proximo passo.
	 */
	public void moveCobraCima() {
		cobraHumana.setLadoVirada("cima");
	}

	/**
	 * Move a cobra de controlo humano para baixo no proximo passo.
	 */
	public void moveCobraBaixo() {
		cobraHumana.setLadoVirada("baixo");
	}

	private void limpaMortos() {
		// Percorre os actores e actualiza o campo e a lista de actores
		// retirando os mortos
		Atores actor;
		List<Atores> actoresVivos = new ArrayList<Atores>();
		terreno.clear();
		for (int i = 0; i < actores.size(); i++) {
			actor = (Atores) actores.get(i);
			if (actor.isAlive()) {
				actoresVivos.add(actor);
				terreno.coloca(actor);
			}
		}
		actores = actoresVivos;
	}

	private void resetCobra(Cobra cobra, int lin, int col) {
		Posicao pos;

		cobra.setAlive();
		cobra.setPosicao(lin, col);
		col--;
		(cobra.getCorpo()).clear();
		for (int i = 1; i <= 3; i++) {
			pos = new Posicao(lin, col);
			cobra.cresce(pos);
			col--;
		}
		cobra.setLadoVirada("direita");
		actores.add(cobra);
		terreno.coloca(cobra);
	}

	private void resetCobraHum() {
		resetCobra((Cobra) cobraHumana, 10, 10);
	}

	private void resetCobraAuto() {
		resetCobra((Cobra) cobraAuto, terreno.getAltura() - 10, 10);
	}

	private void criaNovoOvo() {
		Posicao pos;
		Random rand = new Random();

		String tipo = "indegesto";

		if (rand.nextDouble() <= PROBABILIDADE_PSICADELICO)
			tipo = "psicadelico";
		else if (rand.nextDouble() <= PROBABILIDADE_DIURETICO)
			tipo = "diuretico";

		do {
			pos = new Posicao(rand.nextInt(terreno.getAltura()), rand.nextInt(terreno.getLargura()));
		} while (terreno.getObjectAt(pos) != null);

		ovo = new Ovo(tipo);
		ovo.setPosicao(pos);
		actores.add(ovo);
		terreno.coloca(ovo);
	}

	private void criaEscaravelho() {
		Posicao pos;

		Random rand = new Random();
		do {
			pos = new Posicao(rand.nextInt(terreno.getAltura()), rand.nextInt(terreno.getLargura()));
		} while (terreno.getObjectAt(pos) != null);

		Escaravelho novoEscaravelho = new Escaravelho();
		novoEscaravelho.setPosicao(pos);
		actores.add(novoEscaravelho);
		terreno.coloca(novoEscaravelho);
	}

	private void criaMangusto() {
		Posicao pos;

		Random rand = new Random();
		do {
			pos = new Posicao(rand.nextInt(terreno.getAltura()), rand.nextInt(terreno.getLargura()));
		} while (terreno.getObjectAt(pos) != null);

		Mangusto novoMangusto = new Mangusto();
		novoMangusto.setPosicao(pos);
		actores.add(novoMangusto);
		terreno.coloca(novoMangusto);
	}

	/**
	 * Recomeça o jogo colocando actores, pontos etc no estado inicial
	 */
	public void recomecar() {
		passo = 0;
		nivel = 0;
		fim = false;
		tempoEntrePassos = TEMPO_ENTRE_PASSOS;
		terreno.clear();
		actores.clear();
		criaCenario();
	}

	public boolean isFim() {
		return fim;
	}

	public int getPasso() {
		return passo;
	}

	public Terreno getTerreno() {
		return terreno;
	}

	public int getNivel() {
		return nivel;
	}

	public int getTempoPasso() {
		if (tempoEntrePassos - (TEMPO_AUMENTADO_POR_NIVEL * getNivel()) >= 25)
			return tempoEntrePassos - (TEMPO_AUMENTADO_POR_NIVEL * getNivel());
		else
			return 25;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public int getPontos() {
		return cobraHumana.getPontos();
	}

	public int getVidas() {
		return cobraHumana.getVidas();
	}

	public String getTipoOvo() {
		return ovo.getTipo();
	}

	public int getAltura() {
		return terreno.getAltura();
	}

	public int getLargura() {
		return terreno.getLargura();
	}

	private void criaCenario() {
		terreno.clear();

		// Criar cobra de controlo humano
		cobraHumana = new CobraHuman();
		resetCobraHum();

		// Criar cobra de controlo automatico
		cobraAuto = new CobraAuto();
		resetCobraAuto();

		// Criar o primeiro Ovo que será do tipo indegesto
		criaNovoOvo();

	}
}
