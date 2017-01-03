import navis.injection.*;

import java.util.Date;


public class Dependencies {
    public static final InstanceFactory<BlackJackGameI> game = new InstanceFactory<BlackJackGameI>(new BuildFunction<BlackJackGameI>(){
        @Override
        public BlackJackGameI build() {
            return new BlackJackGame();
        }
    });
    public static final InstanceFactory<ConsoleIOI> console = new InstanceFactory<ConsoleIOI>(BuildFunction.singletonBuilder(ConsoleIO.class));
    public static final InstanceFactory<DeckI> deck = new InstanceFactory<DeckI>(BuildFunction.singletonBuilder(Deck.class));
    public static final InstanceFactory<GameResultI> gameResult = new InstanceFactory<GameResultI>(BuildFunction.singletonBuilder(GameResult.class));
    public static final InstanceFactory<HandI> hand = new InstanceFactory<HandI>(new BuildFunction<HandI>() {
        @Override
        public HandI build() {
            return new Hand();
        }
    });
    public static final InstanceFactory<Long> now = new InstanceFactory<>(() -> new Date(System.currentTimeMillis()).getTime());
    public static final InstanceFactory<OperationsI> gameOps = new InstanceFactory<OperationsI>(BuildFunction.singletonBuilder(Operations.class));
    public static final InstanceFactory1Arg<PlayerI, HandI> botPlayer =
            new InstanceFactory1Arg<PlayerI, HandI>(new BuildFunction1Arg<PlayerI, HandI>() {
                @Override
                    public PlayerI build(HandI hand) {
                        if (hand != null)
                            return new BotPlayer(hand);
                        return new BotPlayer(Dependencies.hand.make());
                    }
                });
    public static final InstanceFactory1Arg<PlayerI, HandI> humanPlayer =
            new InstanceFactory1Arg<PlayerI, HandI>(new BuildFunction1Arg<PlayerI, HandI>() {
                @Override
                public PlayerI build(HandI hand) {
                    if (hand != null){
                        return new HumanPlayer(hand);
                    }
                    return new HumanPlayer(Dependencies.hand.make());
                }
            });
    public static final InstanceFactory<PromptI> prompt = new InstanceFactory<PromptI>(BuildFunction.singletonBuilder(Prompt.class));
}

