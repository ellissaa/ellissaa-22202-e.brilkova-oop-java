package CommandTests;


import commands.Sub;
import context.Context;
import exceptions.NEmptyStackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubTest {
    private final Sub subTest = new Sub();
    private Context context;

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test(expected = NEmptyStackException.class)
    public void testEmptyStack() throws NEmptyStackException {
        subTest.execute(context, null);
    }

    @Test
    public void testValid() throws NEmptyStackException {
        context.push(12345.0987);
        context.push(9876.012345);
        subTest.execute(context, null);
        Assert.assertEquals(2469.086355, context.peek(), 1e-5D);
    }
}