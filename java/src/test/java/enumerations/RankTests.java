package enumerations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RankTests {

    @Test
    public void has_thirteen_values(){
        assertEquals(Rank.values().length, 13);
    }

    @Test
    public void non_face_cards_provide_expected_values(){
        assertEquals(Rank.Ace.getValue(), Integer.valueOf(1));
        assertEquals(Rank.Two.getValue(), Integer.valueOf(2));
        assertEquals(Rank.Three.getValue(), Integer.valueOf(3));
        assertEquals(Rank.Four.getValue(), Integer.valueOf(4));
        assertEquals(Rank.Five.getValue(), Integer.valueOf(5));
        assertEquals(Rank.Six.getValue(), Integer.valueOf(6));
        assertEquals(Rank.Seven.getValue(), Integer.valueOf(7));
        assertEquals(Rank.Eight.getValue(), Integer.valueOf(8));
        assertEquals(Rank.Nine.getValue(), Integer.valueOf(9));
        assertEquals(Rank.Ten.getValue(), Integer.valueOf(10));
    }

    @Test
    public void face_cards_value_are_ten(){
        assertEquals(Rank.Jack.getValue(), Integer.valueOf(10));
        assertEquals(Rank.Queen.getValue(), Integer.valueOf(10));
        assertEquals(Rank.King.getValue(), Integer.valueOf(10));
    }
}
