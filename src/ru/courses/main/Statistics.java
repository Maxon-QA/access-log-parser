package ru.courses.main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {

    private int countEntry;
    private long totalTraffic;
    private int totalBot;
    private int totalError;
    private LocalDateTime minTime, maxTime;
    private final Set<String> pagesWebSite;
    private final Set<String> pagesWebSiteNotFound;
    private final Set<String> uniqUser;
    private final Map<String, Integer> countOS;
    private final Map<String, Integer> countBrowser;

    {
        this.countEntry = 0;
        this.totalTraffic = 0L;
        this.totalBot = 0;
        this.totalError = 0;
        this.minTime = null;
        this.maxTime = null;
        this.pagesWebSite = new HashSet<>();
        this.pagesWebSiteNotFound = new HashSet<>();
        this.uniqUser = new HashSet<>();
        this.countOS = new HashMap<>();
        this.countBrowser = new HashMap<>();
    }

    public void addEntry(LogEntry logEntry) {
        countEntry++;

        totalTraffic += logEntry.getSizeRespond();

        if (logEntry.getUserAgent().getIsBot()) totalBot++;

        if (logEntry.getCodeRespond() >= 400 && logEntry.getCodeRespond() <= 599) totalError++;

        LocalDateTime dateTime = logEntry.getDateTimeRequest();
        if (minTime == null || minTime.isAfter(dateTime)) {
            minTime = dateTime;
        }
        if (maxTime == null || maxTime.isBefore(dateTime)) {
            maxTime = dateTime;
        }

        if (logEntry.getCodeRespond() == 200) {
            pagesWebSite.add(logEntry.getPathRequest());
        } else if (logEntry.getCodeRespond() == 404) {
            pagesWebSiteNotFound.add(logEntry.getPathRequest());
        }

        if (!logEntry.getUserAgent().getIsBot()) {
            uniqUser.add(logEntry.getIP());
        }

        String thisOS = logEntry.getUserAgent().getOS();
        if (!countOS.containsKey(thisOS)) {
            countOS.put(thisOS, 1);
        } else countOS.put(thisOS, countOS.get(thisOS) + 1);

        String thisBrowser = logEntry.getUserAgent().getBrowser();
        if (!countBrowser.containsKey(thisBrowser)) {
            countBrowser.put(thisBrowser, 1);
        } else countBrowser.put(thisBrowser, countBrowser.get(thisBrowser) + 1);
    }

    public Set<String> getPagesWebSite() {
        return pagesWebSite;
    }

    public Set<String> getPagesWebSiteNotFound() {
        return pagesWebSiteNotFound;
    }

    public HashMap<String, Double> statOS() {
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

    public HashMap<String, Double> statBrowser() {
        int allCount = 0;
        List<Integer> listCount = new ArrayList<>(countBrowser.values().stream().toList());
        for (Integer integer : listCount) {
            allCount += integer;
        }

        HashMap<String, Double> resultMap = new HashMap<>();

        for (String browser : countBrowser.keySet()) {
            resultMap.put(browser, Double.valueOf(countBrowser.get(browser)) / allCount);
        }
        return resultMap;
    }

    public double trafficRate() {
        double duration = (double) Duration.between(minTime, maxTime).toSeconds() / 3600;
        return totalTraffic / duration;
    }

    public double visitRate() {
        double duration = (double) Duration.between(minTime, maxTime).toSeconds() / 3600;
        return (countEntry - totalBot) / duration;
    }

    public double errorRate() {
        double duration = (double) Duration.between(minTime, maxTime).toSeconds() / 3600;
        return totalError / duration;
    }

    public double averageUniqVisit() {
        return ((double) (countEntry - totalBot) / uniqUser.size());
    }

}
