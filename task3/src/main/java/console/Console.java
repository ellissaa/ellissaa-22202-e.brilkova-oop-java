package console;

import game_model.Model;
import game_model.ModelUpload;

import javax.swing.*;
import java.awt.*;

public class Console extends JFrame implements ModelUpload {
    private final Model gameModel;
    private final JLabel playerLabel;

    public Console(Model gameModel) {
        super("Space Invaders");
        this.gameModel = gameModel;
        gameModel.setUpload(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        Insets border = getInsets();
        setSize(Model.fieldWidth + border.left + border.right,
                Model.fieldHeight + border.top + border.bottom);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(12, 12, 19));

        playerLabel = createPlayer();
    }

    private JLabel createPlayer() {

    }

    @Override
    public void modelChange() {

    }
}
