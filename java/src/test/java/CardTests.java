import enumerations.Rank;
import enumerations.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CardTests {

    @Test
    public void of_diff_suit_and_same_rank_are_not_equal() {
        final Card c1 = new Card(Suit.Clubs, Rank.Ace);
        final Card c2 = new Card(Suit.Diamonds, Rank.Ace);
        assertFalse(c1.equals(c2));
    }

    @Test
    public void of_diff_suit_and_rank_are_not_equal() {
        final Card c1 = new Card(Suit.Hearts, Rank.Eight);
        final Card c2 = new Card(Suit.Spades, Rank.Queen);
        assertFalse(c1.equals(c2));
    }

    @Test
    public void of_same_suit_and_same_rank_have_same_hash_code_and_are_equal(){
        final Card c1 = new Card(Suit.Spades, Rank.Jack);
        final Card c2 = new Card(Suit.Spades, Rank.Jack);
        assertTrue(c1.hashCode() == c2.hashCode());
        assertEquals(c1, c2);
    }

    @Test
    public void to_string_returns_rank_and_suit(){
        final Card c1 = new Card(Suit.Spades, Rank.Jack);
        assertEquals(c1.toString(), "Card{rank=Jack, suit=Spades}");
    }
}
