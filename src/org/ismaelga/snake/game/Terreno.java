package org.ismaelga.snake.game;

import java.util.List;

/**
 * Guarda actores nas posicoes e disponibliliza metodos para manipulação
 * 
 * @Ismael
 * @04-2010
 */
public class Terreno implements java.io.Serializable {
    // instance variables - replace the example below with your own
    private int largura, altura;
    private Actor[][] terreno;
    
    /**
     * Construtor da class Terreno.
     * @param altura Altura para o terreno.
     * @param largura Largura para o terreno.
     */
    public Terreno(int altura, int largura) {
        // initialise instance variables
        this.altura = altura;
        this.largura = largura;
        terreno = new Actor[altura][largura];
    }

    /**
     * Coloca no terreno uma peça de um Actor ou então simplesmente um actor.
     * @param actor Actor a colocar na posicao.
     */
    public void coloca(Actor actor) {
        Posicao posicao = actor.getPosicao();
        terreno[posicao.getLinha()][posicao.getColuna()] = actor;
        if(actor instanceof Cobra) {
            Cobra cobra = (Cobra)actor;
            coloca(cobra.getCorpo());
        }
    }
    
    private void coloca(List corpoCobra) {
        for(int i=0; i <= corpoCobra.size()-1; i++) {
            Actor pedaço = (Actor)corpoCobra.get(i);
            Posicao posicao = pedaço.getPosicao();
            terreno[posicao.getLinha()][posicao.getColuna()] = pedaço;
        }
    }
    
    public Actor getObjectAt(Posicao posicao) {
        return getObjectAt(posicao.getLinha(), posicao.getColuna());
    }
    
    public Actor getObjectAt(int lin, int col) {
        return terreno[lin][col];
    }

    /**
     * Limpa o terreno.
     */
    public void clear() {
        for(int lin = 0; lin < altura; lin++) {
            for(int col = 0; col < largura; col++) {
                terreno[lin][col] = null;
            }
        }
    }
    
    public int getAltura() {
        return altura-1;
    }
    
    /**
     * @return The width of the field.
     */
    public int getLargura() {
        return largura-1;
    }
}
