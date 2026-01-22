import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    static int countFiles = 0;
    static String path;

    public static void main(String[] args) {
        while (true) {
            checkFileExist();
            readFile();
        }
    }

    public static void checkFileExist() {
        path = new Scanner(System.in).nextLine();
        File file = new File(path);

        boolean fileExists = file.exists();
        boolean isDirectory = file.isDirectory();

        if (isDirectory) {
            System.out.println("Указана директория папки, а не файла!");

        } else if (fileExists) {
            countFiles++;
            System.out.println("Путь указан верно");
            System.out.println("Это файл № " + countFiles);
        } else {
            System.out.println("Файл по указанному пути не найден!");
        }
    }

    public static void readFile() {
        final int CHECK_MAX_VALUE = 1024;
        Statistics statistics = new Statistics();

        String botYandex = "YandexBot";
        String botGoogle = "Googlebot";

        int countString = 0;
        int countBotYandex = 0;
        int countBotGoogle = 0;

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                countString++;
                int length = line.length();

                if (length > CHECK_MAX_VALUE) {
                    throw new MaxLengthStringException(CHECK_MAX_VALUE, length);
                }
                if (line.charAt(line.length() - 1) != '"') {
                    continue;
                }
                LogEntry logEntry = new LogEntry(line);
                statistics.addEntry(logEntry);

                int indexStart = line.indexOf('(');
                int indexFinish = line.indexOf(')');
                if (indexStart == -1 || indexFinish == -1 || indexStart > indexFinish) {
                    continue;
                }

                String firstBrackets = line.substring(indexStart, indexFinish);
                String[] parts = firstBrackets.split(";");

                if (parts.length >= 2) {
                    for (int i = 0; i < parts.length - 1; i++) {
                        parts[i] = parts[i].strip();
                    }

                    int indexForwardSlash = parts[1].indexOf('/');
                    if (indexForwardSlash == -1) {
                        continue;
                    }
                    String fragment = parts[1].substring(0, indexForwardSlash);

                    if (fragment.equals(botYandex)) {
                        countBotYandex++;
                    } else if (fragment.equals(botGoogle)) {
                        countBotGoogle++;
                    }
                }
            }

            double percentBotYandex = (double) countBotYandex / countString * 100;
            double percentBotGoogle = (double) countBotGoogle / countString * 100;

            System.out.println("Число строк : " + countString);
            System.out.println("Доля запросов от YandexBot = " + percentBotYandex);
            System.out.println("Доля запросов от Googlebot = " + percentBotGoogle);
            System.out.println("Объем часового трафика: " + statistics.getTrafficRate() / 1024 + " КБ");
            System.out.println("-------------------------------------------------------");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

