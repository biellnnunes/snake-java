package org.ufal.p3.atores;

import org.ufal.p3.controladores.Terreno;

/**
 * Cria uma cobra para controlo humano.
 * 
 * @Ismael
 * @04-2010
 */
public class CobraHuman extends Cobra {
    // instance variables - replace the example below with your own
    /**
     * Constructor da classe.
     */
    public CobraHuman(){
        super();
    }
    
    /**
     * Envia mensagem para se mover para o lado que está virada a cobra
     * @param terreno Terreno de jogo.
     */
    public void actua(Terreno terreno){
        this.moveCobra("mesmo", terreno);
    }

}
