import enumerations.Action;

import java.io.IOException;

public class HumanPlayer implements PlayerI {

    private Hand humanHand = new Hand();

    @Override
    public Action nextAction(Hand otherHand) {
        ConsoleIOI console = Dependencies.console.make();
        ScoreI score = Dependencies.score.make();
        PromptI prompt = Dependencies.prompt.make();
        console.generateConsoleOutput("Robot has following card dealt: %s \n", otherHand.visibleHand(true));
        console.generateConsoleOutput("Your dealt hand is: %s \n", humanHand.visibleHand(false));
        if (score.scoreHand(humanHand.getCards()) > 21) {
            console.generateConsoleOutput("Sorry, you have Busted; Game over \n");
            return Action.Busted;
        }
        String answer = prompt.prompt("Enter h for Hit, s for Stay \n", "[h, s]", "Invalid response. \n");
        while (answer.equals("Invalid response.")) {
            answer = prompt.prompt("Enter h for Hit, s for Stay \n", "[h, s]", "Invalid response. \n");
        }
        if (answer.contains(Action.Hit.getValue())) {
            return Action.Hit;
        }
        return Action.Stay;
    }

    @Override
    public Hand getHand() {
        return humanHand;
    }

}
