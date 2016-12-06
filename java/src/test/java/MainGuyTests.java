import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class MainGuyTests {

    private BlackJackGame mockedGame = mock(BlackJackGame.class);
    private ConsoleIO mockedConsole = mock(ConsoleIO.class);
    private GameResultI mockedGameResult = mock(GameResult.class);
    private MainGuy m = new MainGuy();

    @Before
    public void setup(){
        Dependencies.game.override(() -> mockedGame);
        Dependencies.console.override(() -> mockedConsole);
        Dependencies.gameResult.override(() -> mockedGameResult);
    }

    @After
    public void teardown(){
        Dependencies.game.close();
        Dependencies.console.close();
        Dependencies.gameResult.close();
    }

    @Test
    public void game_is_invoked_once_for_play_with_console_interaction() throws OutOfCardsException, IOException{
        final String strGameResult = "Human wins";
        when(mockedGame.play()).thenReturn(mockedGameResult);
        when(mockedGameResult.toString()).thenReturn(strGameResult);
        when(mockedConsole.getConsoleInput()).thenReturn("n");

        m.main(null);

        verify(mockedGame, times(1)).play();
        verify(mockedConsole, times(3)).generateConsoleOutput(anyString());
    }

    @Test
    public void out_of_card_exception_stops_game() throws OutOfCardsException{
        final String errResultMsg = "testing throwing of exception";
        when(mockedGame.play()).thenThrow(new OutOfCardsException(errResultMsg));

        m.main(null);

        verify(mockedConsole, times(1)).generateConsoleOutput(anyString());
        verify(mockedGame, times(1)).play();
    }

    @Test
    public void game_able_to_be_play_n_times() throws OutOfCardsException, IOException{
        final String gameResultStr = "Bot Wins";
        when(mockedGame.play()).thenReturn(mockedGameResult);
        when(mockedGameResult.toString()).thenReturn(gameResultStr);
        when(mockedConsole.getConsoleInput()).thenReturn("y").thenReturn("n");

        m.main(null);

        verify(mockedConsole, times(5)).generateConsoleOutput(anyString());
        verify(mockedConsole, times (2)).getConsoleInput();
        verify(mockedGame, times(2)).play();
    }

    @Test
    public void io_exception_stops_game() throws OutOfCardsException, IOException{
        final String ioExceptionMessage = "test io exception";
        final String gameResultMsg = "Bot wins";
        when(mockedConsole.getConsoleInput()).thenThrow(new IOException(ioExceptionMessage));
        when(mockedGame.play()).thenReturn(mockedGameResult);
        when(mockedGameResult.toString()).thenReturn(gameResultMsg);

        m.main(null);

        verify(mockedConsole, times(3)).generateConsoleOutput(anyString());
        verify(mockedGame, times(1)).play();

    }
}
