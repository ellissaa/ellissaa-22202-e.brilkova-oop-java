package controller;

import model.Model;

public class Ticker extends Thread {
    private final Model gameModel;

    public Ticker(Model gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(10);
                gameModel.update();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}