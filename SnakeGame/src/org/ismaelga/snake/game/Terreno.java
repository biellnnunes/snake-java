package org.ismaelga.snake.game;

import java.util.List;

import naomexer.Atores;
import naomexer.Posicao;

/**
 * Guarda actores nas posicoes e disponibliliza metodos para manipulação
 * 
 * @Ismael @04-2010
 * @Gabriel @11-2015
 */
public class Terreno {
	private int largura, altura;
	private Atores[][] terreno;

	/**
	 * Construtor da class Terreno.
	 * 
	 * @param altura
	 *            Altura para o terreno.
	 * @param largura
	 *            Largura para o terreno.
	 */
	public Terreno(int altura, int largura) {
		// initialise instance variables
		this.altura = altura;
		this.largura = largura;
		terreno = new Atores[altura][largura];
	}

	/**
	 * Coloca no terreno uma peça de um Actor ou então simplesmente um actor.
	 * 
	 * @param actor
	 *            Actor a colocar na posicao.
	 */
	public void coloca(Atores actor) {
		Posicao posicao = actor.getPosicao();
		terreno[posicao.getLinha()][posicao.getColuna()] = actor;
		if (actor instanceof Cobra) {
			Cobra cobra = (Cobra) actor;
			colocaCobra(cobra.getCorpo());
		}
	}

	@SuppressWarnings("rawtypes")
	private void colocaCobra(List corpoCobra) {
		for (int i = 0; i <= corpoCobra.size() - 1; i++) {
			Atores pedaço = (Atores) corpoCobra.get(i);
			Posicao posicao = pedaço.getPosicao();
			terreno[posicao.getLinha()][posicao.getColuna()] = pedaço;
		}
	}

	public Atores getObjectAt(Posicao posicao) {
		return getObjectAt(posicao.getLinha(), posicao.getColuna());
	}

	public Atores getObjectAt(int lin, int col) {
		return terreno[lin][col];
	}

	/**
	 * Limpa o terreno.
	 */
	public void clear() {
		for (int lin = 0; lin < altura; lin++) {
			for (int col = 0; col < largura; col++) {
				terreno[lin][col] = null;
			}
		}
	}

	public int getAltura() {
		return altura - 1;
	}

	/**
	 * @return The width of the field.
	 */
	public int getLargura() {
		return largura - 1;
	}
}
