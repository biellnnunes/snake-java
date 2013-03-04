package org.ismaelga.snake.game;

import java.util.Random;
/**
 * Cria um escaravelho que se irá movimentar
 * este morre passado N passos.
 * 
 * @Ismael
 * @04-2010
 */
public class Escaravelho extends Actor
{
    // instance variables - replace the example below with your own
    private static final int TEMPO_PARA_MOVER = 3;
    private static final int TEMPO_VIDA = 50;
    private int proximoMovimento;
    private int vida;

    /**
     * Constructor for objects of class Escaravelho
     */
    public Escaravelho()
    {
        proximoMovimento = TEMPO_PARA_MOVER;
        vida = TEMPO_VIDA;
    }
    /**
     * Movimenta o Escaravelho
     * @param terreno Terreno do jogo
     */
    public void actua(Terreno terreno)
    {
        if(vida == 0)
        {
            this.setDead();
        }
        else
        {
            if(proximoMovimento == 0)
            {
                Random rand = new Random();
                int numRand;
                int tentativa = 0;
        
               // Posicao posActual = this.getPosicao();
                int lin;
                int col;
                Posicao posActual = this.getPosicao();
                Posicao novaPos;
                Actor actor;
                boolean morre;
        
                do{
                    numRand = rand.nextInt(7);
                    lin = posActual.getLinha();
                    col = posActual.getColuna();
                    morre = false;
                    if(numRand == 0)
                    {
                        col++;
                    }
                    else if(numRand == 1)
                    {
                        col--;
                    }
                    else if(numRand == 2)
                    {
                        lin--;
                    }
                    else if(numRand == 3)
                    {
                        lin++;
                    }
                    else if(numRand == 4)
                    {
                        lin++;
                        col--;
                    }
                    else if(numRand == 5)
                    {
                        lin++;
                        col++;
                    }
                    else if(numRand == 6)
                    {
                        lin--;
                        col--;
                    }
                    else if(numRand == 7)
                    {
                        lin--;
                        col++;
                    }
                
                    novaPos = new Posicao(lin, col);
                    if(lin < 0 || col < 0 || lin > terreno.getAltura() || col > terreno.getLargura())
                    {
                        morre = true;
                        tentativa++;
                    }
                    else
                    {
                    
                        actor = terreno.getObjectAt(novaPos);
            
                        if(actor instanceof Mangusto)
                        {
                            morre = true;
                            tentativa++;
                        }
                        else if(actor instanceof Cobra)
                        {
                            morre = true;
                            tentativa++;
                        }
                        else if(actor instanceof CorpoCobra)
                        {
                            morre = true;
                            tentativa++;
                        }
                        else if(actor instanceof Escaravelho)
                        {
                            morre = true;
                            tentativa++;
                        }
                        else if(actor instanceof CorpoCobra)
                        {
                            morre = true;
                            tentativa++;
                        }
                        else if(actor instanceof Ovo)
                        {
                            morre = true;
                            tentativa++;
                        }
                    }

                }while(tentativa < 10 && morre == true);
                this.move(novaPos, terreno);
                vida--;
                proximoMovimento = TEMPO_PARA_MOVER;
            }
            else
            {
                proximoMovimento--;
            }
            vida--;
        }
    }
    
    private void move(Posicao pos, Terreno terreno)
    {
        
        Posicao posActual = this.getPosicao();
        int linha = pos.getLinha();
        int coluna = pos.getColuna();
        
        //VER O QUE ESTA NA POSICAO E TOMAR AS CONSEQUENCIAS
        if(linha < 0 || coluna < 0 || linha > terreno.getAltura() || coluna > terreno.getLargura())
        {
            this.setDead();
        }
        else
        {
            Actor actorNaPos = (Actor)terreno.getObjectAt(pos);
            if(actorNaPos instanceof Ovo)
            {
                this.setDead();
            }
            else if(actorNaPos instanceof Cobra)
            {
                this.setDead();
            }
            else if(actorNaPos instanceof CorpoCobra)
            {
                this.setDead();
            }
            else if(actorNaPos instanceof Escaravelho)
            {
                actorNaPos.setDead();
                this.setDead();
            }
            else if(actorNaPos instanceof Mangusto)
            {
                this.setDead();
            }
         
            this.setPosicao(pos);    
        }
    }

}
