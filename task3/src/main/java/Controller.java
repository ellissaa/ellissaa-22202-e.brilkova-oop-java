import game_model.Model;
import game_model.objects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private final Model gameModel;

    public Controller(Model gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = gameModel.getPlayer();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                player.setSpeedX(10);
                break;
            case KeyEvent.VK_A:
                player.setSpeedX(-10);
                break;
            case KeyEvent.VK_SPACE:
                gameModel.playerShoot();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player player = gameModel.getPlayer();
        if (e.getKeyCode() == KeyEvent.VK_D ||
                e.getKeyCode() == KeyEvent.VK_A) player.setSpeedX(0);
    }
}