import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Stack<String>> allocatedRoomIDs = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
        allocatedRoomIDs.put(type, new Stack<>());
    }

    public String bookRoom(String type) {
        if (inventory.getOrDefault(type, 0) > 0) {
            int currentCount = inventory.get(type);
            String roomID = type.substring(0, 1).toUpperCase() + "-" + (100 + currentCount);

            inventory.put(type, currentCount - 1);
            allocatedRoomIDs.get(type).push(roomID);
            return roomID;
        }
        return null;
    }

    public void cancelBooking(String type) throws Exception {
        if (!allocatedRoomIDs.containsKey(type) || allocatedRoomIDs.get(type).isEmpty()) {
            throw new Exception("Cancellation Failed: No active bookings found for type " + type);
        }

        String releasedID = allocatedRoomIDs.get(type).pop();
        int currentCount = inventory.get(type);
        inventory.put(type, currentCount + 1);

        System.out.println("Rollback Successful: Room " + releasedID + " returned to inventory.");
    }

    public void displayStatus() {
        System.out.println("Inventory: " + inventory);
        System.out.println("Allocated IDs: " + allocatedRoomIDs);
        System.out.println("--------------------------------------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v10.0 ---");

        RoomInventory hotel = new RoomInventory();
        hotel.addRoomType("Suite", 2);

        System.out.println("Initial State:");
        hotel.displayStatus();

        System.out.println("Processing 2 bookings for 'Suite'...");
        hotel.bookRoom("Suite");
        hotel.bookRoom("Suite");
        hotel.displayStatus();

        System.out.println("Guest initiates cancellation for 'Suite'...");
        try {
            hotel.cancelBooking("Suite");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Final State after Rollback:");
        hotel.displayStatus();

        System.out.println("Attempting invalid cancellation...");
        try {
            hotel.cancelBooking("Single");
        } catch (Exception e) {
            System.out.println("Caught Expected Error: " + e.getMessage());
        }
    }
}