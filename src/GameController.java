import javax.swing.JOptionPane;

/**
 * Coordinates the board, score, and GUI.
 */
public class GameController {
    private GameBoard gameBoard;
    private ScoreBoard scoreBoard;
    private GameGUI gameGUI;
    private boolean gameOver;

    public GameController() {
        gameBoard = new GameBoard();
        scoreBoard = new ScoreBoard();
        gameOver = false;
        gameGUI = new GameGUI(this);
        gameGUI.displayGame();
        gameGUI.updateBoard();
        gameGUI.updateCounters();
    }

    public void newGame() {
        gameBoard = new GameBoard();
        scoreBoard.resetCounters();
        gameOver = false;
        gameGUI.updateBoard();
        gameGUI.updateCounters();
        gameGUI.setGameEnabled(true);
    }

    public void fireShot(int row, int col) {
        if (gameOver) {
            return;
        }

        Cell cell = gameBoard.fireShot(row, col);
        if (cell.isSelected()) {
            return;
        }

        boolean hit = cell.receiveShot();
        if (hit) {
            Ship ship = cell.getShip();
            ship.registerHit();
            scoreBoard.recordHit();
            gameGUI.updateBoard();
            gameGUI.updateCounters();

            if (ship.isSunk()) {
                JOptionPane.showMessageDialog(gameGUI,
                        "You sunk a ship of length " + ship.getSize() + "!",
                        "Ship Sunk!", JOptionPane.INFORMATION_MESSAGE);
            }

            if (gameBoard.allShipsSunk()) {
                endGame(true);
            }
        } else {
            scoreBoard.recordMiss();
            gameGUI.updateBoard();
            gameGUI.updateCounters();

            if (scoreBoard.hasLost()) {
                endGame(false);
            }
        }
    }

    public void processHit(Cell cell) {
        if (cell != null && cell.hasShip() && !cell.isSelected()) {
            fireShot(cell.getRow(), cell.getCol());
        }
    }

    public void processMiss() {
        // Misses are processed by fireShot(row, col), which knows the cell coordinates.
    }

    public boolean checkGameOver() {
        return gameOver;
    }

    public void handleReplay() {
        int answer = JOptionPane.showConfirmDialog(gameGUI,
                "Are you sure you want to start a new game?",
                "Play Again", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            newGame();
        }
    }

    public void handleQuit() {
        Object[] options = {"Play Again", "Quit"};
        int answer = JOptionPane.showOptionDialog(gameGUI,
                "Would you like to play again or quit?",
                "Quit Game", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (answer == 0) {
            newGame();
        } else if (answer == 1) {
            gameGUI.dispose();
        }
    }

    private void endGame(boolean won) {
        gameOver = true;
        gameGUI.setGameEnabled(false);
        String message = won
                ? "You sank all five ships. You win!"
                : "You received three strikes. Game over!";
        int answer = JOptionPane.showConfirmDialog(gameGUI,
                message + "\nWould you like to play again?",
                won ? "Victory!" : "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            newGame();
        } else {
            gameGUI.dispose();
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
