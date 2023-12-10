package com.backinteligente;

public class main {
    public static void main(String[] args) {
        // Crear el agente y simular una actualización del tablero
        minmax robot = new minmax();
        robot.updateBoard(new char[][]{
            {' ', ' ', ' '},
            {' ', 'C', ' '},
            {'T', 'C', ' '}
        });
    
        // Obtener y mostrar el mejor movimiento según el algoritmo minimax
        int[] bestMove = robot.getBestMove();
        System.out.println("Mejor movimiento: Fila " + bestMove[0] + ", Columna " + bestMove[1]);
    }
    
}
