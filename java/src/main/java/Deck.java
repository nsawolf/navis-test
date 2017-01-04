import enumerations.Rank;
import enumerations.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    public List<Card> cardDeck;

    public Deck(){
        cardDeck = new ArrayList<Card>();
        for (Suit s : Suit.values()){
            for (Rank r : Rank.values()){
                Card c = new Card(s, r);
                cardDeck.add(c);
            }
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(cardDeck, new Random(Dependencies.now.make()));
    }

    public Card dealCard() throws OutOfCardsException {
        if (cardDeck.size() <= 52 && cardDeck.size() > 0){
            Card dealtCard = cardDeck.get(0);
            cardDeck.remove(0);
            return dealtCard;
        }
       throw new OutOfCardsException("No cards remain in the deck.");
    }

    public int size(){
        return cardDeck.size();
    }

}
