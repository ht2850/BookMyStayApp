import java.util.*;

class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class AddOnManager {
    private Map<String, List<Service>> reservationAddOns;

    public AddOnManager() {
        this.reservationAddOns = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, Service service) {
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getName() + " to Reservation: " + reservationId);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = reservationAddOns.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public void displayReservationAddOns(String reservationId) {
        List<Service> services = reservationAddOns.get(reservationId);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for " + reservationId);
        } else {
            System.out.println("Add-ons for " + reservationId + ": " + services);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v7.0 ---");

        AddOnManager addOnManager = new AddOnManager();

        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Treatment", 80.0);
        Service wifi = new Service("Premium Wi-Fi", 15.0);

        String resId1 = "S-101";
        String resId2 = "SU-105";

        System.out.println("Processing Add-on Selections...");
        addOnManager.addServiceToReservation(resId1, breakfast);
        addOnManager.addServiceToReservation(resId1, wifi);
        addOnManager.addServiceToReservation(resId2, spa);

        System.out.println("\n--- Service Summary ---");
        addOnManager.displayReservationAddOns(resId1);
        System.out.println("Total Add-on Cost for " + resId1 + ": $" + addOnManager.calculateTotalServiceCost(resId1));

        System.out.println();

        addOnManager.displayReservationAddOns(resId2);
        System.out.println("Total Add-on Cost for " + resId2 + ": $" + addOnManager.calculateTotalServiceCost(resId2));

        System.out.println("\nCore inventory and booking state remain isolated from add-on logic.");
    }
}