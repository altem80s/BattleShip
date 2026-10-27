import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {

    @Test
    public void fiveMissesCauseOneStrike() {
        ScoreBoard score = new ScoreBoard();

        for (int i = 0; i < 5; i++) {
            score.recordMiss();
        }

        Assertions.assertEquals(1, score.getStrikeCount());
        Assertions.assertEquals(0, score.getMissCount());
        Assertions.assertEquals(5, score.getTotalMissCount());
    }

    @Test
    public void hitResetsConsecutiveMisses() {
        ScoreBoard score = new ScoreBoard();

        score.recordMiss();
        score.recordMiss();
        score.recordHit();

        Assertions.assertEquals(0, score.getMissCount());
        Assertions.assertEquals(1, score.getTotalHitCount());
        Assertions.assertEquals(2, score.getTotalMissCount());
    }
}
