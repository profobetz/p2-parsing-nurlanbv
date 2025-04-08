import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class RequestLoader {
    private File file;

    public RequestLoader(File file) {
        this.file = file;
    }

    public List<Neighborhood> load() {
        Map<String, Neighborhood> map = new HashMap<>();

        try {
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setDelimiter(',')
                    .setQuote('"')
                    .build();

            CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, format);

            for (CSVRecord record : parser) {
                try {
                    String neighborhood = record.get("neighborhood").trim();
                    String openStr = record.get("open_dt").trim();
                    String closeStr = record.get("closed_dt").trim();
                    String onTimeStr = record.get("ontime").trim();
                    String reason = record.get("reason").trim();

                    LocalDate openDate = LocalDate.parse(openStr.split(" ")[0]); // e.g., "2025-01-01 14:00:00"
                    LocalDate closedDate = closeStr.isEmpty() ? null : LocalDate.parse(closeStr.split(" ")[0]);
                    boolean onTime = onTimeStr.equalsIgnoreCase("true");

                    ServiceRequest request = new ServiceRequest(openDate, closedDate, onTime, reason, neighborhood);

                    map.putIfAbsent(neighborhood, new Neighborhood(neighborhood));
                    map.get(neighborhood).addRequest(request);
                } catch (Exception e) {
                    System.err.println("Skipping invalid row: " + record);
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
        }

        return new ArrayList<>(map.values());
    }
}
