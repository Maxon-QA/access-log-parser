package ru.courses.main;

public class UserAgent {

    final private String OS, browser;
    final private boolean isBot;

    public UserAgent(String line) {
        this.OS = parsingOS(line);
        this.browser = parsingBrowser(line);
        this.isBot = parsingIsBot(line);
    }

    public String parsingOS(String line) {
        if (line.equals("-")) return "n/d";

        int IndexFinishToken = line.indexOf(' ');
        if (IndexFinishToken == -1) {
            return "n/d";
        }

        int IndexStartSystemInformation = IndexFinishToken + 1;
        int IndexFinishSystemInformation = line.indexOf(')');
        if (line.charAt(IndexStartSystemInformation) != '(' || IndexFinishSystemInformation == -1) {
            return "n/d";
        }

        String systemInformation = line.substring(IndexStartSystemInformation, IndexFinishSystemInformation);
        if (systemInformation.contains("Windows") || systemInformation.contains("Win")) {
            return "Windows";
        } else if (systemInformation.contains("Macintosh") || systemInformation.contains("Mac OS")) {
            return "macOS";
        } else if (systemInformation.contains("Android")) {
            return "Android";
        } else if (systemInformation.contains("Linux")) {
            return "Linux";
        } else return "n/d";
    }

    public String parsingBrowser(String line) {
        if (line.equals("-")) return "n/d";

        if (line.contains("Firefox")) {
            return "Firefox";
        } else if ((line.contains("Edg/")) || (line.contains("EdgA/")) || line.contains("EdgiOS/")) {
            return "Edge";
        } else if ((line.contains("OPT/")) || (line.contains("OPR/"))) {
            return "Opera";
        } else if (line.contains("Version/") && line.contains("Safari")) {
            return "Safari";
        } else if (line.contains("KHTML, like Gecko") && (line.contains("Chrome/"))) {
            return "Chrome";
        } else return "n/d";
    }

    public boolean parsingIsBot(String line) {
        return (line.toLowerCase().contains("bot"));
    }

    public String getOS() {
        return this.OS;
    }

    public String getBrowser() {
        return this.browser;
    }

    public boolean getIsBot() {
        return this.isBot;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "OS='" + OS + '\'' +
                ", browser='" + browser + '\'' +
                ", isBot=" + isBot +
                '}';
    }
}