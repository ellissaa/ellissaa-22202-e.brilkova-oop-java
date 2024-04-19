package console;

import game_model.Model;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model gameModel = new Model();
        SwingUtilities.invokeLater(() -> {
            Console console = new Console(gameModel);
        });
    }
}
