package factory;

import commands.Command;
import exceptions.CreateCommandException;
import exceptions.NoSuchCommandException;

import javax.naming.ConfigurationException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class FactoryCommands {
    private HashMap<String, Class<Command>> commandNames = new HashMap<>();

    public FactoryCommands() throws ConfigurationException {
        FactoryConfig config = new FactoryConfig();
        config.configure("FactoryConfig.txt", commandNames);
    }

    public Command produceCommand(String commandName) throws NoSuchCommandException, CreateCommandException {
        if (commandName == null || !commandNames.containsKey(commandName)) {
            throw new NoSuchCommandException("Bad name of the command.");
        }

        Command newCommand;
        try {
            newCommand = commandNames.get(commandName).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                 | IllegalArgumentException | InvocationTargetException e) {
            throw new CreateCommandException("Can't create new command. " + e.getMessage());
        }
        return newCommand;
    }
}