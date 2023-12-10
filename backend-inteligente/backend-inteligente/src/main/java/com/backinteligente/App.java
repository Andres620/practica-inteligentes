package com.backinteligente;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {

    private static char[][] currentBoard;

    public static void main(String[] args) {
        // Estado actual del juego
        currentBoard = new char[][] {
                { 'C', ' ', 'C' },
                { 'T', 'T', ' ' },
                { 'T', 'C', ' ' }
        };

        // Imprime el tablero actual
        System.out.println("Estado actual del tablero:");
        printBoard();

        // Encuentra la mejor jugada utilizando el algoritmo minimax
        int[] bestMove = minimax(currentBoard, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        // Imprime la mejor jugada encontrada
        System.out.println("Mejor jugada para 'T': Fila " + bestMove[0] + ", Columna " + bestMove[1]);
    }

    // Función minimax
    private static int[] minimax(char[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || isGameOver(board)) {
            int utility = evaluate(board);
            return new int[] { utility, -1, -1 };
        }

        int[] bestMove = new int[] { maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE, -1, -1 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = maximizingPlayer ? 'T' : 'C';

                    int[] result = minimax(board, depth - 1, alpha, beta, !maximizingPlayer);

                    // Deshacer la jugada
                    board[i][j] = ' ';

                    if (maximizingPlayer) {
                        if (result[0] > bestMove[0]) {
                            bestMove[0] = result[0];
                            bestMove[1] = i;
                            bestMove[2] = j;
                        }
                        alpha = Math.max(alpha, bestMove[0]);
                    } else {
                        if (result[0] < bestMove[0]) {
                            bestMove[0] = result[0];
                            bestMove[1] = i;
                            bestMove[2] = j;
                        }
                        beta = Math.min(beta, bestMove[0]);
                    }

                    // Poda alfa-beta
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestMove;
    }

    // Funciones auxiliares
    private static boolean isGameOver(char[][] board) {
        return checkWinner('T') || checkWinner('C') || isBoardFull();
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWinner(char player) {
        // Verifica si hay una fila, columna u diagonal con tres fichas del mismo
        // jugador
        for (int i = 0; i < 3; i++) {
            if ((currentBoard[i][0] == player && currentBoard[i][1] == player && currentBoard[i][2] == player) ||
                    (currentBoard[0][i] == player && currentBoard[1][i] == player && currentBoard[2][i] == player)) {
                return true;
            }
        }

        // Verifica las diagonales
        if ((currentBoard[0][0] == player && currentBoard[1][1] == player && currentBoard[2][2] == player) ||
                (currentBoard[0][2] == player && currentBoard[1][1] == player && currentBoard[2][0] == player)) {
            return true;
        }

        return false;
    }

    private static int evaluate(char[][] board) {
        int score = 0;

        // Evalúa filas y columnas
        for (int i = 0; i < 3; i++) {
            score += evaluateLine(board[i][0], board[i][1], board[i][2]); // Filas
            score += evaluateLine(board[0][i], board[1][i], board[2][i]); // Columnas
        }

        // Evalúa diagonales
        score += evaluateLine(board[0][0], board[1][1], board[2][2]); // Diagonal principal
        score += evaluateLine(board[0][2], board[1][1], board[2][0]); // Diagonal secundaria

        return score;
    }

    private static int evaluateLine(char cell1, char cell2, char cell3) {
        int score = 0;

        // Evalúa la línea formada por tres celdas
        if (cell1 == 'T') {
            score = 1;
        } else if (cell1 == 'C') {
            score = -1;
        }

        if (cell2 == 'T') {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cell2 == 'C') {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        if (cell3 == 'T') {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cell3 == 'C') {
            if (score < 0) {
                score *= 10;
            } else if (score > 0) {
                return 0;
            } else {
                score = -1;
            }
        }

        return score;

    }

    // Imprimir el tablero
    private static void printBoard() {
        for (char[] row : currentBoard) {
            System.out.println(Arrays.toString(row));
        }
    }

}
