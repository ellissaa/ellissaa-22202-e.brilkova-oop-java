package factory.model.suppliers;

import factory.model.products.AbstractProduct;
import factory.model.storage.AbstractStock;

public abstract class Supplier<T extends AbstractProduct> {
    public final static int DEFAULT_TIMEOUT = 1000;
    public final static int MAX_TIMEOUT = 2000;
    public final static int MIN_TIMEOUT = 100;

    protected int supplyTimeout;
    protected AbstractStock<T> associatedStock;

    protected Supplier(int supplyTimeout, AbstractStock<T> stock) {
        this.supplyTimeout = supplyTimeout;
        this.associatedStock = stock;
    }

    public abstract T produce();

    public void supplyToStock() {
        while (true) {
            try {
                Thread.sleep(supplyTimeout);
            } catch (InterruptedException e) {
                return;
            }
            T product = produce();
            associatedStock.add(product);
        }
    }

    public void setSupplyTimeout(int supplyTimeout) {
        this.supplyTimeout = supplyTimeout;
    }

    public int getSupplyTimeout() {
        return supplyTimeout;
    }
}
