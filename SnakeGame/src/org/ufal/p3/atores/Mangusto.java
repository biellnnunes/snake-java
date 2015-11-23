package org.ufal.p3.atores;

import java.util.Random;

import org.ufal.p3.controladores.Posicao;
import org.ufal.p3.controladores.Terreno;

/**
 * Class Mangusto cria um mangusto que irá comer cobras e este irá mexer-se
 * aleatoriamente e irá morrer passados N passos.
 * 
 * @Ismael @04-2010
 * @Gabriel @11-2015
 */
public class Mangusto extends Atores {
	// instance variables - replace the example below with your own
	private static final int TEMPO_VIDA = 20;
	private int vida;

	/**
	 * Construtor para Mangustos
	 */
	public Mangusto() {
		vida = TEMPO_VIDA;
	}

	/**
	 * Metodo Que faz o Mangusto movimentar-se
	 * 
	 * @param terreno
	 *            Terreno do jogo
	 */
	public void actua(Terreno terreno) {
		if (vida == 0) {
			this.setDead();
		} else {
			Random rand = new Random();
			int numRand;
			int tentativa = 0;

			Posicao posActual = this.getPosicao();
			int lin;
			int col;
			Posicao novaPos;
			Atores actor;
			boolean morre;

			do {
				numRand = rand.nextInt(7);
				lin = posActual.getLinha();
				col = posActual.getColuna();
				morre = false;

				if (numRand == 0) {
					col++;
				} else if (numRand == 1) {
					col--;
				} else if (numRand == 2) {
					lin--;
				} else if (numRand == 3) {
					lin++;
				} else if (numRand == 4) {
					lin++;
					col--;
				} else if (numRand == 5) {
					lin++;
					col++;
				} else if (numRand == 6) {
					lin--;
					col--;
				} else if (numRand == 7) {
					lin--;
					col++;
				}

				novaPos = new Posicao(lin, col);
				if (lin < 0 || col < 0 || lin > terreno.getAltura() || col > terreno.getLargura()) {
					morre = true;
					tentativa++;
				} else {

					actor = terreno.getObjectAt(novaPos);

					if (actor instanceof Mangusto) {
						morre = true;
						tentativa++;
					} else if (actor instanceof Ovo) {
						morre = true;
						tentativa++;
					} else if (actor instanceof Escaravelho) {
						morre = true;
						tentativa++;
					}
				}

			} while (tentativa < 6 && morre == true);

			this.move(novaPos, terreno);

			vida--;
		}
	}

	private void move(Posicao pos, Terreno terreno) {

		int linha = pos.getLinha();
		int coluna = pos.getColuna();

		// VER O QUE ESTA NA POSICAO E TOMAR AS CONSEQUENCIAS
		if (linha < 0 || coluna < 0 || linha > terreno.getAltura() || coluna > terreno.getLargura()) {
			this.setDead();
		} else {
			Atores actorNaPos = (Atores) terreno.getObjectAt(pos);
			if (actorNaPos instanceof Ovo) {
				actorNaPos.setDead();
			} else if (actorNaPos instanceof Cobra) {
				actorNaPos.setDead();
				vida += 10;
			} else if (actorNaPos instanceof CorpoCobra) {
				((CorpoCobra) actorNaPos).setDeadAll();
				vida += 10;
			} else if (actorNaPos instanceof Escaravelho) {
				actorNaPos.setDead();
			} else if (actorNaPos instanceof Mangusto) {
				this.setDead();
				actorNaPos.setDead();
			}

			this.setPosicao(pos);
		}
	}
}
