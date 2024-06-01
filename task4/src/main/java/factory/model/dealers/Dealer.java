package factory.model.dealers;

import factory.model.products.Car;
import factory.model.storage.CarStockController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Dealer {
    public final static int DEFAULT_TIMEOUT = 1000;
    public final static int MAX_TIMEOUT = 2000;
    public final static int MIN_TIMEOUT = 100;

    private final UUID id;
    private int requestTimeout;
    private final CarStockController carStockController;

    private final Logger logger = LoggerFactory.getLogger(Dealer.class);

    public Dealer(int requestTimeout, CarStockController controller) {
        this.requestTimeout = requestTimeout;
        this.carStockController = controller;
        id = UUID.randomUUID();
    }

    public Car requestCar() {
        Car car = carStockController.getCar(); // короче сделать через CarStock, чтобы дилер не знал про контролер
        logger.info("Dealer {}: Auto {} (Body: {}, Motor: {}, Accessory: {}",
                id, car.getId(), car.getBody().getId(), car.getEngine().getId(),
                car.getAccessory().getId());
        return car;
    }

    public void startRequesting() { // в беск цикле запрашиваем новые машины
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
