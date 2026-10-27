
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    @Test
    public void shipIsNotSunkBeforeEnoughHits() {
        Ship ship = new Ship(3, true, 0, 0);

        ship.registerHit();
        ship.registerHit();

        assertFalse(ship.isSunk());
        assertEquals(2, ship.getHits());
    }

    @Test
    public void shipSinksAfterRequiredHits() {
        Ship ship = new Ship(3, true, 0, 0);

        ship.registerHit();
        ship.registerHit();
        ship.registerHit();

        assertTrue(ship.isSunk());
        assertEquals(3, ship.getHits());
    }
}
