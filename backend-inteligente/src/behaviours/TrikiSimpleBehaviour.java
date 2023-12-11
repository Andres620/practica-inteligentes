package behaviours;

import java.io.IOException;

import algorithm.Minimax;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class TrikiSimpleBehaviour extends SimpleBehaviour {

    Agent myAgent;
    private int[] bestMove;

    public TrikiSimpleBehaviour(Agent myAgent) {
        this.myAgent = myAgent;
    }

    @Override
    public void action() {
        bestMove = Minimax.bestMove();
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("receptor", AID.ISLOCALNAME));
        try {
            message.setContentObject(bestMove);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.myAgent.send(message);
        
        System.out.println("Fila " + bestMove[0] + ", Columna " + bestMove[1]);
    }


    @Override
    public boolean done() {
        return true;
    }

    public int[] getBestMove() {
        return bestMove;
    }
}
