import enumerations.Action;

import java.io.IOException;

public class HumanPlayer implements PlayerI {

    private Hand humanHand = new Hand();

    @Override
    public Action nextAction(Hand otherHand) {
        ConsoleIOI console = Dependencies.console.make();
        ScoreI score = Dependencies.score.make();
        PromptI prompt = Dependencies.prompt.make();
        console.generateConsoleOutput("Robot has following card dealt: " + otherHand.visibleHand(true));
        console.generateConsoleOutput("Your dealt hand is: " + humanHand.visibleHand(false));
        if (score.scoreHand(humanHand.getCards()) > 21) {
            console.generateConsoleOutput("Sorry, you have Busted; game over");
            return Action.Busted;
        }
        String answer = prompt.prompt("Enter H for Hit, S for Stay", "[H, S]", "Invalid response.");
        while (answer.equals("Invalid response.")){
            answer = prompt.prompt("Enter H for Hit, S for Stay", "[H, S]", "Invalid response.");
        }
        if (answer.contains("H")) {
            return Action.Hit;
        }
        return Action.Stay;
    }

    @Override
    public Hand getHand() {
        return humanHand;
    }
}
