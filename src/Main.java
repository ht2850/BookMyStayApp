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

class ThreadSafeInventory {
    private final Map<String, Integer> inventory = new HashMap<>();
    private final Set<String> allocatedIDs = new HashSet<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean processBooking(ReservationRequest request) {
        String type = request.getRoomType();
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            String roomID = type.substring(0, 1).toUpperCase() + "-" + (100 + available);
            allocatedIDs.add(roomID);
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Success: " +
                    request.getGuestName() + " secured Room " + roomID);
            return true;
        } else {
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Failed: No rooms for " +
                    request.getGuestName());
            return false;
        }
    }

    public synchronized void displayFinalState() {
        System.out.println("\n--- Final Thread-Safe State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Total Allocated: " + allocatedIDs.size());
        System.out.println("Allocated IDs: " + allocatedIDs);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Book My Stay App v11.0 ---");

        ThreadSafeInventory sharedInventory = new ThreadSafeInventory();
        sharedInventory.addRoomType("Suite", 2); // Only 2 rooms available

        // Creating 5 simultaneous booking attempts for only 2 rooms
        List<Thread> guestThreads = new ArrayList<>();
        String[] guests = {"Alice", "Bob", "Charlie", "Dave", "Eve"};

        for (String name : guests) {
            Thread t = new Thread(() -> {
                ReservationRequest req = new ReservationRequest(name, "Suite");
                sharedInventory.processBooking(req);
            });
            guestThreads.add(t);
        }

        System.out.println("Simulating simultaneous clicks for the last 2 Suites...\n");

        for (Thread t : guestThreads) t.start();
        for (Thread t : guestThreads) t.join(); // Wait for all threads to finish

        sharedInventory.displayFinalState();
    }
}