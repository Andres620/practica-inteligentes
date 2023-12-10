package com.backinteligente.behaviours;

import com.backinteligente.algorithm.Minimax;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class TrikiSimpleBehaviour extends SimpleBehaviour {

    Agent myAgent;
    private boolean gameFinished = false;

    public TrikiSimpleBehaviour(Agent myAgent) {
        this.myAgent = myAgent;
    }

    @Override
    public void action() {
        if (!gameFinished) {
            int[] bestMove = Minimax.bestMove();

            if (bestMove[0] == -1 && bestMove[1] == -1) {
                gameFinished = true;
            } else {
                System.out.println("Mejor movimiento: Fila " + bestMove[0] + ", Columna " + bestMove[1]);
            }
        }
    }

    @Override
    public boolean done() {
        System.out.println("¡Juego terminado!.");
        return gameFinished;
    }
}
