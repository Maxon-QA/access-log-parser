public class UserAgent {

    final String OS, browser;

    public UserAgent(String line) {
        this.OS = parsingOS(line);
        this.browser = parsingBrowser(line);
    }

    public String parsingOS(String line) {
        if (line.equals("-")) return null;

        int IndexFinishToken = line.indexOf(' ');
        if (IndexFinishToken == -1) {
            return null;
        }

        int IndexStartSystemInformation = IndexFinishToken + 1;
        int IndexFinishSystemInformation = line.indexOf(')');
        if (line.charAt(IndexStartSystemInformation) != '(' || IndexFinishSystemInformation == -1) {
            return null;
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
        } else return null;
    }

    public String parsingBrowser(String line) {
        if (line.equals("-")) return null;

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
        } else return "Other";
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "OS='" + OS + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }
}