package factory;

import commands.Command;
import exceptions.CreateCommandException;
import exceptions.NoSuchCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ConfigurationException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class FactoryCommands { // создаем новую команду
    private final HashMap<String, Class<Command>> commandNames;
    private static final Logger logger = LoggerFactory.getLogger(FactoryCommands.class);

    public FactoryCommands() throws ConfigurationException {
        FactoryConfig config = new FactoryConfig();
        config.configure("FactoryConfig.properties");
        commandNames = config.getCommandNames();
    }

    public Command produceCommand(String commandName) throws NoSuchCommandException, CreateCommandException {
        if (commandName == null || !commandNames.containsKey(commandName)) {
            logger.error("Bad name of the command.");
            throw new NoSuchCommandException("Bad name of the command.");
        }

        Command newCommand;
        try {
            newCommand = commandNames.get(commandName).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                 | IllegalArgumentException | InvocationTargetException e) {
            logger.error("Can't create new command. {}", e.getMessage());
            throw new CreateCommandException("Can't create new command. " + e.getMessage());
        }
        return newCommand;
    }
}