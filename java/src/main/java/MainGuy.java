public class MainGuy {

    // FIXME break out logic into operations helper and inject into blackjack game so that this is just the play that gets called here and all
    //     others fall away
    public static void main(String[] args){
        BlackJackGameI game = Dependencies.game.make();
        ConsoleIOI console = Dependencies.console.make();
        console.generateConsoleOutput("Let us begin the game.  Cards have been shuffled and dealt.");
        try{
            String result = game.play();
            console.generateConsoleOutput(result);

        } catch (OutOfCardsException e){
            e.printStackTrace();
        }
    }
}
