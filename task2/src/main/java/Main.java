import exceptions.BadInputFormatException;
import exceptions.CommandException;
import exceptions.CreateCommandException;
import exceptions.NoSuchCommandException;

import javax.naming.ConfigurationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = createScanner(args);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found.");
            return;
        }
        scanner.useDelimiter("\n");

        Calculator calculator;
        try {
            calculator = new Calculator();
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
            return;
        }
        run(scanner, calculator);
    }

    private static void run(Scanner scanner, Calculator calculator) {
        SplitCommands splitCommands = new SplitCommands();

        while (scanner.hasNext()) {
            String str = scanner.next();
            if (str.strip().equalsIgnoreCase("EXIT")) {
                return;
            }
            if (str.strip().startsWith("#")) {
                continue;
            }

            splitCommands.splitCommand(str);
            try {
                calculator.execute(splitCommands.getCommandName(), splitCommands.getCommandArgs());
            } catch (CreateCommandException | NoSuchCommandException | BadInputFormatException e) {
                System.out.println(e.getMessage());
            } catch (CommandException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private static Scanner createScanner(String[] args) throws FileNotFoundException {
        Scanner scanner;
        if (args.length == 0) {
            return new Scanner(System.in);
        }
        return new Scanner(new FileReader(args[0]));
    }
}
