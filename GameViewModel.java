package viewmodel;

import model.GameModel;

public class GameViewModel {
    private GameModel model;

    public GameViewModel() {
        model = new GameModel();
    }

    public String getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    public boolean isGameEnded() {
        return model.isGameEnded();
    }

    public boolean makeMove(int row, int col) {
        return model.makeMove(row, col);
    }

    public void resetGame() {
        model.resetGame();
    }

    public String getGameResult() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameResult'");
    }
}
