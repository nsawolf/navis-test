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
    public void re_asks_question_when_fails_to_match_pattern() throws IOException {
        final String illegalResponse = "not legal";
        final String legalResponse = "test";
        final String pattern = "test";
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn(illegalResponse).thenReturn(illegalResponse).thenReturn(legalResponse);

        String result = prompt.prompt(testQuestion, pattern, defaultResponse);

        verify(console, times(2)).generateConsoleOutput(anyString());
        verify(console, times(3)).getConsoleInput();
    }

    @Test
    public void when_console_throws_exception_returns_default_response() throws IOException {
        final String emptyResponse = "";
        PromptI prompt = Dependencies.prompt.make();
        when(console.getConsoleInput()).thenReturn(emptyResponse);

        String result = prompt.prompt(testQuestion, regexResponsePattern, defaultResponse);

        assumeTrue(result.equals(defaultResponse));
    }

    @Test
    public void prompt_trims_whitespace_from_response() throws IOException {
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
