package factory;

import commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class FactoryConfig { // читаем файл конфигурации, чтобы создавать объекты
    private final HashMap<String, Class<Command>> commandNames = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FactoryConfig.class);

    public void configure(String fileName)
            throws ConfigurationException {
        InputStream input = FactoryConfig.class.getResourceAsStream("/" + fileName);

        if (input == null) {
            logger.error("File with this name doesn't exist.");
            throw new ConfigurationException("File with this name doesn't exist.");
        }

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Bad format of configuration file.");
            throw new ConfigurationException("Bad format of configuration file.");
        }

        for (String key : properties.stringPropertyNames()) {
            addCommand(key, properties.getProperty(key));
        }
    }

    private void addCommand(String commandName, String className)
            throws ConfigurationException {
        try {
            commandNames.put(commandName, (Class<Command>) Class.forName(className));
        } catch (ClassNotFoundException | ClassCastException e) {
            logger.error("Can't create class {}", className);
            throw new ConfigurationException("Can't create class " + className);
        }
    }

    public HashMap<String, Class<Command>> getCommandNames() {
        return commandNames;
    }
}