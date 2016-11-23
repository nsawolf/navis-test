import enumerations.Action;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HumanPlayerTests {

    private HumanPlayer humanPlayer = new HumanPlayer();
    private Hand mockedHand = mock(Hand.class);
    private Score mockedScore = mock(Score.class);
    private Prompt mockedPrompt = mock(Prompt.class);
    private Integer over21 = 22;
    private Integer blackJack = 21;
    private Integer smallValue = 4;

    @Before
    public void setup(){
        Dependencies.hand.override(() -> mockedHand);
        Dependencies.score.override(() -> mockedScore);
        Dependencies.prompt.override(() -> mockedPrompt);
    }

    @After
    public void teardown(){
        Dependencies.hand.close();
        Dependencies.score.close();
        Dependencies.prompt.close();
    }

    @Test
    public void busts_if_score_is_over_21(){
        when(mockedScore.scoreHand(anySet())).thenReturn(over21);

        Action result = humanPlayer.nextAction(mockedHand);

        assertEquals(result, Action.Busted);
    }

    @Test
    public void stays_when_player_decides_to_not_have_more_cards_dealt() throws IOException{
        when(mockedScore.scoreHand(anySet())).thenReturn(blackJack);
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn(Action.Stay.getValue());
        Action result = humanPlayer.nextAction(mockedHand);

        assertEquals(result, Action.Stay);
    }

    @Test
    public void hits_when_player_decides_to_get_another_card() throws IOException{
        when(mockedScore.scoreHand(anySet())).thenReturn(smallValue);
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn(Action.Hit.getValue());

        Action result = humanPlayer.nextAction(mockedHand);

        assertTrue(result.equals(Action.Hit));
    }

    @Test
    public void asked_question_until_valid_response_entered() throws IOException{
        when(mockedScore.scoreHand(anySet())).thenReturn(smallValue);
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("Invalid response.").thenReturn(Action.Stay.getValue());

        Action result = humanPlayer.nextAction(mockedHand);

        assertEquals(result, Action.Stay);
        verify(mockedPrompt, times(2)).prompt(anyString(), anyString(), anyString());
    }
}
