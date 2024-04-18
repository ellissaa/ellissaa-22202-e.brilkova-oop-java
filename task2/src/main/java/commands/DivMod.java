package commands;

import context.Context;
import exceptions.CommandException;
import exceptions.MathException;
import exceptions.NEmptyStackException;

import java.util.EmptyStackException;

public class DivMod implements Command {
    @Override
    public void execute(Context context, String[] args) throws CommandException {
        double a, b;
        try {
            a = context.pop();
            b = context.pop();
        } catch (EmptyStackException e) {
            throw new NEmptyStackException("Stack is empty.");
        }
        if (b == 0) {
            throw new MathException("Division by zero.");
        }
        context.push(a % b);
    }
}