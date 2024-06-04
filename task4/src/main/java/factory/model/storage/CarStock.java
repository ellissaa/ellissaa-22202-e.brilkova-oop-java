package factory.model.storage;

import factory.model.products.Car;

public class CarStock extends AbstractStock<Car> {
    private CarStockListener carStockListener;

    public CarStock(int capacity) {
        super(capacity);
        this.id = StockId.CAR;
    }

    public void setCarStockListener(CarStockListener carStockListener) {
        this.carStockListener = carStockListener;
    }

    @Override
    synchronized public Car get() {
        Car car = super.get();
        if (carStockListener != null)
            carStockListener.requestCar();
        return car;
    }
}
