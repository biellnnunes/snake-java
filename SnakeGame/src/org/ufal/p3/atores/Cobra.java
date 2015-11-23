package org.ufal.p3.atores;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.ufal.p3.controladores.Posicao;
import org.ufal.p3.controladores.Terreno;

/**
 * Classe cobra onde fica definido a localização da cabeça e guardado as partes
 * constituintes.
 * 
 * @Ismael @04-2010
 */
public abstract class Cobra extends Atores {
	// instance variables - replace the example below with your own
	private ArrayList<CorpoCobra> corpo;

	private CorpoCobra cauda;
	private int porCrescer;
	private String ladoVirada;
	private int ovosComidos;
	private int vidas;
	private int pontos;
	private int tonta;
	
	
	private static Cobra instance = null;

	public static Cobra getInstance() {

		if (instance == null) {
			instance = new Cobra() {
				
				@Override
				public void actua(Terreno terreno) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		return instance;
	}


	abstract public void actua(Terreno terreno);

	/**
	 * Construtor para criar cobras
	 */
	public Cobra() {
		super();
		corpo = new ArrayList<CorpoCobra>();
		porCrescer = 0;
		ovosComidos = 0;
		vidas = 3;
		tonta = 0;
	}
	

	/**
	 * Movimenta a cobra
	 * 
	 * @param lado
	 *            Lado que a cobra se irá movimentar
	 * @param terreno
	 *            Terreno de jogo
	 */
	public void moveCobra(String lado, Terreno terreno) {
		Posicao posActual = this.getPosicao();
		int linha = posActual.getLinha();
		int coluna = posActual.getColuna();
		// ve se vai em lado contrario
		if (lado.equals("mesmo"))
			this.moveCobra(ladoVirada, terreno);
		else {
			this.ladoVirada = lado;
			if (lado.equals("direita")) {
				coluna++;
			} else {
				if (lado.equals("esquerda")) {
					coluna--;
				} else {
					if (lado.equals("cima")) {
						linha--;
					} else {
						linha++;
					}
				}
			}

			// VER O QUE ESTA NA POSICAO E TOMAR AS CONSEQUENCIAS

			if (linha < 0 || coluna < 0 || linha > terreno.getAltura() || coluna > terreno.getLargura()) {
				this.setDead();
			} else {
				Posicao novaPos = new Posicao(linha, coluna);
				Atores actorNaPos = (Atores) terreno.getObjectAt(novaPos);
				if (actorNaPos instanceof Ovo) {
					comeOvo((Ovo) actorNaPos);
					pontos += 1;

				} else if (actorNaPos instanceof Cobra) {
					this.setDead();
					actorNaPos.setDead();
				} else if (actorNaPos instanceof CorpoCobra) {
					this.setDead();
				} else if (actorNaPos instanceof Escaravelho) {
					actorNaPos.setDead();
					pontos += 3;
					vidas++;
				} else if (actorNaPos instanceof Mangusto) {
					this.setDead();
				}

				if (porCrescer > 0) {
					this.cresce(cauda.getPosicao());
					porCrescer--;
				}
				this.setPosicao(novaPos);
				// Cada peça do corpo fica com a posicao da peça da frente

				for (int i = corpo.size() - 1; i > 0; i--) {
					((Atores) corpo.get(i)).setPosicao(((Atores) corpo.get(i - 1)).getPosicao());
				}
				// a primeira peça fica com a posicao anterior da cabeça
				((Atores) corpo.get(0)).setPosicao(posActual);

				if (tonta > 0) {
					Random rand = new Random();
					if (rand.nextDouble() < 0.40) {
						entontece();
						tonta--;
					}
				}
			}
		}
	}

	private void comeOvo(Ovo ovo) {
		String tipo = ovo.getTipo();
		ovosComidos++;
		if (tipo.equals("indegesto")) {
			porCrescer += 6;

		} else if (tipo.equals("diuretico")) {
			encurta();
		} else // psicadelico
		{
			tonta += 3;
		}
		ovo.setDead();
	}

	/**
	 * Aumenta o tamanho da cobra em 1 na posicao indicada
	 * 
	 * @param posicao
	 *            Posicao para nascer o novo pedaço de cobra
	 */
	public void cresce(Posicao posicao) {
		CorpoCobra novoPedaço;
		novoPedaço = new CorpoCobra(this);
		novoPedaço.setPosicao(posicao.getLinha(), posicao.getColuna());
		corpo.add(novoPedaço);
		this.cauda = novoPedaço;
	}

	private void encurta() {
		int i;
		ArrayList<CorpoCobra> novoCorpo = new ArrayList<CorpoCobra>();
		int tamanho = corpo.size() - 1;

		for (i = tamanho; i > tamanho - 9 && i > 0; i--) {
			((Atores) corpo.get(i)).setDead();
		}
		this.cauda = (CorpoCobra) corpo.get(i);
		tamanho = tamanho - (tamanho - i);
		for (i = 0; i <= tamanho; i++) {
			novoCorpo.add(corpo.get(i));
		}
		this.corpo = novoCorpo;
	}

	private void entontece() {
		Posicao posTemp;
		// troca posicão da cabeça pela cauda
		posTemp = this.getPosicao();

		this.setPosicao(((CorpoCobra) corpo.get(corpo.size() - 1)).getPosicao());
		((CorpoCobra) corpo.get(corpo.size() - 1)).setPosicao(posTemp);
		// inverte posicao do corpo
		for (int i = 0; i <= (corpo.size() - 2) / 2; i++) {
			posTemp = ((CorpoCobra) corpo.get(i)).getPosicao();
			((CorpoCobra) corpo.get(i)).setPosicao(((CorpoCobra) corpo.get((corpo.size() - 2) - i)).getPosicao());
			((CorpoCobra) corpo.get((corpo.size() - 2) - i)).setPosicao(posTemp);
		}
		if (ladoVirada.equals("direita")) {
			ladoVirada = "esquerda";
		} else if (ladoVirada.equals("esquerda")) {
			ladoVirada = "direita";
		} else if (ladoVirada.equals("cima")) {
			ladoVirada = "baixo";
		} else if (ladoVirada.equals("baixo")) {
			ladoVirada = "cima";
		}
	}

	/**
	 * Muda o lado para que a cobra irá andar
	 * 
	 * @param lado
	 *            Lado para se virar
	 */
	public void setLadoVirada(String lado) {
		if (ladoVirada == null)
			this.ladoVirada = lado;
		else if (!((ladoVirada.equals("direita") && lado.equals("esquerda"))
				|| (ladoVirada.equals("esquerda") && lado.equals("direita"))
				|| (ladoVirada.equals("cima") && lado.equals("baixo"))
				|| (ladoVirada.equals("baixo") && lado.equals("cima"))))
			this.ladoVirada = lado;
	}

	/**
	 * Muda o estado da cobra para morta e de todas as suas peçasque compoe o
	 * corpo.
	 */
	public void setDead() {
		ladoVirada = null;
		super.setDead();
		for (int i = corpo.size() - 1; i >= 0; i--) {
			((Atores) corpo.get(i)).setDead();
		}
		vidas--;
	}

	/**
	 * @return List Lista das peças do corpo
	 */
	public List<CorpoCobra> getCorpo() {
		return corpo;
	}

	/**
	 * @return String Lado que a cobra está virada
	 */
	public String getLadoVirada() {
		return ladoVirada;
	}

	/**
	 * @return int Total de ovos comidos pela cobra
	 */
	public int getOvosComidos() {
		return ovosComidos;
	}

	/**
	 * @return int Total de vidas da cobra
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * @return int Pontos da cobra
	 */
	public int getPontos() {
		return pontos;
	}
}
