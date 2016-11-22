import enumerations.Rank;
import enumerations.Suit;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ScoreTests {

    Set<Card> cards = new HashSet<Card>();

    @Test
    public void when_ace_is_present_value_of_eleven_is_applied_appropriately(){
        ScoreI score = Dependencies.score.make();
        Card card1 = new Card(Suit.Diamonds, Rank.Ace);
        Card card2 = new Card(Suit.Spades, Rank.Five);
        cards.add(card1);
        cards.add(card2);

        Integer result = score.scoreHand(cards);

        assertEquals(result, Integer.valueOf(16));
    }

    @Test
    public void when_ace_is_present_value_of_one_is_applied_appropriately(){
        ScoreI score = Dependencies.score.make();
        Card card1 = new Card(Suit.Diamonds, Rank.Ace);
        Card card2 = new Card(Suit.Spades, Rank.Five);
        Card card3 = new Card(Suit.Hearts, Rank.Jack);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        Integer result = score.scoreHand(cards);

        assertEquals(result, Integer.valueOf(16));
    }

    @Test
    public void when_ace_and_card_of_value_ten_are_present_value_is_21(){
        ScoreI score = Dependencies.score.make();
        Card card1 = new Card(Suit.Diamonds, Rank.Ace);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        cards.add(card1);
        cards.add(card2);

        Integer result = score.scoreHand(cards);

        assertEquals(result, Integer.valueOf(21));
    }

    @Test
    public void when_face_card_present_ten_added_to_score(){
        ScoreI score = Dependencies.score.make();
        Card card1 = new Card(Suit.Diamonds, Rank.Seven);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        cards.add(card1);
        cards.add(card2);

        Integer result = score.scoreHand(cards);

        assertEquals(result, Integer.valueOf(17));
    }
}
