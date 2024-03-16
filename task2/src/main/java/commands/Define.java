package commands;

import context.Context;
import exceptions.BadInputFormatException;

public class Define implements Command {
    public boolean checkInputString(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isAlphabetic(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void execute(Context context, String[] args) throws BadInputFormatException {
        if (args.length != 2) {
            throw new BadInputFormatException("DEFINE should consist of 2 arguments.");
        }

        if (!checkInputString(args[0])) {
            throw new BadInputFormatException("Value name is not correct, expected letters.");
        }

        try {
            context.insertValue(args[0], Double.parseDouble(args[1]));
        } catch (NumberFormatException e) {
            throw new BadInputFormatException("Double value expected.");
        }
    }
}
