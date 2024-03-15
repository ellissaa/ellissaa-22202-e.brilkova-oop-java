package commands;

import context.Context;
import exceptions.NEmptyStackException;
import java.util.EmptyStackException;

public class Sub implements Command {
    public void execute(Context context, String[] args) throws NEmptyStackException {
        double a, b;
        try {
            b = context.pop();
            a = context.pop();
        } catch (EmptyStackException e) {
            throw new NEmptyStackException("Stack is empty, no numbers.\n", e);
        }
        context.push(a - b);
    }
}
