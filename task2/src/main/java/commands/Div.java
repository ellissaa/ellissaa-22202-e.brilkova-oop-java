package commands;

import context.Context;
import exceptions.MathException;
import exceptions.NEmptyStackException;
import java.util.EmptyStackException;

public class Div implements Command {
    public void execute(Context context, String[] args) throws NEmptyStackException, MathException {
        double a, b;
        try {
            b = context.pop();
            a = context.pop();
        } catch (EmptyStackException e) {
            throw new NEmptyStackException("Stack is empty, no numbers.\n", e);
        }
        if (b == 0) {
            throw new MathException("Division by zero.\n");
        }
        context.push(a / b);
    }
}
