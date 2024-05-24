package factory.model;

import factory.controller.FactoryControllerListener;
import factory.model.assembly.AssemblyLine;
import factory.model.dealers.Dealer;
import factory.model.exceptions.*;
import factory.model.storage.*;
import factory.model.suppliers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Factory implements FactoryControllerListener {
    private final AccessoryStock accessoryStock;
    private final BodyStock bodyStock;
    private final EngineStock engineStock;
    private final CarStockController carStockController;

    private final AssemblyLine assemblyLine;

    private final AccessorySupplier[] accessorySuppliers;
    private final BodySupplier bodySupplier;
    private final EngineSupplier engineSupplier;
    private final ExecutorService suppliersThreadPool;

    private final Dealer[] dealers;
    private final ExecutorService dealersThreadPool;

    private final List<ModelListener> listeners = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(Factory.class);

    public Factory() throws FactoryCreationException {
        ConfigReader configReader;
        try {
            configReader = new ConfigReader("factoryConf.properties");
        } catch (IOException e) {
            logger.error("Could not read config file. Error: " + e.getMessage());
            throw new FactoryCreationException("Could not read config file", e);
        }

        int numAccessorySuppliers, numDealers;
        try {
            accessoryStock = new AccessoryStock(configReader.readAccessoryStockSize());
            bodyStock = new BodyStock(configReader.readBodyStockSize());
            engineStock = new EngineStock(configReader.readEngineStockSize());

            assemblyLine = new AssemblyLine(accessoryStock, bodyStock, engineStock,
                    configReader.readNumWorkers());
            carStockController = new CarStockController(configReader.readCarStockSize(),
                    assemblyLine);

            numAccessorySuppliers = configReader.readNumAccessorySuppliers();
            numDealers = configReader.readNumDealers();
        } catch (ConfigException e) {
            logger.error("Bad formatting of config file. Error: " + e.getMessage());
            throw new FactoryCreationException("Bad formatting of config file", e);
        }

        accessorySuppliers = new AccessorySupplier[numAccessorySuppliers];
        for (int i = 0; i < numAccessorySuppliers; i++)
            accessorySuppliers[i] = new AccessorySupplier(
                    Supplier.DEFAULT_TIMEOUT, accessoryStock);
        bodySupplier = new BodySupplier(Supplier.DEFAULT_TIMEOUT, bodyStock);
        engineSupplier = new EngineSupplier(Supplier.DEFAULT_TIMEOUT, engineStock);
        suppliersThreadPool = Executors.newFixedThreadPool(numAccessorySuppliers + 2);

        dealers = new Dealer[numDealers];
        for (int i = 0; i < numDealers; i++) {
            dealers[i] = new Dealer(Dealer.DEFAULT_TIMEOUT, carStockController);
        }
        dealersThreadPool = Executors.newFixedThreadPool(numDealers);
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);

        accessoryStock.addListener(listener);
        bodyStock.addListener(listener);
        engineStock.addListener(listener);
        carStockController.addListener(listener);
    }

    public void start() {
        for (AccessorySupplier supplier : accessorySuppliers)
            suppliersThreadPool.submit(supplier::supplyToStock);
        suppliersThreadPool.submit(bodySupplier::supplyToStock);
        suppliersThreadPool.submit(engineSupplier::supplyToStock);

        for (Dealer dealer : dealers)
            dealersThreadPool.submit(dealer::startRequesting);
    }

    @Override
    public void accessoryTimeoutChanged(int newTimeout) {
        for (AccessorySupplier supplier : accessorySuppliers)
            supplier.setSupplyTimeout(newTimeout);
    }

    @Override
    public void bodyTimeoutChanged(int newTimeout) {
        bodySupplier.setSupplyTimeout(newTimeout);
    }

    @Override
    public void engineTimeoutChanged(int newTimeout) {
        engineSupplier.setSupplyTimeout(newTimeout);
    }

    @Override
    public void requestTimeoutChanged(int newTimeout) {
        for (Dealer dealer : dealers)
            dealer.setRequestTimeout(newTimeout);
    }
}
