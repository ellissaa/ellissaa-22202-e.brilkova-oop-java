package CommandTests;

import commands.Define;
import context.Context;
import exceptions.BadInputFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefineTest {
    private final Define defTest = new Define();
    private Context context;

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test
    public void testBadNumOfArgs1() {
        String[] args = {};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> defTest.execute(context, args));
        String expectedMessage = "DEFINE should consist of 2 arguments.";
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testBadNumOfArgs2() {
        String[] args = {"one"};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> defTest.execute(context, args));
        String expectedMessage = "DEFINE should consist of 2 arguments.";
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testBadNumOfArgs3() {
        String[] args = {"1", "2", "3"};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> defTest.execute(context, args));
        String expectedMessage = "DEFINE should consist of 2 arguments.";
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testBadName() {
        String[] args = {"bad123name", "0"};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> defTest.execute(context, args));
        String expectedMessage = "Value name is not correct, expected letters.";
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testBadValue() {
        String[] args = {"a", "badvalue!!"};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> defTest.execute(context, args));
        String expectedMessage = "Double value expected.";
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testCorrect() {
        String[] args = {"n", "123.456"};
        try {
            defTest.execute(context, args);
        } catch (BadInputFormatException e) {
            Assert.fail(e.getMessage());
        }

        double value = context.getValue("n");
        Assert.assertEquals(123.456, value, 1e-5);
    }
}