import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.*;


public class PromptTests {

    private ConsoleIO console = null;

    @Before
    public void setup() {
        console = mock(ConsoleIO.class);
        Dependencies.console.override(() -> console);
    }

    @After
    public void teardown() {
        Dependencies.console.close();
    }

    @Test
    public void asks_question_until_legal_reponse_is_received() throws IOException {
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn("not legal").thenReturn("another guy").thenReturn("test");

        String result = prompt.prompt("TestQuestion", "test", "Please Try Again");

        verify(console, times(2)).generateConsoleOutput(anyString());
        verify(console, times(3)).getConsoleInput();
        assumeTrue(result.equals("test"));
    }

    @Test
    public void empty_response_returns_the_default_response() throws IOException {
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn("");

        String result = prompt.prompt("A Question", "something", "Default Response");

        assumeTrue(result.equals("Default Response"));
        verify(console, times(1)).getConsoleInput();
        verify(console, times(1)).generateConsoleOutput(anyString());
    }

    @Test
    public void whitespace_is_trimmed_from_input() throws IOException {
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn("  trim whitespaces off  ");

        String result = prompt.prompt("A test question", "(.*)(\\w+)(.*)", "Default Response");

        assumeTrue(result.equals("trim whitespaces off"));
//        verify(console, times(1)).getConsoleInput();
    }

    @Test
    public void io_exception_returns_the_default() throws IOException {
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenThrow(new IOException("testing io exception"));

        String result = prompt.prompt("A question", "some response", "Default Response");

        assumeTrue(result.equals("Default Response"));
        verify(console, times(1)).getConsoleInput();
        verify(console, times(1)).generateConsoleOutput(anyString());
    }
}
