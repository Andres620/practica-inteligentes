/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import behaviours.TrikiSimpleBehaviour;
import jade.core.Agent;

public class Robot extends Agent {
    
    protected void setup() {
        System.out.println("Hola, soy un agente JADE.");

        TrikiSimpleBehaviour trikiBehaviour = new TrikiSimpleBehaviour(this);
        addBehaviour(trikiBehaviour);
    }
}