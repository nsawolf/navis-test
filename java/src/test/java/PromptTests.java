import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.*;


public class PromptTests {

    private ConsoleIO console = null;
    private final String testQuestion = "Test Question";
    private final String regexResponsePattern = "(.*\\w+.*)";
    private final String defaultResponse = "Default Response";

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
        final String illegal = "not legal";
        final String variableInput = "another guy";
        final String testResponse = "test";
        final String testPattern = "test";
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn(illegal).thenReturn(variableInput).thenReturn(testResponse);

        String result = prompt.prompt(testQuestion, testPattern, defaultResponse);

        verify(console, times(2)).generateConsoleOutput(anyString());
        verify(console, times(3)).getConsoleInput();
    }

    @Test
    public void empty_response_returns_the_default_response() throws IOException {
        final String emptyResponse = "";
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn(emptyResponse);

        String result = prompt.prompt(testQuestion, regexResponsePattern, defaultResponse);

        assumeTrue(result.equals(defaultResponse));
    }

    @Test
    public void returns_valid_response_with_whitespace_trimmed() throws IOException {
        final String whiteSpacedValue = "  trim whitespaces off  ";
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn(whiteSpacedValue);

        String result = prompt.prompt(testQuestion, regexResponsePattern, defaultResponse);

        assumeTrue(result.equals(whiteSpacedValue.trim()));
    }

    @Test
    public void io_exception_returns_the_default() throws IOException {
        final String exceptionValue = "testing io exception";
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenThrow(new IOException(exceptionValue));

        String result = prompt.prompt(testQuestion, regexResponsePattern, defaultResponse);

        assumeTrue(result.equals(defaultResponse));
        verify(console, times(1)).getConsoleInput();
        verify(console, times(1)).generateConsoleOutput(anyString());
    }
}
