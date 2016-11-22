import enumerations.Rank;
import enumerations.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeckTests {

    @Test
    public void starts_in_rank_suit_order(){
        Deck deck = new Deck();
        List<Card> new_deck = new ArrayList<Card>();
        for (Suit s : Suit.values()){
            for (Rank r : Rank.values()){
                new_deck.add(new Card(s, r));
            }
        }
        assertTrue(deck.cardDeck.equals(new_deck));
        assertTrue(deck.cardDeck.get(0).equals(new_deck.get(0)));
        assertEquals(deck.cardDeck.get(0), new Card(Suit.Clubs, Rank.Ace));
    }

    @Test
    public void starts_with_52_cards(){
        Deck deck = new Deck();
        assertEquals(deck.size(), 52);
    }

    @Test
    public void deals_card_from_top_of_deck() throws OutOfCardsException {
        Deck deck = new Deck();
        assertEquals(deck.dealCard(), new Card(Suit.Clubs, Rank.Ace));
        assertEquals(deck.size(), 51);
    }

    @Test(expected = OutOfCardsException.class)
    public void deals_cards_from_top_until_no_more_cards() throws OutOfCardsException {
        Deck deck = new Deck();
        for(int i = 0; i < 52; i++){
            deck.dealCard();
        }
        deck.dealCard();
    }

    // -- maybe pass in random number generator so it can be controlled (take control to allow for easier testing)
    //     if use a random number generator can assume it's already been tested in and of itself
    //     in spec may want to say something about the number generator... depending on use

    @Test
    public void is_shuffled_cards_in_different_order(){
        Deck deck = new Deck();
        deck.shuffleDeck(10L);

        assertEquals(deck.cardDeck.get(0), new Card(Suit.Spades, Rank.Five));
        assertEquals(deck.cardDeck.get(1), new Card(Suit.Diamonds, Rank.Jack));
        assertEquals(deck.cardDeck.get(2), new Card(Suit.Diamonds, Rank.Four));
        assertEquals(deck.cardDeck.get(3), new Card(Suit.Clubs, Rank.Seven));
    }

    @Test
    public void is_shuffled_no_cards_are_lost(){
        Deck deck = new Deck();
        deck.shuffleDeck(10L);
        HashSet<Card>s_deck = new HashSet<Card>();
        s_deck.addAll(deck.cardDeck);
        assertEquals(52, s_deck.size());
    }

    @Test
    public void is_shuffled_all_cards_present(){
        Deck deck = new Deck();
        HashSet<Card> original_deck = new HashSet<Card>();
        original_deck.addAll(deck.cardDeck);

        deck.shuffleDeck(10L);
        HashSet<Card> shuffle_deck = new HashSet<Card>();
        shuffle_deck.addAll(deck.cardDeck);
        assertTrue(original_deck.equals(shuffle_deck));
    }
}
