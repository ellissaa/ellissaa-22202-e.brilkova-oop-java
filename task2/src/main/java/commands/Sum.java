package commands;

import context.Context;
import exceptions.NEmptyStackException;
import java.util.EmptyStackException;

public class Sum implements Command {
    public void execute(Context context, String[] args) throws NEmptyStackException {
        try {
            context.push(context.pop() + context.pop());
        } catch (EmptyStackException e) {
            throw new NEmptyStackException("Stack is empty, no numbers.\n", e);
        }
    }
}
