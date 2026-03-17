import java.util.HashMap;
import java.util.Map;

abstract class Room {
    private String roomType;
    private double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single", 100.0); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double", 150.0); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 300.0); }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int current = inventory.get(type);
            inventory.put(type, Math.max(0, current + change));
        }
    }

    public void displayInventory() {
        System.out.println("--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
        System.out.println("------------------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v3.0 ---");

        RoomInventory hotelInventory = new RoomInventory();

        hotelInventory.addRoomType("Single", 10);
        hotelInventory.addRoomType("Double", 5);
        hotelInventory.addRoomType("Suite", 2);

        hotelInventory.displayInventory();

        System.out.println("Simulating a booking for a Double room...");
        hotelInventory.updateAvailability("Double", -1);

        System.out.println("Availability of Double rooms: " + hotelInventory.getAvailability("Double"));

        hotelInventory.displayInventory();
    }
}