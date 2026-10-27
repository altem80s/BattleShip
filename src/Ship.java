/**
 * Represents a ship and tracks the number of hits it has received.
 */
public class Ship {
    private final int size;
    private final boolean horizontal;
    private final int startRow;
    private final int startCol;
    private int hits;

    public Ship(int size, boolean horizontal, int startRow, int startCol) {
        this.size = size;
        this.horizontal = horizontal;
        this.startRow = startRow;
        this.startCol = startCol;
        this.hits = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void registerHit() {
        if (hits < size) {
            hits++;
        }
    }

    public boolean isSunk() {
        return hits >= size;
    }

    public int getHits() {
        return hits;
    }

    public void reset() {
        hits = 0;
    }
}
