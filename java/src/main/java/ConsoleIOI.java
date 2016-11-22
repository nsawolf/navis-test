import java.io.IOException;

public interface ConsoleIOI {
    String getConsoleInput() throws IOException;
    void generateConsoleOutput(String format, Object... args);
}
