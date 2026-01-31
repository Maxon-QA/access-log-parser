package ru.courses.main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime, maxTime;
    private final Set<String> pagesWebSite;
    private final Map<String, Integer> countOS;

    {
        this.totalTraffic = 0L;
        this.minTime = null;
        this.maxTime = null;
        this.pagesWebSite = new HashSet<>();
        this.countOS = new HashMap<>();
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

        if (logEntry.getCodeRespond() == 200) {
            pagesWebSite.add(logEntry.getPathRequest());
        }

        String thisOS = logEntry.getUserAgent().getOS();
        if (!countOS.containsKey(thisOS)) {
            countOS.put(thisOS, 1);
        } else countOS.put(thisOS, countOS.get(thisOS) + 1);

    }

    public Set<String> getPagesWebSite() {
        return pagesWebSite;
    }

    public HashMap<String, Double> getStatOS() {
        int allCount = 0;
        List<Integer> listCount = new ArrayList<>(countOS.values().stream().toList());
        for (int i = 0; i < listCount.size(); i++) {
            allCount += listCount.get(i);
        }

        HashMap<String, Double> resultMap = new HashMap<>();

        for (String OS : countOS.keySet()) {
            resultMap.put(OS, Double.valueOf(countOS.get(OS)) / allCount);
        }
        return resultMap;
    }

    public double getTrafficRate() {
        double duration = Duration.between(minTime, maxTime).toHours();
        return totalTraffic / duration;
    }
}
