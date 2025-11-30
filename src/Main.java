import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int countFiles = 0;
        while (true) {
            String path = new Scanner(System.in).nextLine();
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
    }
}

