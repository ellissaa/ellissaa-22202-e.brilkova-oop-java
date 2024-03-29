package CommandTests;

import commands.Push;
import context.Context;
import exceptions.BadInputFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PushTest {
    private final Push push = new Push();
    private Context context;

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test
    public void testBadNumOfArgs() {
        String[] args = {};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> push.execute(context, args));
        String expectedMessage = "PUSH should consist of 1 argument.";
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testUnknownVariable() {
        String[] args = {"a"};
        Exception e = Assert.assertThrows(BadInputFormatException.class,
                () -> push.execute(context, args));
        String expectedMessage = "No value named " + args[0];
        Assert.assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testPushNumber() throws BadInputFormatException {
        String[] args = {"123.456"};
        push.execute(context, args);
        Assert.assertEquals(123.456, context.peek(), 1e-5D);
    }

    @Test
    public void testPushVariable() throws BadInputFormatException {
        context.insertValue("a", 123.456);
        String[] args = {"a"};
        push.execute(context, args);
        Assert.assertEquals(123.456, context.peek(), 1e-5D);
    }
}