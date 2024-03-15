import commands.Command;
import context.Context;
import exceptions.CommandException;
import exceptions.CreateCommandException;
import exceptions.NoSuchCommandException;
import factory.FactoryCommands;

import javax.naming.ConfigurationException;

public class Calculator {
    private Context context;
    private FactoryCommands factory;

    public Calculator() throws ConfigurationException {
        context = new Context();
        factory = new FactoryCommands();
    }

    public void execute(String commandName, String[] args)
            throws CreateCommandException, NoSuchCommandException, CommandException {
        Command command = factory.produceCommand(commandName);
        command.execute(context, args);
    }
}