import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class MainGuyTests {

    private BlackJackGame mockedGame = mock(BlackJackGame.class);
    private ConsoleIO mockedConsole = mock(ConsoleIO.class);
    private MainGuy m = new MainGuy();

    @Before
    public void setup(){
        Dependencies.game.override(() -> mockedGame);
        Dependencies.console.override(() -> mockedConsole);
    }

    @After
    public void teardown(){
        Dependencies.game.close();
        Dependencies.console.close();
    }

    @Test
    public void game_is_invoked_once_for_play_with_console_interaction() throws OutOfCardsException, IOException{
        when(mockedGame.play()).thenReturn("Human wins game");
        when(mockedConsole.getConsoleInput()).thenReturn("n");

        m.main(null);

        verify(mockedGame, times(1)).play();
        verify(mockedConsole, times(3)).generateConsoleOutput(anyString());
    }

    @Test
    public void out_of_card_exception_stops_game() throws OutOfCardsException{
        when(mockedGame.play()).thenThrow(new OutOfCardsException("testing throwing of exception"));

        m.main(null);

        verify(mockedConsole, times(1)).generateConsoleOutput(anyString());
        verify(mockedGame, times(1)).play();
    }

    @Test
    public void game_able_to_be_play_n_times() throws OutOfCardsException, IOException{
        when(mockedGame.play()).thenReturn("Bot Wins");
        when(mockedConsole.getConsoleInput()).thenReturn("y").thenReturn("n");

        m.main(null);

        verify(mockedConsole, times(5)).generateConsoleOutput(anyString());
        verify(mockedConsole, times (2)).getConsoleInput();
        verify(mockedGame, times(2)).play();
    }

    @Test
    public void io_exception_stops_game() throws OutOfCardsException, IOException{
        when(mockedConsole.getConsoleInput()).thenThrow(new IOException("test io exception"));
        when(mockedGame.play()).thenReturn("Bot wins");

        m.main(null);

        verify(mockedConsole, times(3)).generateConsoleOutput(anyString());
        verify(mockedGame, times(1)).play();

    }
}
