import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {

    long totalTraffic;
    LocalDateTime minTime, maxTime;

    {
        this.totalTraffic = 0L;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getSizeRespond();

        LocalDateTime dateTime = logEntry.getDateTimeRequest();
        if (minTime == null || minTime.isAfter(dateTime)) {
            minTime = dateTime;
        }
        if (maxTime == null || maxTime.isBefore(dateTime)) {
            maxTime = dateTime;
        }
    }

    public double getTrafficRate() {
        double duration = Duration.between(minTime, maxTime).toHours();
        return totalTraffic / duration;
    }
}
