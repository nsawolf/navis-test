//import enumerations.Action;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.*;
//
//public class BlackjackGameTests {
//
//    private OperationsI mockedOps = mock(Operations.class);
//    private Deck mockedDeck = mock(Deck.class);
//    private PlayerI mockedBot = mock(BotPlayer.class);
//    private PlayerI mockedHuman = mock(HumanPlayer.class);
//    private GameResult mockedGameResult = mock(GameResult.class);
//    private BlackJackGame game = new BlackJackGame();
//    private Integer scoreUnder21 = 20;
//    private Integer scoreOver21 = 23;
//    private Integer scoreAtStay = 17;
//    private Integer scoreBlackJack = 21;
//
//    @Before
//    public void setup() {
//        Dependencies.gameOps.override(() -> mockedOps);
//        Dependencies.deck.override(() -> mockedDeck);
//        Dependencies.botPlayer.override(() -> mockedBot);
//        Dependencies.humanPlayer.override(() -> mockedHuman);
//        Dependencies.gameResult.override(() -> mockedGameResult);
//        Dependencies.game.make();
//    }
//
//    @After
//    public void teardown() {
//        Dependencies.gameOps.close();
//        Dependencies.deck.close();
//        Dependencies.botPlayer.close();
//        Dependencies.humanPlayer.close();
//        Dependencies.gameResult.close();
//        Dependencies.game.close();
//    }
//
//    @Test
//    public void human_player_wins_when_bot_busts() throws OutOfCardsException {
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreOver21).thenReturn(scoreAtStay);
//        when(mockedGameResult.determineWinner(anyInt(), anyInt())).thenReturn("Human player wins the game with " + scoreUnder21 + " Bot loses with " + scoreOver21);
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreUnder21.toString()) && result.contains(scoreOver21.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(PlayerI.class));
//    }
//
//    @Test
//    public void bot_player_wins_when_human_busts() throws OutOfCardsException {
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreAtStay).thenReturn(scoreOver21);
//        when(mockedGameResult.determineWinner(anyInt(), anyInt())).thenReturn("Bot player wins the game with " + scoreAtStay + " Human loses with " + scoreOver21);
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreAtStay.toString()) && result.contains(scoreOver21.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    @Test
//    public void goes_until_player_with_highest_score_under_21_wins() throws OutOfCardsException {
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreBlackJack).thenReturn(scoreAtStay);
//        when(mockedGameResult.determineWinner(anyInt(), anyInt())).thenReturn("Human wins the game with " + scoreUnder21 + " bot loses with " + scoreAtStay + "\n");
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreAtStay.toString()) && result.contains(scoreUnder21.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    @Test
//    public void draw_when_both_players_have_same_score_under_21() throws OutOfCardsException {
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreAtStay).thenReturn(scoreAtStay);
//        when(mockedOps.gameIsPush(anyInt(), anyInt())).thenReturn(true);
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreAtStay.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    @Test
//    public void human_player_wins_with_a_natural_21() throws OutOfCardsException {
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreBlackJack).thenReturn(scoreAtStay);
//        when(mockedGameResult.determineWinner(anyInt(), anyInt())).thenReturn("Human wins the game with " + scoreBlackJack + " bot loses with " + scoreAtStay);
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreBlackJack.toString()) && result.contains(scoreAtStay.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    @Test
//    public void game_is_draw_when_both_players_have_21() throws OutOfCardsException {
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreBlackJack).thenReturn(scoreBlackJack);
//        when(mockedOps.gameIsPush(anyInt(), anyInt())).thenReturn(true);
//
//        String result = game.play();
//
//        assertEquals(result, "Game is a push at " + scoreBlackJack + "\n");
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    // TODO: more serious. What are you testing? (name vs body). This test proves nothing, and I can break code without test failure.
//    @Test
//    public void bot_player_wins_with_a_natural_21() throws OutOfCardsException {
//
//        /* What I expected to see:
//
//        setup: passed a bot and human score to a function, and asserted what it returned.
//         */
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreBlackJack).thenReturn(scoreAtStay); // TODO: (smell) high drag. I have to read play to understand why I need this programming
//        when(mockedGameResult.determineWinner(anyInt(), anyInt())).thenReturn("Bot wins the game with " + scoreBlackJack + " human loses with " + scoreAtStay + "\n");
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreBlackJack.toString()) && result.contains(scoreAtStay.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    @Test
//    public void no_one_wins_when_both_players_bust() throws OutOfCardsException {
//        Integer anotherOver21 = scoreOver21 + 1;
//
//        mocked_deal_cards_get_actions();
//        when(mockedOps.getScore(any(HandI.class))).thenReturn(scoreOver21).thenReturn(anotherOver21);
//        when(mockedOps.bothPlayersBust(any(Action.class), any(Action.class))).thenReturn(true);
//
//        String result = game.play();
//
//        assertTrue(result.contains(scoreOver21.toString()) && result.contains(anotherOver21.toString()));
//        verify(mockedOps, times(1)).showBotHand(any(HandI.class));
//    }
//
//    private void mocked_deal_cards_get_actions() throws OutOfCardsException {
//        when(mockedOps.initialGameDeal(any(BotPlayer.class), any(HumanPlayer.class))).thenReturn(mockedDeck);
//        when(mockedOps.handlePlayerAction(mockedBot, mockedHand, mockedDeck)).thenReturn(Action.Stay);
//        when(mockedOps.handlePlayerAction(mockedHuman, mockedBot, mockedDeck)).thenReturn(Action.Stay);
//    }
//
//}
