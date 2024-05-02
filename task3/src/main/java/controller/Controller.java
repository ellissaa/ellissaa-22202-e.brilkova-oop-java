package controller;

import game_model.Model;
import game_model.objects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private final Model gameModel;
    private long lastShotTime;

    public Controller(Model gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = gameModel.getPlayer();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                player.setSpeedX(Player.playerSpeed);
                break;
            case KeyEvent.VK_LEFT:
                player.setSpeedX(-Player.playerSpeed);
                break;
            case KeyEvent.VK_SPACE:
                long currTime = System.currentTimeMillis();
                if (currTime - lastShotTime > 500) {
                    gameModel.playerShoot();
                    lastShotTime = currTime;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player player = gameModel.getPlayer();
        if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_RIGHT) player.setSpeedX(0);
    }
}