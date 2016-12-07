import enumerations.Action;
import enumerations.Rank;
import enumerations.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HumanPlayerTests {

    private HandI mockedHand = spy(Hand.class);
    private Prompt mockedPrompt = mock(Prompt.class);
    private Integer over21 = 22;
    private Integer blackJack = 21;
    private Integer smallValue = 4;
    private List<Card> cards = new ArrayList<Card>();


    @Before
    public void setup(){
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.prompt.override(() -> mockedPrompt);
    }

    @After
    public void teardown(){
        Dependencies.hand.close();
        Dependencies.prompt.close();
    }

    @Test
    public void busts_if_score_is_over_21(){
        final String handString = "eight, five";
        when(mockedHand.visibleHand(anyBoolean())).thenReturn(handString).thenReturn(handString);
        when(mockedHand.scoreHand()).thenReturn(over21);
        HumanPlayer humanPlayer = new HumanPlayer(mockedHand);

        Action result = humanPlayer.nextAction(mockedHand);

        assertEquals(result, Action.Busted);
    }

    @Test
    public void stays_when_player_decides_to_not_have_more_cards_dealt() throws IOException{
        when(mockedHand.scoreHand()).thenReturn(blackJack);
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn(Action.Stay.getValue());
        HumanPlayer humanPlayer = new HumanPlayer(mockedHand);

        Action result = humanPlayer.nextAction(mockedHand);

        assertEquals(result, Action.Stay);
        verify(mockedPrompt, times(1)).prompt(anyString(), anyString(), anyString());
    }

    @Test
    public void hits_when_player_decides_to_get_another_card() throws IOException{
        when(mockedHand.scoreHand()).thenReturn(smallValue);
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn(Action.Hit.getValue());
        HumanPlayer humanPlayer = new HumanPlayer(mockedHand);

        Action result = humanPlayer.nextAction(mockedHand);

        assertTrue(result.equals(Action.Hit));
        verify(mockedPrompt, times(1)).prompt(anyString(), anyString(), anyString());
    }

    @Test
    public void asked_question_until_valid_response_entered() throws IOException{
        final String invalidResponse = "Invalid Response.";
        when(mockedHand.scoreHand()).thenReturn(smallValue);
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn(invalidResponse).thenReturn(Action.Stay.getValue());
         HumanPlayer humanPlayer = new HumanPlayer(mockedHand);

        Action result = humanPlayer.nextAction(mockedHand);

        assertEquals(result, Action.Stay);
        verify(mockedPrompt, times(2)).prompt(anyString(), anyString(), anyString());
    }
}
