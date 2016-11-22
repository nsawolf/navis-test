package navis.injection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstanceFactoryTest {
    @Test
    public void requires_a_builder_function_which_produces_the_value() {
        InstanceFactory<Integer> factory = new InstanceFactory<>(() -> 42);

        int actual = factory.make();

        assertEquals(42, actual);
    }

    @Test
    public void allows_user_to_override_lambda_in_a_try_with_resources() {
        InstanceFactory<Integer> factory = new InstanceFactory<>(() -> 42);
        try (Resettable r = factory.override(() -> 12)) {
            assertEquals(12, factory.make().longValue());
        }
    }

    @Test
    public void resumes_original_value_after_override_in_try_with_resources() {
        InstanceFactory<Integer> factory = new InstanceFactory<>(() -> 42);
        try (Resettable r = factory.override(() -> 12)) {
        }
        assertEquals(42, factory.make().longValue());
    }
}
