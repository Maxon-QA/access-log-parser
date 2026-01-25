package ru.courses.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {

    final String IP, pathRequest, refer;
    final int codeRespond, sizeRespond;
    final LocalDateTime dateTimeRequest;
    final MethodRequest methodRequest;
    final UserAgent userAgent;

    private int cursorParse = 0;

    public LogEntry(String line) {
        String[] resultParsing = parsingLine(line);
        this.IP = resultParsing[0];
        this.dateTimeRequest = LocalDateTime.parse(resultParsing[1], DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH));
        this.methodRequest = MethodRequest.valueOf(resultParsing[2]);
        this.pathRequest = resultParsing[3];
        this.codeRespond = Integer.parseInt(resultParsing[4]);
        this.sizeRespond = Integer.parseInt(resultParsing[5]);
        this.refer = resultParsing[6];
        this.userAgent = new UserAgent(resultParsing[7]);
    }

    public String getIP() {
        return IP;
    }

    public LocalDateTime getDateTimeRequest() {
        return dateTimeRequest;
    }

    public String getPathRequest() {
        return pathRequest;
    }

    public String getRefer() {
        return refer;
    }

    public int getCodeRespond() {
        return codeRespond;
    }

    public int getSizeRespond() {
        return sizeRespond;
    }

    public MethodRequest getMethodRequest() {
        return methodRequest;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public String[] parsingLine(String line) {
        String[] result = new String[8];

        parseIP(result, line);
        parseDateTimeRequest(result, line);
        parseMethodRequest(result, line);
        parsePathRequest(result, line);
        parseCodeRespond(result, line);
        parseSizeRespond(result, line);
        parseRefer(result, line);
        parseUserAgent(result, line);

        return result;
    }

    private void parseIP(String[] result, String line) {
        int indexFinish = line.indexOf(' ');
        result[0] = line.substring(0, indexFinish);
        cursorParse = indexFinish;
    }

    private void parseDateTimeRequest(String[] result, String line) {
        int indexStart = line.indexOf('[');
        int indexFinish = line.indexOf(']');
        result[1] = line.substring(indexStart + 1, indexFinish);
        cursorParse = indexFinish;
    }

    private void parseMethodRequest(String[] result, String line) {
        int indexStart = line.indexOf('"', cursorParse);
        int indexFinish = line.indexOf(' ', indexStart);
        result[2] = line.substring(indexStart + 1, indexFinish);
        cursorParse = indexFinish;
    }

    private void parsePathRequest(String[] result, String line) {
        int indexStart = line.indexOf('/', cursorParse);
        int indexFinish = line.indexOf(' ', indexStart);
        result[3] = line.substring(indexStart, indexFinish);
        cursorParse = indexFinish;
    }

    private void parseCodeRespond(String[] result, String line) {
        int indexStart = line.indexOf("\" ", cursorParse);
        int indexFinish = line.indexOf(' ', indexStart + 2);
        result[4] = line.substring(indexStart + 2, indexFinish);
        cursorParse = indexFinish;
    }

    private void parseSizeRespond(String[] result, String line) {
        int indexStart = line.indexOf(' ', cursorParse);
        int indexFinish = line.indexOf(' ', indexStart + 1);
        result[5] = line.substring(indexStart + 1, indexFinish);
        cursorParse = indexFinish;
    }

    private void parseRefer(String[] result, String line) {
        int indexStart = line.indexOf('"', cursorParse);
        int indexFinish = line.indexOf('"', indexStart + 1);
        result[6] = line.substring(indexStart + 1, indexFinish);
        cursorParse = indexFinish;
    }

    private void parseUserAgent(String[] result, String line) {
        int indexStart = line.indexOf('"', cursorParse + 1);
        int indexFinish = line.indexOf('"', indexStart + 1);
        result[7] = line.substring(indexStart + 1, indexFinish);
        cursorParse = indexFinish;
    }
}
