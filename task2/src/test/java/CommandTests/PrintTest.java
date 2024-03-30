package CommandTests;

import commands.Print;
import context.Context;
import exceptions.NEmptyStackException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintTest {
    private final Print print = new Print();
    private Context context;

    private final ByteArrayOutputStream outCaptor = new ByteArrayOutputStream();
    private final PrintStream sysOut = System.out;

    @Before
    public void setUp() {
        context = new Context();
        outCaptor.reset();
        System.setOut(new PrintStream(outCaptor));
    }

    @After
    public void restore() {
        System.setOut(sysOut);
    }

    @Test(expected = NEmptyStackException.class)
    public void testEmptyStack() throws NEmptyStackException {
        print.execute(context, null);
    }

    @Test
    public void testCorrect() throws NEmptyStackException {
        context.push(123.456);
        print.execute(context, null);
        Assert.assertEquals("123.456", outCaptor.toString().trim());
    }
}
