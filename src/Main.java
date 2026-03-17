import java.util.*;

class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void validateBooking(String type) throws BookingException {
        if (!inventory.containsKey(type)) {
            throw new BookingException("Error: Room type '" + type + "' does not exist in our system.");
        }
        if (inventory.get(type) <= 0) {
            throw new BookingException("Error: Room type '" + type + "' is currently sold out.");
        }
    }

    public void deductInventory(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayStatus() {
        System.out.println("Current Inventory: " + inventory);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v9.0 ---");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Suite", 2);

        String[] testRequests = {"Single", "Penthouse", "Single", "Suite"};

        for (String type : testRequests) {
            System.out.println("\nAttempting to book: " + type);
            try {
                inventory.validateBooking(type);
                inventory.deductInventory(type);
                System.out.println("Success: Booking confirmed for " + type);
            } catch (BookingException e) {
                System.err.println("Validation Failed -> " + e.getMessage());
            } finally {
                inventory.displayStatus();
            }
        }

        System.out.println("\nSystem remains stable after handling all errors.");
    }
}