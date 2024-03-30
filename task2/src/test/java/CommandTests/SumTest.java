package CommandTests;

import commands.Sum;
import context.Context;
import exceptions.NEmptyStackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SumTest {
    private final Sum sumTest = new Sum();
    private Context context;

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test(expected = NEmptyStackException.class)
    public void testEmptyStack() throws NEmptyStackException {
        sumTest.execute(context, null);
    }

    @Test
    public void testPositive() throws NEmptyStackException {
        context.push(12.34);
        context.push(5.67);
        sumTest.execute(context, null);
        Assert.assertEquals(18.01, context.peek(), 1e-5D);
    }

    @Test
    public void testNegative() throws NEmptyStackException {
        context.push(-9.87);
        context.push(-6.54);
        sumTest.execute(context, null);
        Assert.assertEquals(-16.41, context.peek(), 1e-5D);
    }

    @Test
    public void testPosAndNeg() throws NEmptyStackException {
        context.push(-9.87);
        context.push(6.54);
        sumTest.execute(context, null);
        Assert.assertEquals(-3.33, context.peek(), 1e-5D);
    }
}