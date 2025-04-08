import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ServiceRequest {
    private LocalDate openDate;
    private LocalDate closedDate;
    private boolean onTime;
    private String reason;
    private String neighborhood;

    public ServiceRequest(LocalDate openDate, LocalDate closedDate, boolean onTime, String reason, String neighborhood) {
        this.openDate = openDate;
        this.closedDate = closedDate;
        this.onTime = onTime;
        this.reason = reason;
        this.neighborhood = neighborhood;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public String getReason() {
        return reason;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public boolean isOpen() {
        return closedDate == null;
    }

    public boolean isOverdue() {
        return !onTime && closedDate != null;
    }

    public long getDaysOpen() {
        if (closedDate == null) {
            return ChronoUnit.DAYS.between(openDate, LocalDate.now());
        }
        return ChronoUnit.DAYS.between(openDate, closedDate);
    }
}
