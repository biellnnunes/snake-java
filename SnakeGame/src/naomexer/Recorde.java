package naomexer;

/**
 * Classe para defenir recordes
 * 
 * @Ismael @06-2010
 */
public class Recorde implements java.io.Serializable {

	private String nome;
	private int pontos;
	private int nivel;

	/**
	 * Construtor da Recorde.
	 * 
	 * @param nome
	 *            Nome do jogador para este recorde.
	 * @param pontos
	 *            Pontos obtidos no total.
	 * @param nivel
	 *            Nivel no momento de fim do Jogo.
	 */
	public Recorde(String nome, int pontos, int nivel) {
		this.nome = nome;
		this.pontos = pontos;
		this.nivel = nivel;
	}

	/**
	 * @return O nome do Jogador deste recorde.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return Os pontos deste recorde.
	 */
	public int getPontos() {
		return pontos;
	}

	/**
	 * @return O nivel deste recorde.
	 */
	public int getNivel() {
		return nivel;
	}
}
