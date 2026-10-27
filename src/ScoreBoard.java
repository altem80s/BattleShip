/**
 * Stores the four counters required by the assignment.
 */
public class ScoreBoard {
    private int missCount;
    private int strikeCount;
    private int totalMissCount;
    private int totalHitCount;

    public ScoreBoard() {
        resetCounters();
    }

    public void recordHit() {
        totalHitCount++;
        resetMissCount();
    }

    public void recordMiss() {
        missCount++;
        totalMissCount++;
        if (missCount >= 5) {
            strikeCount++;
            missCount = 0;
        }
    }

    public void resetMissCount() {
        missCount = 0;
    }

    public void resetCounters() {
        missCount = 0;
        strikeCount = 0;
        totalMissCount = 0;
        totalHitCount = 0;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public int getTotalMissCount() {
        return totalMissCount;
    }

    public int getTotalHitCount() {
        return totalHitCount;
    }

    public boolean hasLost() {
        return strikeCount >= 3;
    }
}
