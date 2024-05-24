package factory.model.dealers;

import factory.model.Factory;
import factory.model.products.Car;
import factory.model.storage.CarStockController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dealer {
    public final static int DEFAULT_TIMEOUT = 1000;
    public final static int MAX_TIMEOUT = 2000;
    public final static int MIN_TIMEOUT = 100;

    private static int nextId;
    private final int id;
    private int requestTimeout;
    private final CarStockController carStockController;

    private final Logger logger = LoggerFactory.getLogger(Dealer.class);

    public Dealer(int requestTimeout, CarStockController controller) {
        this.requestTimeout = requestTimeout;
        this.carStockController = controller;
        id = nextId++;
    }

    public Car requestCar() {
        Car car = carStockController.getCar();
        logger.info("Dealer {}: Auto {} (Body: {}, Motor: {}, Accessory: {}",
                id, car.getId(), car.getBody().getId(), car.getEngine().getId(),
                car.getAccessory().getId());
        return car;
    }

    public void startRequesting() {
        while (true) {
            try {
                Thread.sleep(requestTimeout);
            } catch (InterruptedException e) {
                return;
            }
            Car car = requestCar();
        }
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }
}
