/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author FA22-BSE-025
 */
import java.util.ArrayList;
import java.util.List;

// Observer Pattern Interface
interface TransportObserver {
    void update(TransportData transportData);
}

// Observer Manager
class TransportStatusManager {
    private List<TransportObserver> observers = new ArrayList<>();

    public void addObserver(TransportObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TransportObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(TransportData transportData) {
        for (TransportObserver observer : observers) {
            observer.update(transportData);
        }
    }
}

// Data Access Layer
class TransportDataManager {
    private List<TransportItem> data;

    public TransportDataManager() {
        this.data = new ArrayList<>();
    }

    public void loadTransportData() {
        // Simulates loading data
        data.add(new TransportItem("PublicTransport", "Bus 1"));
        data.add(new TransportItem("PublicTransport", "Train A"));
        data.add(new TransportItem("OwnTransport", "User's Car"));
        data.add(new TransportItem("UniversityTransport", "Campus Shuttle"));
    }

    public List<TransportItem> getData() {
        return data;
    }
}

// TransportData Class
class TransportItem {
    private String type;
    private String details;

    public TransportItem(String type, String details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }
}

// Filter Interface
interface TransportFilter {
    List<String> process(List<TransportItem> data);
}

// Filter Implementations
class PublicTransport implements TransportFilter {
    @Override
    public List<String> process(List<TransportItem> data) {
        List<String> results = new ArrayList<>();
        for (TransportItem item : data) {
            if ("PublicTransport".equals(item.getType())) {
                results.add(item.getDetails());
            }
        }
        return results;
    }
}

class OwnTransport implements TransportFilter {
    @Override
    public List<String> process(List<TransportItem> data) {
        List<String> results = new ArrayList<>();
        for (TransportItem item : data) {
            if ("OwnTransport".equals(item.getType())) {
                results.add(item.getDetails());
            }
        }
        return results;
    }
}

class UniversityTransport implements TransportFilter {
    @Override
    public List<String> process(List<TransportItem> data) {
        List<String> results = new ArrayList<>();
        for (TransportItem item : data) {
            if ("UniversityTransport".equals(item.getType())) {
                results.add(item.getDetails());
            }
        }
        return results;
    }
}

// Business Logic Layer
class TransportData {
    private List<String> publicTransport;
    private List<String> ownTransport;
    private List<String> universityTransport;

    public TransportData(List<String> publicTransport, List<String> ownTransport, List<String> universityTransport) {
        this.publicTransport = publicTransport;
        this.ownTransport = ownTransport;
        this.universityTransport = universityTransport;
    }

    @Override
    public String toString() {
        return "Available Transport Options:\n" +
                "Public Transport: " + String.join(", ", publicTransport) + "\n" +
                "Own Transport: " + String.join(", ", ownTransport) + "\n" +
                "University Transport: " + String.join(", ", universityTransport) + "\n";
    }
}


class TransportSystem {
    private TransportDataManager dataManager;
    private TransportStatusManager statusManager;

    public TransportSystem(TransportDataManager dataManager) {
        this.dataManager = dataManager;
        this.statusManager = new TransportStatusManager();
    }

    public void addObserver(TransportObserver observer) {
        statusManager.addObserver(observer);
    }

    public void processTransport() {
        dataManager.loadTransportData();
        List<TransportItem> transportData = dataManager.getData();

        PublicTransport publicTransport = new PublicTransport();
        OwnTransport ownTransport = new OwnTransport();
        UniversityTransport universityTransport = new UniversityTransport();

        List<String> processedPublic = publicTransport.process(transportData);
        List<String> processedOwn = ownTransport.process(transportData);
        List<String> processedUni = universityTransport.process(transportData);

        TransportData transportDataObj = new TransportData(processedPublic, processedOwn, processedUni);
        statusManager.notifyObservers(transportDataObj);
    }
}

// Presentation Layer
class TransportUI implements TransportObserver {
    @Override
    public void update(TransportData transportData) {
        displayOptions(transportData);
    }

    public void displayOptions(TransportData transportData) {
        System.out.println(transportData);
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        TransportDataManager dataManager = new TransportDataManager();
        TransportSystem transportSystem = new TransportSystem(dataManager);
        TransportUI ui = new TransportUI();

        transportSystem.addObserver(ui);
        transportSystem.processTransport();
    }
}
