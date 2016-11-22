package enumerations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SuitTests {

    @Test
    public void has_four_suits(){
        assertEquals(Suit.values().length, 4);
    }

}
