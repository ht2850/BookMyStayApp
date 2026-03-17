import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single", 100.0, "Single Bed, Wi-Fi"); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double", 150.0, "Queen Bed, TV, Wi-Fi"); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 300.0, "King Bed, Jacuzzi, Mini-bar"); }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(inventory);
    }
}

class SearchService {
    public void searchAvailableRooms(List<Room> roomCatalog, RoomInventory inventory) {
        System.out.println("\n--- Search Results: Available Rooms ---");
        boolean found = false;

        for (Room room : roomCatalog) {
            int count = inventory.getAvailability(room.getRoomType());

            if (count > 0) {
                displayRoomCard(room, count);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
        System.out.println("---------------------------------------\n");
    }

    private void displayRoomCard(Room room, int count) {
        System.out.println("Room Type: " + room.getRoomType());
        System.out.println("Price: $" + room.getPrice());
        System.out.println("Amenities: " + room.getAmenities());
        System.out.println("Status: " + count + " units left");
        System.out.println(".......................................");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v4.0 ---");

        RoomInventory hotelInventory = new RoomInventory();
        hotelInventory.addRoomType("Single", 5);
        hotelInventory.addRoomType("Double", 0);
        hotelInventory.addRoomType("Suite", 2);

        List<Room> roomCatalog = new ArrayList<>();
        roomCatalog.add(new SingleRoom());
        roomCatalog.add(new DoubleRoom());
        roomCatalog.add(new SuiteRoom());

        SearchService searchService = new SearchService();

        System.out.println("Guest is performing a search...");
        searchService.searchAvailableRooms(roomCatalog, hotelInventory);

        System.out.println("System state verified: Inventory remains unchanged.");
    }
}