package factory;

import commands.Command;

import javax.naming.ConfigurationException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class FactoryConfig {
    public void configure(String fileName, HashMap<String, Class<Command>> commandNames)
            throws ConfigurationException {
        InputStream input = FactoryConfig.class.getResourceAsStream("/" + fileName);

        if (input == null) {
            throw new ConfigurationException("File with this name doesn't exist.");
        }

        Scanner configScanner = new Scanner(input);
        while (configScanner.hasNext()) {
            String[] configStr = configScanner.next().split("\\s*=\\s*"); // str   =   val
            addCommand(configStr, commandNames);
        }
    }

    private void addCommand(String[] configStr, HashMap<String, Class<Command>> commandNames)
            throws ConfigurationException {
        if (configStr.length != 2) {
            throw new ConfigurationException("Bad file string, incorrect format.");
        }

        try {
            // forName - вернули класс с названием configStr[1]
            commandNames.put(configStr[0], (Class<Command>) Class.forName(configStr[1]));
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new ConfigurationException("Can't create class " + configStr[1]);
        }
    }
}