package model;

public class GameModel {
    private String[][] board;
    private String currentPlayer;
    private boolean gameEnded;
    private int moveCount;

    public GameModel() {
        board = new String[3][3];
        currentPlayer = "X";
        gameEnded = false;
        moveCount = 0;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void resetGame() {
        board = new String[3][3];
        currentPlayer = "X";
        gameEnded = false;
        moveCount = 0;
    }

    public String[][] getBoard() {
        // Return a deep copy to prevent external modification
        String[][] copy = new String[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    public boolean makeMove(int row, int col) {
        // Validate input
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return false;
        }

        if (board[row][col] == null && !gameEnded) {
            board[row][col] = currentPlayer;
            moveCount++;
            
            if (checkWinner(row, col)) {
                gameEnded = true;
            } else if (moveCount == 9) {
                gameEnded = true; // Draw
            } else {
                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            }
            return true;
        }
        return false;
    }

    private boolean checkWinner(int row, int col) {
        // Check row
        if (board[row][0] != null && 
            board[row][0].equals(board[row][1]) && 
            board[row][0].equals(board[row][2])) {
            return true;
        }
        
        // Check column
        if (board[0][col] != null && 
            board[0][col].equals(board[1][col]) && 
            board[0][col].equals(board[2][col])) {
            return true;
        }
        
        // Check main diagonal
        if (row == col) {
            if (board[0][0] != null && 
                board[0][0].equals(board[1][1]) && 
                board[0][0].equals(board[2][2])) {
                return true;
            }
        }
        
        // Check anti-diagonal
        if (row + col == 2) {
            if (board[0][2] != null && 
                board[0][2].equals(board[1][1]) && 
                board[0][2].equals(board[2][0])) {
                return true;
            }
        }
        
        return false;
    }
    
    public String getGameResult() {
        if (!gameEnded) return null;
        if (moveCount == 9) return "Draw";
        return currentPlayer + " wins!";
    }
}