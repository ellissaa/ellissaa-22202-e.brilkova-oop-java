package CommandTests;

import commands.Div;
import context.Context;
import exceptions.MathException;
import exceptions.NEmptyStackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DivTest {
    private final Div divTest = new Div();
    private Context context;

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test(expected = NEmptyStackException.class)
    public void testEmptyStack() throws NEmptyStackException, MathException {
        divTest.execute(context, null);
    }

    @Test(expected = MathException.class)
    public void testDivByZero() throws NEmptyStackException, MathException {
        context.push(123.);
        context.push(0.);
        divTest.execute(context, null);
    }

    @Test
    public void testCorrect() throws MathException, NEmptyStackException {
        context.push(1234.987);
        context.push(345.67);
        divTest.execute(context, null);
        Assert.assertEquals(3.57273411057, context.peek(), 1e-5D);
    }
}