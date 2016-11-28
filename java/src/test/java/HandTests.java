import enumerations.Rank;
import enumerations.Suit;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class HandTests {

    private Card fiveClubs = new Card(Suit.Clubs, Rank.Five);
    private Card jackSpades = new Card(Suit.Spades, Rank.Jack);
    private Card fiveHearts = new Card(Suit.Hearts, Rank.Five);

    @Test
    public void contains_exact_cards_dealt() throws OutOfCardsException {
        Hand hand = new Hand();
        hand.addCard(fiveClubs);

        assertTrue(hand.size() == 1);
    }

    @Test
    public void visible_hand_returns_all_cards_when_not_hidden_for_current_hand() {
        Hand hand = new Hand();
        hand.addCard(fiveHearts);
        hand.addCard(jackSpades);

        String result = hand.visibleHand(false);

        assertEquals(result, fiveHearts.toString() +"," + jackSpades.toString());
    }

    @Test
    public void hide_bottom_indicates_first_in_card_set_is_obscurred() {
        Hand hand = new Hand();
        hand.addCard(fiveHearts);
        hand.addCard(jackSpades);

        String result = hand.visibleHand(true);

        assertEquals(result, hand.obscurredCard + "," + jackSpades.toString());
    }

    @Test
    public void returns_cards_as_a_set() {
        Hand hand = new Hand();
        HashSet<Card> expectedCards = new HashSet<Card>();
        hand.addCard(fiveHearts);
        hand.addCard(jackSpades);
        expectedCards.add(fiveHearts);
        expectedCards.add(jackSpades);

        Set<Card> hcards = hand.getCards();

        assertEquals(expectedCards, hcards);
    }

    @Test
    public void is_cleared_of_cards_when_reset(){
        Hand hand = new Hand();
        hand.addCard(fiveHearts);
        hand.addCard(jackSpades);

        hand.resetHand();

        assertEquals(hand.size(), 0);
    }

}
