package CommandTests;

import commands.Pop;
import context.Context;
import exceptions.NEmptyStackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PopTest {
    private final Pop pop = new Pop();
    private Context context;

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test(expected = NEmptyStackException.class)
    public void testEmptyStack() throws NEmptyStackException {
        pop.execute(context, null);
    }

    @Test
    public void testCorrect() throws NEmptyStackException {
        context.push(123.456);
        context.push(56.78);
        pop.execute(context, null);
        Assert.assertEquals(123.456, context.peek(), 1e-5D);
    }
}