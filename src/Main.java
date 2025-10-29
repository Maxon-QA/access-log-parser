import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int firstNumber = new Scanner(System.in).nextInt();

        System.out.println("Введите второе число:");
        int secondNumber = new Scanner(System.in).nextInt();

        int sum = firstNumber + secondNumber;
        int sub = firstNumber - secondNumber;
        int mult = firstNumber * secondNumber;
        double div = (double)firstNumber / secondNumber;

        System.out.println("Сумма = " + sum);
        System.out.println("Разность = " + sub);
        System.out.println("Произведение = " + mult);
        System.out.println("Частное = " + div);
    }
}
