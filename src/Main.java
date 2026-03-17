abstract class Room {
    private String roomType;
    private double price;
    private String amenities;

    public Room(String roomType, double price, String amenities) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
    }

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public String getAmenities() { return amenities; }

    public abstract void displayDescription();
}

// 2. Concrete Classes - Inheritance & Specialization
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 100.0, "Single Bed, Wi-Fi");
    }
    @Override
    public void displayDescription() {
        System.out.println("Perfect for solo travelers.");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 150.0, "Queen Bed, Wi-Fi, TV");
    }
    @Override
    public void displayDescription() {
        System.out.println("Ideal for couples or friends.");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 300.0, "King Bed, Mini-bar, Jacuzzi, Balcony");
    }
    @Override
    public void displayDescription() {
        System.out.println("Luxury experience with premium amenities.");
    }
}

// 3. Application Entry Point
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v2.0 ---");
        System.out.println("Initializing Room Inventory...\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        displayRoomInfo(single, singleAvailable);
        displayRoomInfo(doubleRoom, doubleAvailable);
        displayRoomInfo(suite, suiteAvailable);

        System.out.println("\nInventory Initialization Complete.");
    }

    private static void displayRoomInfo(Room room, int count) {
        System.out.println("Type: " + room.getRoomType());
        System.out.println("Price: $" + room.getPrice());
        System.out.println("Amenities: " + room.getAmenities());
        System.out.print("Description: ");
        room.displayDescription();
        System.out.println("Rooms Available: " + count);
        System.out.println("-----------------------------------");
    }
}