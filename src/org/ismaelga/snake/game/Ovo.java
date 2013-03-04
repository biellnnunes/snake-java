package org.ismaelga.snake.game;

/**
 * Classe Ovo permite criar um ovo com um determinado tipo
 * 
 * @Ismael
 * @04-2010
 */
public class Ovo extends Actor {
    String tipo;
    /**
     * Constructor for objects of class Ovo
     * @param String Tipo que o ovo a ser criado irá ter
     */
    public Ovo(String otipo)
    {
        super();
        tipo = otipo;
    }

    public void actua(Terreno terreno) {

    }
    /**
     * @return String Tipo deste ovo
     */
    public String getTipo()
    {
        return tipo;
    }
}
