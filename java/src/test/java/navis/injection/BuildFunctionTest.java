package navis.injection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class TestArtifact {
}

public class BuildFunctionTest {
    @Test
    public void can_be_used_to_create_a_function_that_returns_the_same_instance_every_time() {
        final BuildFunction f = BuildFunction.singletonBuilder(TestArtifact.class);

        assertEquals(f.build(), f.build());
    }
}
