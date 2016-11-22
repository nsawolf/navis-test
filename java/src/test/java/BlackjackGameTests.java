import enumerations.Action;
import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.*;

public class BlackjackGameTests {

    private Deck mockedDeck = mock(Deck.class);
    private PlayerI mockedBot = mock(BotPlayer.class);
    private PlayerI mockedHuman = mock(HumanPlayer.class);
    private Hand mockedHand = mock(Hand.class);
    private Score mockedScore = mock(Score.class);
    private BlackJackGame game = new BlackJackGame();
    private Integer scoreUnder21 = 20;
    private Integer scoreOver21 = 23;
    private Integer scoreAtStay = 17;
    private Integer scoreBlackJack = 21;

    @Before
    public void setup(){
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.botPlayer.override(() -> mockedBot);
        Dependencies.humanPlayer.override(() -> mockedHuman);
        Dependencies.score.override(() -> mockedScore);
        Dependencies.game.make();
    }

    @After
    public void teardown(){
        Dependencies.deck.close();
        Dependencies.botPlayer.close();
        Dependencies.humanPlayer.close();
        Dependencies.score.close();
        Dependencies.game.close();
    }

    @Test
    public void human_player_wins_when_bot_busts() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Nine));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Busted);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreOver21).thenReturn(scoreUnder21);

        String result = game.play();

        assertEquals(result, "Human player wins the game with " + scoreUnder21 + " Bot loses with " + scoreOver21);
        verify(mockedHuman, times(2)).nextAction(any(Hand.class));
        verify(mockedBot, times(2)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(6)).dealCard();
    }

    @Test
    public void bot_player_wins_when_human_busts() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Nine));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Busted);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Stay);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreAtStay).thenReturn(scoreOver21);

        String result = game.play();

        assertEquals(result, "Bot player wins the game with " + scoreAtStay + " Human loses with " + scoreOver21);
        verify(mockedHuman, times(2)).nextAction(any(Hand.class));
        verify(mockedBot, times(1)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(5)).dealCard();
    }

    @Test
    public void goes_until_player_with_highest_score_under_21_wins() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Ten));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreAtStay).thenReturn(scoreUnder21);

        String result = game.play();

        assertEquals(result, "Human player wins the game with " + scoreUnder21 + " Bot loses with " + scoreAtStay);
        verify(mockedHuman, times(2)).nextAction(any(Hand.class));
        verify(mockedBot, times(2)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(6)).dealCard();
    }

    @Test
    public void draw_when_both_players_have_same_score_under_21() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Ten));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreAtStay).thenReturn(scoreAtStay);

        String result = game.play();

        assertEquals(result, "Game is a draw at " + scoreAtStay);
        verify(mockedHuman, times(2)).nextAction(any(Hand.class));
        verify(mockedBot, times(2)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(6)).dealCard();
    }

    @Test
    public void human_player_wins_with_a_natural_21() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Ten));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Stay);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreBlackJack).thenReturn(scoreBlackJack);

        String result = game.play();

        assertTrue(result.contains(scoreBlackJack.toString()));
        verify(mockedHuman, times(1)).nextAction(any(Hand.class));
        verify(mockedBot, times(2)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(5)).dealCard();
    }

    @Test
    public void bot_player_wins_with_a_natural_21() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Ten));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Stay);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Stay);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreBlackJack).thenReturn(scoreAtStay);

        String result = game.play();

        assertTrue(result.contains(scoreBlackJack.toString()));
        assertTrue(result.contains(scoreAtStay.toString()));
        verify(mockedHuman, times(1)).nextAction(any(Hand.class));
        verify(mockedBot, times(1)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(4)).dealCard();
    }

    @Test
    public void no_one_wins_when_both_players_bust() throws OutOfCardsException{
        Card card1 = new Card(Suit.Clubs, Rank.Nine);
        Card card2 = new Card(Suit.Hearts, Rank.Jack);
        mockedHand.addCard(card1);
        mockedHand.addCard(card2);
        when(mockedDeck.dealCard()).thenReturn(new Card(Suit.Clubs, Rank.Ten));
        when(mockedHuman.getHand()).thenReturn(mockedHand);
        when(mockedBot.getHand()).thenReturn(mockedHand);
        when(mockedHuman.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Busted);
        when(mockedBot.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Busted);
        when(mockedScore.scoreHand(anySet())).thenReturn(scoreOver21).thenReturn(scoreOver21);

        String result = game.play();

        assertEquals(result, "No one wins this game, both players busted. Human score " + scoreOver21 + " Bot score " + scoreOver21);
        verify(mockedHuman, times(2)).nextAction(any(Hand.class));
        verify(mockedBot, times(2)).nextAction(any(Hand.class));
        verify(mockedScore, times(2)).scoreHand(anySet());
        verify(mockedDeck, times(6)).dealCard();
    }


}
