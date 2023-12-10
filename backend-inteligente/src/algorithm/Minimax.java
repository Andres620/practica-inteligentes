package algorithm;

public class Minimax {

    static char[][] board = {
            { ' ', ' ', ' ' },
            { ' ', 'T', ' ' },
            { 'C', 'C', 'T' }
    };

    // public static void main(String[] args) {
    // int[] bestMove = bestMove();
    // if (bestMove[0] == -1 && bestMove[1] == -1) {
    // System.out.println("¡Juego terminado!.");
    // }
    // System.out.println("Mejor movimiento: Fila " + bestMove[0] + ", Columna " +
    // bestMove[1]);
    // }

    public static int[] bestMove() {
        int bestEval = Integer.MIN_VALUE;
        int[] bestMove = new int[] { -1, -1 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    int row = findEmptyRow(board, j);
                    if (row != -1) {
                        board[row][j] = 'C';
                        Tuple eval = minimax(board, 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        board[row][j] = ' '; // Deshacer movimiento

                        if (eval.getNumber() > bestEval) {
                            bestEval = eval.getNumber();
                            bestMove[0] = row;
                            bestMove[1] = j;
                        }

                        if (eval.getPlayer() != ' ') {
                            // Juego terminado
                            // System.out.println("¡Juego terminado! Ganador: " + eval.getPlayer());
                            return bestMove; // Indicador de juego terminado
                        }
                    }
                }
            }
        }

        return bestMove;
    }

    // Algoritmo Minimax con poda alfa-beta
    static Tuple minimax(char[][] board, int depth, boolean isMaximizer, int alpha, int beta) {
        if (depth == 0 || isTerminal(board)) {
            return evaluation(board);
        }

        if (isMaximizer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        int row = findEmptyRow(board, j);
                        if (row != -1) {
                            board[row][j] = 'C';
                            Tuple eval = minimax(board, depth - 1, false, alpha, beta);
                            board[row][j] = ' '; // Deshacer movimiento
                            maxEval = Math.max(maxEval, eval.getNumber());
                            alpha = Math.max(alpha, eval.getNumber());
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
            }
            return new Tuple(maxEval, ' ');
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        int row = findEmptyRow(board, j);
                        if (row != -1) {
                            board[row][j] = 'T';
                            Tuple eval = minimax(board, depth - 1, true, alpha, beta);
                            board[row][j] = ' '; // Deshacer movimiento
                            minEval = Math.min(minEval, eval.getNumber());
                            beta = Math.min(beta, eval.getNumber());
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
            }
            return new Tuple(minEval, ' ');
        }
    }

    // Función de evaluación: retorna 1 si el jugador 'C' (cuadrado) gana, -1 si el
    // jugador 'T' (triángulo) gana, 0 si empate o no hay ganador
    static Tuple evaluation(char[][] board) {
        if (isWinner(board, 'C')) {
            return new Tuple(1, 'C');
        } else if (isWinner(board, 'T')) {
            return new Tuple(-1, 'T');
        } else {
            return new Tuple(0, 'E'); // Empate
        }
    }

    // Verifica si hay un ganador para un jugador específico en filas y columnas
    // (movimientos ortogonales)
    static boolean isWinner(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if ((board[i][0] == player) && (board[i][1] == player) && (board[i][2] == player)) {
                return true;
            }
            // Check columns
            if ((board[0][i] == player) && (board[1][i] == player) && (board[2][i] == player)) {
                return true;
            }
        }
        return false;
    }

    /// Verifica si el juego ha terminado (hay un ganador o el tablero está lleno)
    static boolean isTerminal(char[][] board) {
        return isWinner(board, 'C') || isWinner(board, 'T') || boardFull(board);
    }

    // Verifica si el tablero está lleno
    static boolean boardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Todavía hay casillas vacías en el tablero
                }
            }
        }
        return true; // Tablero lleno, empate
    }

    // Encuentra la fila vacía más baja en una columna dada
    static int findEmptyRow(char[][] board, int column) {
        for (int i = 2; i >= 0; i--) {
            if (board[i][column] == ' ') {
                return i;
            }
        }
        return -1; // Columna llena
    }

    static class Tuple {
        private final int number;
        private final char player;

        public Tuple(int number, char player) {
            this.number = number;
            this.player = player;
        }

        public int getNumber() {
            return number;
        }

        public char getPlayer() {
            return player;
        }
    }

    // Set the board in the Minimax class
    public static void setBoard(char[][] newBoard) {
        board = newBoard;
    }
}
