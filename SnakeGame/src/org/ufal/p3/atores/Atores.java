package org.ufal.p3.atores;

import org.ufal.p3.controladores.Posicao;
import org.ufal.p3.controladores.Terreno;

/**
 * Abstract class Actor - Guarda atributos dos actores do jogo.
 * 
 * @Ismael @04-2010
 */
public abstract class Atores {
	// Posicao do actor
	private Posicao posicao;
	// Estado vivo caso true ou morto caso false
	private boolean alive;

	public final void templateAtor(Terreno terreno){
		
		actua(terreno);
	}
	
	
	/**
	 * Construtor para um actor
	 */
	public Atores() {
		alive = true;
	}

	/**
	 * Faz o Actor actuar uma vez
	 */
	abstract public void actua(Terreno terreno);

	/**
	 * Marca o actor como morto
	 */
	public void setDead() {
		alive = false;
	}

	/**
	 * Marca o actor como vivo
	 */
	public void setAlive() {
		alive = true;
	}

	/**
	 * @return Verdadeiro se actor estiver vivo
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @return Posicao do actor
	 */
	public Posicao getPosicao() {
		return posicao;
	}

	/**
	 * Dá uma posicao ao actor
	 * 
	 * @param lin
	 *            Linha da posicao
	 * @param col
	 *            Coluna da posicao
	 */
	public void setPosicao(int lin, int col) {
		this.posicao = new Posicao(lin, col);
	}

	/**
	 * Dá uma posicao ao actor
	 * 
	 * @param posicao
	 *            Posicao para o actor
	 */
	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}
}
