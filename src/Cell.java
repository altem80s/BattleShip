/**
 * Represents one square on the 10-by-10 Battleship board.
 */
public class Cell {
    private final int row;
    private final int col;
    private Ship ship;
    private boolean selected;
    private boolean hit;
    private boolean miss;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        reset();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isMiss() {
        return miss;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Records a shot. Returns true if the cell contains a ship.
     * Repeated shots are ignored by the controller before this is called.
     */
    public boolean receiveShot() {
        if (selected) {
            return hit;
        }
        selected = true;
        if (hasShip()) {
            markHit();
            return true;
        }
        markMiss();
        return false;
    }

    public void markHit() {
        hit = true;
        miss = false;
        selected = true;
    }

    public void markMiss() {
        miss = true;
        hit = false;
        selected = true;
    }

    public void reset() {
        ship = null;
        selected = false;
        hit = false;
        miss = false;
    }
}
