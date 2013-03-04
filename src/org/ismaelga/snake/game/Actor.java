package org.ismaelga.snake.game;

/**
 * Abstract class Actor - Guarda atributos dos actores do jogo.
 * 
 * @Ismael
 * @04-2010
 */
public abstract class Actor implements java.io.Serializable
{
    // Posicao do actor
    private Posicao posicao;
    // Estado vivo caso true ou morto caso false
    private boolean alive;
    
    /**
     * Construtor para um actor
     */
    public Actor()
    {
        alive = true;
    }
    
    /**
     * Faz o Actor actuar uma vez
     */
    abstract public void actua(Terreno terreno);                         

    /**
     * Marca o actor como morto
     */ 
    public void setDead()
    {
        alive = false;
    }
    
    /**
     * Marca o actor como vivo
     */ 
    public void setAlive()
    {
        alive = true;
    }
    /**
     * @return Verdadeiro se actor estiver vivo
     */
    public boolean isAlive()
    {
        return alive;
    }
    /**
     * @return Posicao do actor
     */
    public Posicao getPosicao()
    {
        return posicao;
    }
    /**
     * Dá uma posicao ao actor
     * @param lin Linha da posicao
     * @param col Coluna da posicao
     */
    public void setPosicao(int lin, int col)
    {
        this.posicao = new Posicao(lin, col);
    }

    /**
     * Dá uma posicao ao actor
     * @param posicao Posicao para o actor
     */
    public void setPosicao(Posicao posicao)
    {
        this.posicao = posicao;
    }
}
