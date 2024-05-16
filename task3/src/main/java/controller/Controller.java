package controller;

import model.Model;
import model.objects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener, AutoCloseable {
    private final Model gameModel;
    private final Ticker ticker;

    public Controller(Model gameModel) {
        this.gameModel = gameModel;
        ticker = new Ticker(gameModel);
        ticker.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                gameModel.updatePlayerSpeed(Player.playerSpeed);
                break;
            case KeyEvent.VK_LEFT:
                gameModel.updatePlayerSpeed(-Player.playerSpeed);
                break;
            case KeyEvent.VK_SPACE:
                gameModel.playerShoot();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_RIGHT)
            gameModel.updatePlayerSpeed(0);
    }

    @Override
    public void close() throws InterruptedException {
        ticker.interrupt();
        ticker.join();
    }
}
