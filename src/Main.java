import java.util.LinkedList;
import java.util.Queue;

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
        return "Request [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}

class BookingQueue {
    private Queue<ReservationRequest> requestQueue;

    public BookingQueue() {
        this.requestQueue = new LinkedList<>();
    }

    public void enqueueRequest(ReservationRequest request) {
        requestQueue.add(request);
        System.out.println("Queued: " + request);
    }

    public ReservationRequest peekNextRequest() {
        return requestQueue.peek();
    }

    public ReservationRequest dequeueRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    public int getQueueSize() {
        return requestQueue.size();
    }

    public void displayQueue() {
        System.out.println("\n--- Current Booking Queue (FIFO) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (ReservationRequest req : requestQueue) {
                System.out.println(">> " + req);
            }
        }
        System.out.println("------------------------------------\n");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v5.0 ---");

        BookingQueue bookingQueue = new BookingQueue();

        System.out.println("Simulating incoming booking requests...");
        bookingQueue.enqueueRequest(new ReservationRequest("Alice", "Suite"));
        bookingQueue.enqueueRequest(new ReservationRequest("Bob", "Single"));
        bookingQueue.enqueueRequest(new ReservationRequest("Charlie", "Double"));

        bookingQueue.displayQueue();

        System.out.println("Preparing for next processing stage...");
        if (!bookingQueue.isEmpty()) {
            System.out.println("Next request to process: " + bookingQueue.peekNextRequest());
        }

        System.out.println("\nTotal requests waiting: " + bookingQueue.getQueueSize());
        System.out.println("Note: Inventory has not been modified yet.");
    }
}