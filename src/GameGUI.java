import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Swing interface for the Battleship game.
 */
public class GameGUI extends JFrame {
    private final JButton[][] boardButtons =
            new JButton[GameBoard.BOARD_SIZE][GameBoard.BOARD_SIZE];
    private final GameController gameController;
    private final JLabel titleLabel = new JLabel("BATTLESHIP", SwingConstants.CENTER);
    private final JLabel missLabel = new JLabel();
    private final JLabel strikeLabel = new JLabel();
    private final JLabel totalMissLabel = new JLabel();
    private final JLabel totalHitLabel = new JLabel();
    private final JButton playAgainButton = new JButton("Play Again");
    private final JButton quitButton = new JButton("Quit");

    public GameGUI(GameController controller) {
        super("Battleship");
        this.gameController = controller;
        buildInterface();
    }

    private void buildInterface() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(10, 10, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton("~");
                button.setFont(new Font("SansSerif", Font.BOLD, 18));
                button.setForeground(new Color(0, 90, 160));
                button.setBackground(new Color(190, 230, 250));
                button.setOpaque(true);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(48, 42));
                final int r = row;
                final int c = col;
                button.addActionListener(e -> gameController.fireShot(r, c));
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        JPanel countersPanel = new JPanel(new GridLayout(2, 2, 8, 5));
        countersPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        countersPanel.add(missLabel);
        countersPanel.add(strikeLabel);
        countersPanel.add(totalMissLabel);
        countersPanel.add(totalHitLabel);
        bottomPanel.add(countersPanel, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel();
        controlsPanel.add(playAgainButton);
        controlsPanel.add(quitButton);
        bottomPanel.add(controlsPanel, BorderLayout.SOUTH);

        playAgainButton.addActionListener(e -> gameController.handleReplay());
        quitButton.addActionListener(e -> gameController.handleQuit());
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(610, 650));
        setLocationRelativeTo(null);
    }

    public void displayGame() {
        setVisible(true);
    }

    public void updateBoard() {
        GameBoard board = gameController.getGameBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Cell cell = board.getCell(row, col);
                JButton button = boardButtons[row][col];

                if (cell.isHit()) {
                    button.setText("X");
                    button.setForeground(Color.RED);
                    button.setBackground(new Color(255, 220, 220));
                    button.setEnabled(false);
                } else if (cell.isMiss()) {
                    button.setText("M");
                    button.setForeground(new Color(180, 130, 0));
                    button.setBackground(new Color(255, 245, 160));
                    button.setEnabled(false);
                } else {
                    button.setText("~");
                    button.setForeground(new Color(0, 90, 160));
                    button.setBackground(new Color(190, 230, 250));
                    button.setEnabled(true);
                }
            }
        }
    }

    public void updateCounters() {
        ScoreBoard score = gameController.getScoreBoard();
        missLabel.setText("Misses in a row: " + score.getMissCount() + " / 5");
        strikeLabel.setText("Strikes: " + score.getStrikeCount() + " / 3");
        totalMissLabel.setText("Total misses: " + score.getTotalMissCount() + " / 83");
        totalHitLabel.setText("Total hits: " + score.getTotalHitCount() + " / 17");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public boolean confirmReplay() {
        return JOptionPane.showConfirmDialog(this,
                "Are you sure you want to play again?",
                "Play Again", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void setGameEnabled(boolean enabled) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Cell cell = gameController.getGameBoard().getCell(row, col);
                boardButtons[row][col].setEnabled(enabled && !cell.isSelected());
            }
        }
    }
}
