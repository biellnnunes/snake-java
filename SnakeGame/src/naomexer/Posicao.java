package naomexer;

/**
 * Classe que cria uma posicao que irá ser atribuida a um actor
 * 
 * @Ismael @04-2010
 */
public class Posicao {
	// instance variables - replace the example below with your own
	private int lin;
	private int col;

	/**
	 * Contrutor da posicao
	 * 
	 * @param lin
	 *            Linha para a posicao
	 * @param col
	 *            Coluna para a posicao
	 */
	public Posicao(int lin, int col) {
		this.lin = lin;
		this.col = col;
	}

	/**
	 * Retorna uma string com a forma de linha, coluna.
	 * 
	 * @return Uma string com a linha e coluna correspondente à posição.
	 */
	public String toString() {
		return lin + "," + col;
	}

	/**
	 * Retorna a linha da posicao
	 * 
	 * @return A linha.
	 */
	public int getLinha() {
		return lin;
	}

	/**
	 * Retorna a coluna da posicao
	 * 
	 * @return A coluna.
	 */
	public int getColuna() {
		return col;
	}

}
