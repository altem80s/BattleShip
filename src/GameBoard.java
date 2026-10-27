import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Owns the 10-by-10 cell grid and places the five ships.
 */
public class GameBoard {
    public static final int BOARD_SIZE = 10;
    private final Cell[][] cells = new Cell[BOARD_SIZE][BOARD_SIZE];
    private final ArrayList<Ship> ships = new ArrayList<>();
    private final Random random = new Random();

    public GameBoard() {
        initializeBoard();
    }

    public void initializeBoard() {
        ships.clear();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
        placeShipsRandomly();
    }

    public void placeShipsRandomly() {
        // Place longer ships first to make placement easier.
        int[] sizes = {5,  4, 3, 3, 2};
        for (int size : sizes) {
            boolean placed = false;
            int attempts = 0;
            while (!placed && attempts < 10000) {
                attempts++;
                boolean horizontal = random.nextBoolean();
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);
                placed = placeShip(new Ship(size, horizontal, row, col));
            }
            // This fallback should be extraordinarily unlikely, but prevents an infinite loop.
            if (!placed) {
                throw new IllegalStateException("Could not place all ships on the board.");
            }
        }
    }

    public boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (size < 1 || row < 0 || col < 0 ||
                row >= BOARD_SIZE || col >= BOARD_SIZE) {
            return false;
        }
        if (horizontal && col + size > BOARD_SIZE) {
            return false;
        }
        if (!horizontal && row + size > BOARD_SIZE) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            int checkRow = row + (horizontal ? 0 : i);
            int checkCol = col + (horizontal ? i : 0);
            if (cells[checkRow][checkCol].hasShip()) {
                return false;
            }
        }
        return true;
    }

    public boolean placeShip(Ship ship) {
        if (ship == null || !canPlaceShip(
                ship.getStartRow(), ship.getStartCol(),
                ship.getSize(), ship.isHorizontal())) {
            return false;
        }

        for (int i = 0; i < ship.getSize(); i++) {
            int row = ship.getStartRow() + (ship.isHorizontal() ? 0 : i);
            int col = ship.getStartCol() + (ship.isHorizontal() ? i : 0);
            cells[row][col].setShip(ship);
        }
        ships.add(ship);
        return true;
    }

    /**
     * Returns the cell at the requested coordinates. The controller processes the shot.
     */
    public Cell fireShot(int row, int col) {
        return getCell(row, col);
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException("Cell coordinates must be between 0 and 9.");
        }
        return cells[row][col];
    }

    public ArrayList<Ship> getShips() {
        return new ArrayList<>(ships);
    }

    public int getTotalShipCells() {
        int total = 0;
        for (Ship ship : ships) {
            total += ship.getSize();
        }
        return total;
    }
}
