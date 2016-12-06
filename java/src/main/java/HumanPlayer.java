import enumerations.Action;

public class HumanPlayer implements PlayerI {

    private HandI humanHand = new Hand();

    @Override
    public Action nextAction(HandI otherHand) {
        ConsoleIOI console = Dependencies.console.make();
        PromptI prompt = Dependencies.prompt.make();
        final String question = "Enter h for Hit, s for Stay \n";
        final String invalidResponse = "Invalid Response.";
        final String regexPattern = "[h, s]";

        console.generateConsoleOutput("Robot has following card dealt: %s \n", otherHand.visibleHand(true));
        console.generateConsoleOutput("Your dealt hand is: %s \n", humanHand.visibleHand(false));
        if (humanHand.scoreHand() > 21) {
            console.generateConsoleOutput("Sorry, you have Busted; Game over \n");
            return Action.Busted;
        }
        String answer = prompt.prompt(question, regexPattern, invalidResponse + "\n");
        while (answer.equals(invalidResponse)) {
            answer = prompt.prompt(question, regexPattern, invalidResponse+ "\n");
        }
        if (answer.contains(Action.Hit.getValue())) {
            return Action.Hit;
        }
        return Action.Stay;
    }

    @Override
    public HandI getHand() {
        return humanHand;
    }

    @Override
    public String showHand(){
        return humanHand.visibleHand(false);
    }
}
