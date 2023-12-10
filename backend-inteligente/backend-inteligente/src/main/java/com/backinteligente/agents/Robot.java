package com.backinteligente.agents;

import com.backinteligente.behaviours.TrikiSimpleBehaviour;

import jade.core.Agent;

public class Robot extends Agent {
    
    protected void setup() {
        System.out.println("Hola, soy un agente JADE.");
        addBehaviour(new TrikiSimpleBehaviour(this));
    }
}
