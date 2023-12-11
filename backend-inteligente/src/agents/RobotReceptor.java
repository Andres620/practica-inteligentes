package agents;

import behaviours.TrikiSimpleBehaviour;
import jade.core.Agent;
import behaviours.ReceptorSimpleBehaviour;

public class RobotReceptor extends Agent{

    protected void setup() {
        ReceptorSimpleBehaviour trikiBehaviour = new ReceptorSimpleBehaviour(this);
        addBehaviour(trikiBehaviour);
    }
}
