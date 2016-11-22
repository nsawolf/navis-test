import com.oracle.webservices.internal.api.databinding.Databinding;
import navis.injection.BuildFunction;
import navis.injection.InstanceFactory;

import javax.swing.text.html.HTMLDocument;
import java.util.Date;


public class Dependencies {
    public static final InstanceFactory<BlackJackGameI> game =
            new InstanceFactory<BlackJackGameI>(BuildFunction.singletonBuilder(BlackJackGame.class));
    public static final InstanceFactory<ConsoleIOI> console =
            new InstanceFactory<ConsoleIOI>(BuildFunction.singletonBuilder(ConsoleIO.class));
    public static final InstanceFactory<DeckI> deck =
            new InstanceFactory<DeckI>(BuildFunction.singletonBuilder(Deck.class));
    public static final InstanceFactory<HandI> hand =
            new InstanceFactory<HandI>(BuildFunction.singletonBuilder(Hand.class));
    public static final InstanceFactory<Date> now =
            new InstanceFactory<>(() -> new Date(System.currentTimeMillis()));
    public static final InstanceFactory<PlayerI> botPlayer =
            new InstanceFactory<PlayerI>(BuildFunction.singletonBuilder(BotPlayer.class));
    public static final InstanceFactory<PlayerI> humanPlayer =
            new InstanceFactory<PlayerI>(BuildFunction.singletonBuilder(HumanPlayer.class));
    public static final InstanceFactory<PromptI> prompt =
            new InstanceFactory<PromptI>(BuildFunction.singletonBuilder(Prompt.class));
    public static final InstanceFactory<ScoreI> score =
            new InstanceFactory<ScoreI>(BuildFunction.singletonBuilder(Score.class));

}
