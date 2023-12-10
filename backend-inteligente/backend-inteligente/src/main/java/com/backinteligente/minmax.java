package com.backinteligente;

import java.util.ArrayList;
import java.util.List;

public class minmax {
    private char[][] board;

    protected void setup() {
        System.out.println("Hola, soy un agente JADE (buenas).");

        // Inicializar el tablero
        board = new char[][] {
                { ' ', ' ', ' ' },
                { ' ', 'C', ' ' },
                { 'T', 'C', ' ' }
        };
    }

    // Método para actualizar el tablero
    public void updateBoard(char[][] newBoard) {
        this.board = newBoard;
    }

    // Método para obtener el mejor movimiento usando minimax con poda alfa-beta
    public int[] getBestMove() {
        int[] bestMove = minimax(2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return bestMove;
    }

    // Implementación del algoritmo minimax con poda alfa-beta
    private int[] minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        // Obtener movimientos disponibles
        List<int[]> availableMoves = getAvailableMoves();

        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = new int[] { -1, -1 };

        for (int[] move : availableMoves) {
            // Simular el movimiento
            makeMove(move, maximizingPlayer ? 'C' : 'T');

            // Llamada recursiva a minimax
            int score = minimax(depth - 1, alpha, beta, !maximizingPlayer)[0];

            // Deshacer el movimiento simulado
            undoMove(move);

            // Actualizar alpha y beta
            if (maximizingPlayer) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestScore);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, bestScore);
            }

            // Realizar poda alfa-beta
            if (beta <= alpha) {
                break;
            }
        }

        return new int[] { bestScore, bestMove[0], bestMove[1] };
    }

    // Método para obtener los movimientos disponibles en el tablero
    private List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == ' ') {
                    moves.add(new int[] { i, j });
                }
            }
        }
        return moves;
    }

    // Método para simular un movimiento en el tablero
    private void makeMove(int[] move, char player) {
        board[move[0]][move[1]] = player;
    }

    // Método para deshacer un movimiento simulado en el tablero
    private void undoMove(int[] move) {
        board[move[0]][move[1]] = ' ';
    }
}
