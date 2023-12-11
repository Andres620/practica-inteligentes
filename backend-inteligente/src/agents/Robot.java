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
        TrikiSimpleBehaviour trikiBehaviour = new TrikiSimpleBehaviour(this);
        addBehaviour(trikiBehaviour);
    }
}