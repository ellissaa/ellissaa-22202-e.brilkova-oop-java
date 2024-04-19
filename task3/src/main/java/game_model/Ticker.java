package game_model;

public class Ticker extends Thread {
    private final Model model;

    public Ticker(Model model) {
        this.model = model;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(20);
                model.update();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}