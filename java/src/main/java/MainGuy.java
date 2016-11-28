import java.io.IOException;

public class MainGuy {

    public static void main(String[] args) {
        String playAgain = "y";
        BlackJackGameI game = Dependencies.game.make();
        ConsoleIOI console = Dependencies.console.make();
        console.generateConsoleOutput("Let us begin the game.\n  Cards have been shuffled and dealt.\n");
        try {
            do {
                String result = game.play();
                console.generateConsoleOutput(result);
                console.generateConsoleOutput("Would you like to play again? \n Enter y to play again anything else ends the game. \n");
            } while (console.getConsoleInput().equals(playAgain));
        } catch (IOException e){
            e.printStackTrace();
        } catch (OutOfCardsException e) {
            e.printStackTrace();
        }
    }
}