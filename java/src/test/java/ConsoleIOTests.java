import org.junit.Test;

import java.io.IOException;

import static org.junit.Assume.assumeTrue;

public class ConsoleIOTests {

    private static ConsoleIOI c = Dependencies.console.make();
    public static void main(String[] args) throws IOException {
        generateOutput("Enter in some yes here: ");
        String input = getInput();
        generateOutput("Thanks for playing " + input);
    }


    private static void generateOutput(String format, Object... args){
        c.generateConsoleOutput(format, args);
    }

    private static String getInput() throws IOException {
        return c.getConsoleInput();
    }

    @Test
    public void run_manual_test_to_validate_input_and_output(){
        assumeTrue(false);
    }
}
