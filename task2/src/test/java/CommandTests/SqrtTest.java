package CommandTests;

import commands.Sqrt;
import context.Context;
import exceptions.MathException;
import exceptions.NEmptyStackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SqrtTest {
    private final Sqrt sqrt = new Sqrt();
    private Context context = new Context();

    @Before
    public void newContext() {
        context = new Context();
    }

    @Test(expected = NEmptyStackException.class)
    public void testEmptyStack() throws NEmptyStackException, MathException {
        sqrt.execute(context, null);
    }

    @Test(expected = MathException.class)
    public void testSqrtOfNegative() throws NEmptyStackException, MathException {
        context.push(-100.);
        sqrt.execute(context, null);
    }

    @Test
    public void testCorrect1() throws NEmptyStackException, MathException {
        context.push(123.);
        sqrt.execute(context, null);
        Assert.assertEquals(11.0905365064, context.peek(), 1e-5D);
    }

    @Test
    public void testCorrect2() throws NEmptyStackException, MathException {
        context.push(100.);
        sqrt.execute(context, null);
        Assert.assertEquals(10., context.peek(), 1e-5D);
    }
}