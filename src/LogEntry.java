import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {

    final String IP, pathRequest, refer;
    final int codeRespond, sizeRespond;
    final LocalDateTime dateTimeRequest;
    final MethodRequest methodRequest;
    final UserAgent userAgent;

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

        //1 IP
        int indexFinishIP = line.indexOf(' ');
        result[0] = line.substring(0, indexFinishIP);

        //2 dateTimeRequest
        int indexStartDateTimeRequest = line.indexOf('[');
        int indexFinishDateTimeRequest = line.indexOf(']');
        result[1] = line.substring(indexStartDateTimeRequest + 1, indexFinishDateTimeRequest);

        //3 methodRequest
        int indexStartMethodRequest = line.indexOf('"', indexFinishDateTimeRequest);
        int indexFinishMethodRequest = line.indexOf(' ', indexStartMethodRequest);
        result[2] = line.substring(indexStartMethodRequest + 1, indexFinishMethodRequest);

        //4 pathRequest
        int indexStartPathRequest = line.indexOf('/', indexFinishMethodRequest);
        int indexFinishPathRequest = line.indexOf(' ', indexStartPathRequest);
        result[3] = line.substring(indexStartPathRequest, indexFinishPathRequest);

        //5 codeRespond
        int indexStartCodeRespond = line.indexOf("\" ", indexFinishPathRequest);
        int indexFinishCodeRespond = line.indexOf(' ', indexStartCodeRespond + 2);
        result[4] = line.substring(indexStartCodeRespond + 2, indexFinishCodeRespond);

        //6 sizeRespond
        int indexStartSizeRespond = line.indexOf(' ', indexFinishCodeRespond);
        int indexFinishSizeRespond = line.indexOf(' ', indexStartSizeRespond + 1);
        result[5] = line.substring(indexStartSizeRespond + 1, indexFinishSizeRespond);

        //7 refer
        int indexStartRefer = line.indexOf('"', indexFinishSizeRespond);
        int indexFinishRefer = line.indexOf('"', indexStartRefer + 1);
        result[6] = line.substring(indexStartRefer + 1, indexFinishRefer);

        //8 userAgent
        int indexStartUserAgent = line.indexOf('"', indexFinishRefer + 1);
        int indexFinishUserAgent = line.indexOf('"', indexStartUserAgent + 1);
        result[7] = line.substring(indexStartUserAgent + 1, indexFinishUserAgent);

        return result;
    }
}
