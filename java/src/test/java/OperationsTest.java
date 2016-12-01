import enumerations.Action;
import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OperationsTest {

    private Deck mockedDeck = mock(Deck.class);
    private Hand mockedHand = mock(Hand.class);
    private HumanPlayer mockedHumanPlayer = mock(HumanPlayer.class);
    private BotPlayer mockedBotPlayer = mock(BotPlayer.class);
    private Score mockedScore = mock(Score.class);
    private Long mockedTimeValue = 23L;
    private Card eightDiamonds = new Card(Suit.Diamonds, Rank.Eight);
    private Card tenHearts = new Card(Suit.Hearts, Rank.Ten);
    private Card threeSpades = new Card(Suit.Spades, Rank.Three);
    private Card aceClubs = new Card(Suit.Clubs, Rank.Ace);
    private Operations gameOps = new Operations();

    @Before
    public void setup() {
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.deck.override(() -> mockedDeck);
        Dependencies.humanPlayer.override(() -> mockedHumanPlayer);
        Dependencies.botPlayer.override(() -> mockedBotPlayer);
        Dependencies.score.override(() -> mockedScore);
        Dependencies.now.override(() -> mockedTimeValue);
        mockedHand.addCard(threeSpades);
    }

    @After
    public void tearDown() {
        Dependencies.deck.close();
        Dependencies.humanPlayer.close();
        Dependencies.botPlayer.close();
        Dependencies.score.close();
        Dependencies.now.close();
        Dependencies.gameOps.close();
    }

    @Test
    public void player_gets_card_dealt_to_hand() throws OutOfCardsException {
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);

        gameOps.dealCardToPlayer(mockedDeck, mockedBotPlayer);

        verify(mockedBotPlayer, times(1)).getHand();
        verify(mockedDeck, times(1)).dealCard();
//        verify(mockedHand, times(1)).addCard(any(Card.class));
    }

    @Test
    public void initialGameDeal_shuffles_and_then_deals_cards() throws OutOfCardsException {
        when(mockedDeck.dealCard()).thenReturn(eightDiamonds).thenReturn(threeSpades).thenReturn(aceClubs).thenReturn(tenHearts);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        InOrder inOrder = inOrder(mockedDeck);

        gameOps.initialGameDeal(mockedBotPlayer, mockedHumanPlayer);

        inOrder.verify(mockedDeck, times(1)).shuffleDeck();
        inOrder.verify(mockedDeck, times(4)).dealCard();
    }

    // TODO: single-responsibility. Shouldn't the top-level game "reset" to a valid overall world? You wrote a bunch of
    // code to do what "new Game" already would do
    @Test
    public void clears_hand_before_initial_deal_of_card_when_playing_another_round() throws OutOfCardsException {
        when(mockedHand.size()).thenReturn(3).thenReturn(2);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedDeck.dealCard()).thenReturn(eightDiamonds).thenReturn(threeSpades).thenReturn(aceClubs).thenReturn(tenHearts);
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);
        InOrder inOrder = inOrder(mockedHand);

        gameOps.initialGameDeal(mockedBotPlayer, mockedHumanPlayer);

        verify(mockedHand, times(2)).size();
        verify(mockedHand, times(2)).resetHand();
    }

    // TODO: "not" is a testing no-no. Say what it does, not what it doesn't.
    // handlePlayerAction_stays_when_human_requests_stay
    // handlePlayerAction_busts_when_human_busts
    @Test
    public void does_not_deal_anymore_cards_when_player_stays() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Stay);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        assertEquals(result, Action.Stay);
        verify(mockedHumanPlayer, times(1)).nextAction(any(Hand.class));
    }

    // really good. Says exactly what the behavior is
    @Test
    public void deals_cards_until_player_stays() throws OutOfCardsException {
        when(mockedHumanPlayer.nextAction(any(Hand.class))).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

        Action result = gameOps.handlePlayerAction(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        assertEquals(result, Action.Stay);
        verify(mockedHumanPlayer, times(3)).nextAction(any(Hand.class));
    }

    // TODO: not sure you need this. If so, the name would be "scoring_the_hand_uses_the_getScore_method". Code smell then: talking about HOW
    // Even if that was what you wanted to prove, you should not have involved magic numbers
    // TODO: The real behaviors for getScore are "passes the players cards to scoreHand" and "returns the result of scoreHand on those cards". Possibly worded as a single test
    @Test
    public void score_of_player_hand_is_calculated_correctly() { // TODO: "correctly" is meaningless
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        when(mockedScore.scoreHand(anySet())).thenReturn(10);

        Integer result = gameOps.getScore(mockedBotPlayer);

        // TODO: would have been a verify, not based on magic number
        assertTrue(result == 10);
    }

    @Test
    public void same_scores_evaluate_to_pushed_game() {
        boolean result = gameOps.gameIsPush(10, 10);

        assertTrue(result);
    }

    @Test
    public void both_players_having_busted_action_is_recognized() {
        boolean result = gameOps.bothPlayersBust(Action.Busted, Action.Busted);

        assertTrue(result);
    }

    @Test
    public void human_score_under_21_wins_game_when_bot_over_21() {
        String result = gameOps.determineWinner(22, 19);

        assertTrue(result.contains("Human wins"));
    }

    @Test
    public void bot_score_under_21_wins_game_when_human_over_21() {
        String result = gameOps.determineWinner(19, 22);

        assertTrue(result.contains("Bot wins"));
    }

    @Test
    public void human_score_under_21_beats_bot_score_under_21() {
        String result = gameOps.determineWinner(17, 19);

        assertTrue(result.contains("Human wins"));
    }

    @Test
    public void bot_score_under_21_beats_human_score_under_21() {
        String result = gameOps.determineWinner(19, 17);

        assertTrue(result.contains("Bot wins"));
    }

    @Test
    public void no_result_when_both_players_are_over_21() {
        String result = gameOps.determineWinner(22, 23);

        assertTrue(result.isEmpty());
    }

    @Test
    public void no_result_when_both_players_have_the_same_score() {
        String result = gameOps.determineWinner(21, 21);

        assertTrue(result.isEmpty());
    }

    @Test
    public void when_hand_has_cards_it_is_not_empty() {
        when(mockedHand.size()).thenReturn(3);

        boolean result = gameOps.handIsEmpty(mockedHand);

        assertFalse(result);
    }

    @Test
    public void when_hand_does_not_have_cards_it_is_empty() {
        when(mockedHand.size()).thenReturn(0);

        boolean result = gameOps.handIsEmpty(mockedHand);

        assertTrue(result);
    }

    @Test
    public void hand_is_cleared_when_reset() {
        when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
        gameOps.resetHand(mockedBotPlayer);

        verify(mockedBotPlayer, times(1)).getHand();
        verify(mockedHand, times(1)).resetHand();
    }

}
