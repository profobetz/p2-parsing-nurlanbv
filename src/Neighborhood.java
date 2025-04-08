import java.util.ArrayList;
import java.util.List;

public class Neighborhood {
    private String name;
    private List<ServiceRequest> requests;

    public Neighborhood(String name) {
        this.name = name;
        this.requests = new ArrayList<>();
    }

    public void addRequest(ServiceRequest request) {
        requests.add(request);
    }

    public String getName() {
        return name;
    }

    public List<ServiceRequest> getOpenCases() {
        List<ServiceRequest> open = new ArrayList<>();
        for (ServiceRequest r : requests) {
            if (r.isOpen()) open.add(r);
        }
        return open;
    }

    public List<ServiceRequest> getOverdueCases() {
        List<ServiceRequest> overdue = new ArrayList<>();
        for (ServiceRequest r : requests) {
            if (r.isOverdue()) overdue.add(r);
        }
        return overdue;
    }

    public double getAverageDaysOpen() {
        long totalDays = 0;
        int count = 0;
        for (ServiceRequest r : requests) {
            if (!r.isOpen()) {
                totalDays += r.getDaysOpen();
                count++;
            }
        }
        return count == 0 ? 0 : (double) totalDays / count;
    }

    public int getTotalRequestCount() {
        return requests.size();
    }

    public double getOverdueRate() {
        int total = requests.size();
        int overdue = getOverdueCases().size();
        return total == 0 ? 0 : (double) overdue / total * 100;
    }
}
