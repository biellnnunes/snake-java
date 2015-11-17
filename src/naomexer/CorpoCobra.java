package naomexer;

import org.ismaelga.snake.game.Cobra;
import org.ismaelga.snake.game.Terreno;

/**
 * Partes para constituir o corpo da cobra
 * 
 * @Ismael @04-2010
 */
public class CorpoCobra extends Atores {
	// Cobra a que pertence
	private Cobra cobraPertencente;

	/**
	 * Constructor for objects of class Corpo_cobra
	 * 
	 * @param cobra
	 *            Cobra a que vai pertencer
	 */
	public CorpoCobra(Cobra cobra) {
		super();
		cobraPertencente = cobra;
	}

	public void actua(Terreno terreno) {

	}

	/**
	 * Mata cobra inteira a que pertence
	 */
	public void setDeadAll() {
		cobraPertencente.setDead();
	}

	/**
	 * @return Cobra tipo classe Cobra a que pertence
	 */
	public Cobra getCobraPertencente() {
		return cobraPertencente;
	}
}
