import javax.swing.SwingUtilities;

/**
 * Entry point for the single-player Battleship game.
 */
public class Battleship {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameController::new);
    }
}
