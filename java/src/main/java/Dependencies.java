import navis.injection.*;

import java.util.Date;


public class Dependencies {
    public static final InstanceFactory<BlackJackGame> game = new InstanceFactory<BlackJackGame>(new BuildFunction<BlackJackGame>(){
        @Override
        public BlackJackGame build() {
            return new BlackJackGame();
        }
    });
    public static final InstanceFactory<ConsoleIOI> console = new InstanceFactory<ConsoleIOI>(BuildFunction.singletonBuilder(ConsoleIO.class));
    public static final InstanceFactory<Deck> deck = new InstanceFactory<Deck>(BuildFunction.singletonBuilder(Deck.class));
    public static final InstanceFactory<GameResult> gameResult = new InstanceFactory<GameResult>(BuildFunction.singletonBuilder(GameResult.class));
    public static final InstanceFactory<Hand> hand = new InstanceFactory<Hand>(new BuildFunction<Hand>() {
        @Override
        public Hand build() {
            return new Hand();
        }
    });
    public static final InstanceFactory<Long> now = new InstanceFactory<>(() -> new Date(System.currentTimeMillis()).getTime());
    public static final InstanceFactory<Operations> gameOps = new InstanceFactory<Operations>(BuildFunction.singletonBuilder(Operations.class));
    public static final InstanceFactory1Arg<PlayerI, Hand> botPlayer =
            new InstanceFactory1Arg<PlayerI, Hand>(new BuildFunction1Arg<PlayerI, Hand>() {
                @Override
                    public PlayerI build(Hand hand) {
                        if (hand != null)
                            return new BotPlayer(hand);
                        return new BotPlayer(Dependencies.hand.make());
                    }
                });
    public static final InstanceFactory1Arg<PlayerI, Hand> humanPlayer =
            new InstanceFactory1Arg<PlayerI, Hand>(new BuildFunction1Arg<PlayerI, Hand>() {
                @Override
                public PlayerI build(Hand hand) {
                    if (hand != null){
                        return new HumanPlayer(hand);
                    }
                    return new HumanPlayer(Dependencies.hand.make());
                }
            });
    public static final InstanceFactory<PromptI> prompt = new InstanceFactory<PromptI>(BuildFunction.singletonBuilder(Prompt.class));
}

