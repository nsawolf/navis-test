import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void game_is_invoked_once_for_play_with_console_interaction() throws OutOfCardsException{
        when(mockedGame.play()).thenReturn("Human wins game");

        m.main(null);

        verify(mockedGame, times(1)).play();
        verify(mockedConsole, times(2)).generateConsoleOutput(anyString());
    }
}
