package commands;

import context.Context;
import exceptions.MathException;
import exceptions.NEmptyStackException;
import java.util.EmptyStackException;

public class Sqrt implements Command {
    public void execute(Context context, String[] args) throws NEmptyStackException, MathException {
        double a;
        try {
            a = context.pop();
        } catch (EmptyStackException e) {
            throw new NEmptyStackException("Stack is empty, no numbers.\n", e);
        }
        if (a < 0) {
            throw new MathException("Number is negative.\n");
        }
        context.push(Math.sqrt(a));
    }
}
