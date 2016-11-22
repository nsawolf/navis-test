import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIO implements ConsoleIOI {

    protected static final InputStreamReader lineNumber = new InputStreamReader(System.in);

    public ConsoleIO(){

    }

    @Override
    public String getConsoleInput() throws IOException {
        BufferedReader br = new BufferedReader(lineNumber);
        return br.readLine();
    }

    @Override
    public void generateConsoleOutput(String format, Object... args) {
        System.out.printf(format, args);
    }
}
