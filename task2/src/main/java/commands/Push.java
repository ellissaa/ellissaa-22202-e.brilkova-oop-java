package commands;

import context.Context;
import exceptions.BadInputFormatException;

public class Push implements Command {
    public void execute(Context context, String[] args) throws BadInputFormatException {
        if (args.length != 1) {
            throw new BadInputFormatException("PUSH should consist of 1 argument.");
        }

        Double a;
        try {
            a = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            a = context.getValue(args[0]);
            if (a == null) {
                throw new BadInputFormatException("No value named " + args[0]);
            }
        }
        context.push(a);
    }
}
