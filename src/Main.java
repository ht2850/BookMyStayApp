import java.util.*;

class ReservationRequest {
    private String guestName;
    private String roomType;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return guestName + " (" + roomType + ")";
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
        allocatedRooms.put(type, new HashSet<>());
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public String allocateRoom(String type) {
        int currentCount = inventory.get(type);
        String roomId = type.substring(0, 1).toUpperCase() + "-" + (100 + currentCount);

        inventory.put(type, currentCount - 1);
        allocatedRooms.get(type).add(roomId);
        return roomId;
    }

    public void displayStatus() {
        System.out.println("\n--- Final System State ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> Available: " + inventory.get(type) +
                    " | Allocated IDs: " + allocatedRooms.get(type));
        }
        System.out.println("--------------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v6.0 ---");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Suite", 1);

        Queue<ReservationRequest> requestQueue = new LinkedList<>();
        requestQueue.add(new ReservationRequest("Alice", "Suite"));
        requestQueue.add(new ReservationRequest("Bob", "Single"));
        requestQueue.add(new ReservationRequest("Charlie", "Suite")); // Should fail (out of stock)
        requestQueue.add(new ReservationRequest("Dave", "Single"));

        System.out.println("Processing Queue...\n");

        while (!requestQueue.isEmpty()) {
            ReservationRequest request = requestQueue.poll();
            System.out.print("Processing " + request + ": ");

            if (inventory.isAvailable(request.getRoomType())) {
                String assignedId = inventory.allocateRoom(request.getRoomType());
                System.out.println("CONFIRMED. Assigned Room: " + assignedId);
            } else {
                System.out.println("REJECTED. No availability.");
            }
        }

        inventory.displayStatus();
    }
}