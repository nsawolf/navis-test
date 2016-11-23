package enumerations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionTests {
    @Test
    public void has_three_values(){
        assertEquals(Action.values().length, 3);
    }

    @Test
    public void provide_expected_values_for_meaning(){
        assertEquals(Action.Hit.getValue(), "h");
        assertEquals(Action.Stay.getValue(), "s");
        assertEquals(Action.Busted.getValue(), "b");
    }
}
