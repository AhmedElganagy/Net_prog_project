package view;

import viewmodel.GameViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private GameViewModel viewModel;
    private boolean multiplayerMode;
    private JLabel statusLabel;
    private JPanel boardPanel;

    public GameView() {
        initializeUI();
        showModeSelection();
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Status panel
        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel("Current Player: X", SwingConstants.CENTER);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.NORTH);

        // Game board panel
        boardPanel = new JPanel(new GridLayout(3, 3));
        add(boardPanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> resetGame());
        controlPanel.add(newGameButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void showModeSelection() {
        String[] options = {"Single Player", "Multiplayer"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose Game Mode",
                "Game Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            multiplayerMode = false;
            startGame();
        } else if (choice == 1) {
            multiplayerMode = true;
            startMultiplayerSetup();
        } else {
            System.exit(0);
        }
    }

    private void startMultiplayerSetup() {
        boolean player1Accept = showAcceptDialog("Player 1 (X)");
        boolean player2Accept = showAcceptDialog("Player 2 (O)");

        if (player1Accept && player2Accept) {
            startGame();
        } else {
            JOptionPane.showMessageDialog(null, "Game Cancelled.");
            System.exit(0);
        }
    }

    private boolean showAcceptDialog(String playerName) {
        int response = JOptionPane.showConfirmDialog(
                null,
                playerName + ", do you accept the game?",
                "Game Request",
                JOptionPane.YES_NO_OPTION
        );
        return response == JOptionPane.YES_OPTION;
    }

    private void startGame() {
        viewModel = new GameViewModel();
        initializeButtons();
        setVisible(true);
    }

    private void initializeButtons() {
        boardPanel.removeAll(); // Clear existing buttons
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                button.setFocusPainted(false);
                button.setBackground(Color.WHITE);
                button.setPreferredSize(new Dimension(100, 100));
                
                final int r = row;
                final int c = col;
                button.addActionListener(e -> handleButtonClick(r, c, button));
                
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }
        
        boardPanel.revalidate();
        boardPanel.repaint();
        updateStatus();
    }

    private void handleButtonClick(int row, int col, JButton button) {
        String currentPlayerBeforeMove = viewModel.getCurrentPlayer();
        
        if (viewModel.makeMove(row, col)) {
            button.setText(currentPlayerBeforeMove);
            
            if (viewModel.isGameEnded()) {
                disableAllButtons();
                showGameResult(currentPlayerBeforeMove);
            } else {
                updateStatus();
            }
        }
    }

    private void disableAllButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void showGameResult(String winningPlayer) {
        String result = viewModel.getGameResult();
        String message = result.equals("Draw") ? 
            "Game ended in a draw!" : 
            winningPlayer + " wins!";
        
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Would you like to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        viewModel.resetGame();
        resetButtons();
        updateStatus();
    }

    private void resetButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
    }

    private void updateStatus() {
        statusLabel.setText("Current Player: " + viewModel.getCurrentPlayer());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameView());
    }
}