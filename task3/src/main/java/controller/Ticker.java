package controller;

public class Ticker extends Thread {
    private final GameController gameModel;

    public Ticker(GameController gameModel) {
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