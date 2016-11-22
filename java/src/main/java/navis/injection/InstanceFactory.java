package navis.injection;

public class InstanceFactory<T> implements Resettable {
    private final BuildFunction<T> builder;
    private BuildFunction<T> overrideBuilder;

    public InstanceFactory(BuildFunction<T> builder) {
        this.builder = builder;
    }

    public T make() {
        if (overrideBuilder == null)
            return builder.build();
        return overrideBuilder.build();
    }

    public Resettable override(BuildFunction<T> builder) {
        this.overrideBuilder = builder;
        return this;
    }

    @Override
    public void close() {
        overrideBuilder = null;
    }
}

