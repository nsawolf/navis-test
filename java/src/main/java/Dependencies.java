import navis.injection.BuildFunction;
import navis.injection.InstanceFactory;

import java.util.Date;


public class Dependencies {
    public static final InstanceFactory<BlackJackGameI> game = new InstanceFactory<BlackJackGameI>(BuildFunction.singletonBuilder(BlackJackGame.class));
    public static final InstanceFactory<ConsoleIOI> console = new InstanceFactory<ConsoleIOI>(BuildFunction.singletonBuilder(ConsoleIO.class));
    public static final InstanceFactory<DeckI> deck = new InstanceFactory<DeckI>(BuildFunction.singletonBuilder(Deck.class));
    public static final InstanceFactory<GameResultI> gameResult = new InstanceFactory<GameResultI>(BuildFunction.singletonBuilder(GameResult.class));
    public static final InstanceFactory<HandI> hand = new InstanceFactory<HandI>(new BuildFunction<HandI>() {
        private HandI hand;

        @Override
        public HandI build() {
            if (hand == null) {
                hand = new Hand();
            }
            return hand;
        }
    });
    public static final InstanceFactory<Long> now = new InstanceFactory<>(() -> new Date(System.currentTimeMillis()).getTime());
    public static final InstanceFactory<OperationsI> gameOps = new InstanceFactory<OperationsI>(BuildFunction.singletonBuilder(Operations.class));
    public static final InstanceFactory<PlayerI> botPlayer = new InstanceFactory<PlayerI>(() -> new BotPlayer(Dependencies.hand.make()));
    public static final InstanceFactory<PlayerI> humanPlayer = new InstanceFactory<PlayerI>(() -> new HumanPlayer(Dependencies.hand.make()));
    public static final InstanceFactory<PromptI> prompt = new InstanceFactory<PromptI>(BuildFunction.singletonBuilder(Prompt.class));
}

