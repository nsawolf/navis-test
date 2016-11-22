package navis.injection;

public class InstanceFactory1Arg<RT, A> implements Resettable {

    private final BuildFunction1Arg<RT, A> builder;
    private BuildFunction1Arg<RT, A> overrideBuilder;

    public InstanceFactory1Arg(BuildFunction1Arg<RT, A> builder) {
        this.builder = builder;
    }

    public RT make(A arg) {
        if (overrideBuilder == null)
            return builder.build(arg);
        return overrideBuilder.build(arg);
    }

    public Resettable override(BuildFunction1Arg<RT, A> builder) {
        this.overrideBuilder = builder;
        return this;
    }

    @Override
    public void close() {
        overrideBuilder = null;
    }
}
