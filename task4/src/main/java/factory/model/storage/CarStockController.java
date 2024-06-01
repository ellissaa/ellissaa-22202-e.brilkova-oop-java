package factory.model.storage;

import factory.model.assembly.AssemblyLine;
import factory.model.assembly.AssemblyLineListener;
import factory.model.products.Car;

import java.util.ArrayList;
import java.util.List;

public class CarStockController implements AssemblyLineListener { // отвечает за запросы на создание новых машин
    private final CarStock carStock;
    private final AssemblyLine assemblyLine;
    private int numPendingRequests;

    private final List<CarStockControllerListener> listeners = new ArrayList<>();

    public CarStockController(int capacity, AssemblyLine assemblyLine) {
        this.carStock = new CarStock(capacity);
        this.assemblyLine = assemblyLine;
        assemblyLine.setListener(this);
        placeRequests(capacity); // запросы на создание новых машин
    }

    public void addListener(CarStockControllerListener listener) {
        listeners.add(listener);
        carStock.addListener(listener);
    }

    synchronized private void placeRequests(int numRequests) {
        for (int i = 0; i < numRequests; i++) {
            assemblyLine.requestCarProduction();
            numPendingRequests++;
        }

        for (CarStockControllerListener listener : listeners)
            listener.pendingUpdated(numPendingRequests);
    }

    synchronized public Car getCar() { // дилеры запрашивают машины у контроллера
        while (carStock.isEmpty()) {
            int numRequests = carStock.getCapacity() - carStock.getNumStored() - numPendingRequests; // кол-во = полный склад
            placeRequests(numRequests);
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        Car car = carStock.get();
        placeRequests(1);
        return car;
    }

    @Override
    synchronized public void produced(Car car) {
        carStock.add(car);
        numPendingRequests--;
        notify(); // для дилера, чтобы забрал машину

        for (CarStockControllerListener listener : listeners)
            listener.pendingUpdated(numPendingRequests);
    }
}
