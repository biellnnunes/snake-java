package org.ismaelga.snake.game;

import java.util.Random;

import naomexer.Atores;
import naomexer.CorpoCobra;
import naomexer.Posicao;
/**
 * Cria uma cobra que irá movimentar-se automaticamente
 * 
 * @Ismael
 * @04-2010
 */
public class CobraAuto extends Cobra {
    // instance variables - replace the example below with your own
   /**
     * Construtor da classe Cobra_auto
     */
    public CobraAuto(){
        super();
    }
    
    public void actua(Terreno terreno){
        String ladoRand;
        Random rand = new Random();
        int numRand;
        int tentativa = 0;
        
        Posicao posActual = this.getPosicao();
        int lin;
        int col;
        Posicao novaPos;
        Atores actor;
        boolean morre;
        
        
        do{
        numRand = rand.nextInt(6);
        lin = posActual.getLinha();
        col = posActual.getColuna();
        morre = false;
        if(numRand == 0)
        {
            ladoRand = "direita";
            col++;
        }
        else if(numRand == 1)
             {
                ladoRand = "esquerda";
                col--;
             }
             else if(numRand == 2)
                {
                    ladoRand = "cima";
                    lin--;
                }
                  else if(numRand == 3)
                  {
                     ladoRand = "baixo";
                     lin++;
                  }
                  else
                  {
                     ladoRand = "mesmo";
                     String ladoVirada = this.getLadoVirada();
                     if(ladoVirada.equals("direita"))
                        col++;
                     else if(ladoVirada.equals("esquerda"))
                        col--;
                        else if(ladoVirada.equals("cima"))
                            lin--;
                            else
                            lin++;
                  }
        if(lin < 0 || col < 0 || lin > terreno.getAltura() || col > terreno.getLargura())
        {
            morre = true;
            tentativa++;
        }
        else
        {
            novaPos = new Posicao(lin, col);
            actor = terreno.getObjectAt(novaPos);
            
            if(actor instanceof Cobra || actor instanceof CorpoCobra)
            {
                morre = true;
                tentativa++;
            }
        }
        } while(tentativa < 6 && morre == true);
        this.moveCobra(ladoRand, terreno);
    }

}
