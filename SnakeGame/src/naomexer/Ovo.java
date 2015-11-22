package naomexer;

import org.ismaelga.snake.game.Terreno;

/**
 * Classe Ovo permite criar um ovo com um determinado tipo
 * 
 * @Ismael @04-2010
 */
public class Ovo extends Atores {
	String tipo;

	/**
	 * Constructor for objects of class Ovo
	 * 
	 * @param String
	 *            Tipo que o ovo a ser criado ir� ter
	 */
	public Ovo(String otipo) {
		super();
		tipo = otipo;
	}

	public void actua(Terreno terreno) {

	}

	/**
	 * @return String Tipo deste ovo
	 */
	public String getTipo() {
		return tipo;
	}
}
