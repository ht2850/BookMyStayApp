import java.util.ArrayList;
import java.util.List;

class ConfirmedBooking {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public ConfirmedBooking(String reservationId, String guestName, String roomType, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
    }

    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return String.format("ID: %-8s | Guest: %-10s | Room: %-10s | Cost: $%.2f",
                reservationId, guestName, roomType, totalCost);
    }
}

class BookingHistory {
    private List<ConfirmedBooking> history;

    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    public void recordBooking(ConfirmedBooking booking) {
        history.add(booking);
    }

    public List<ConfirmedBooking> getHistoryRecords() {
        return new ArrayList<>(history);
    }
}

class ReportService {
    public void generateSummaryReport(List<ConfirmedBooking> bookings) {
        System.out.println("\n========== BOOKING SUMMARY REPORT ==========");
        double totalRevenue = 0;

        if (bookings.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (ConfirmedBooking b : bookings) {
                System.out.println(b);
                totalRevenue += b.getTotalCost();
            }
        }

        System.out.println("--------------------------------------------");
        System.out.println("Total Bookings: " + bookings.size());
        System.out.printf("Total Revenue:  $%.2f\n", totalRevenue);
        System.out.println("============================================\n");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App v8.0 ---");

        BookingHistory historyStore = new BookingHistory();
        ReportService reportService = new ReportService();

        System.out.println("Recording confirmed bookings into history...");

        historyStore.recordBooking(new ConfirmedBooking("S-101", "Alice", "Suite", 325.0));
        historyStore.recordBooking(new ConfirmedBooking("R-202", "Bob", "Single", 100.0));
        historyStore.recordBooking(new ConfirmedBooking("D-303", "Charlie", "Double", 150.0));

        List<ConfirmedBooking> records = historyStore.getHistoryRecords();
        reportService.generateSummaryReport(records);

        System.out.println("Audit trail complete. History is preserved in arrival order.");
    }
}