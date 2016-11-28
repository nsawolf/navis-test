import java.io.IOException;

public class Prompt implements PromptI {

    @Override
    public String prompt(String question, String legalResponsePattern, String defaultReturnValue) {
        ConsoleIOI console = Dependencies.console.make();
        try {
            do {
                console.generateConsoleOutput(question, legalResponsePattern, defaultReturnValue);
                String input = console.getConsoleInput();

                if (input.isEmpty()) {
                    console.generateConsoleOutput(defaultReturnValue);
                    return defaultReturnValue;
                } else if (isLegalPattern(legalResponsePattern, input)) {
                    return input.trim();
                }
                console.generateConsoleOutput(question);
            } while (!legalResponsePattern.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
            console.generateConsoleOutput(defaultReturnValue);
            return defaultReturnValue;
        }
        return question;
    }

    private boolean isLegalPattern(String legalResponsePattern, String input) {
        return input.matches(legalResponsePattern) && !legalResponsePattern.isEmpty();
    }
}
