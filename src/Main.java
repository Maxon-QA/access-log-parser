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
        int countString = 0;
        int maxLengthString = 0;
        int minLengthString = Integer.MAX_VALUE;
        final int CHECK_MAX_VALUE = 1024;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                int length = line.length();

                if (length > CHECK_MAX_VALUE) {
                    throw new MaxLengthStringException(CHECK_MAX_VALUE, length);
                }

                if (length > maxLengthString) {
                    maxLengthString = length;
                }
                if (length < minLengthString) {
                    minLengthString = length;
                }
                countString++;
            }

            System.out.println("Число строк : " + countString);
            if (countString != 0) {
                System.out.println("Длина самой длинной строки в файле : " + maxLengthString);
                System.out.println("Длина самой короткой строки в файле : " + minLengthString);
            }
            System.out.println("-------------------------------------------------------");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

