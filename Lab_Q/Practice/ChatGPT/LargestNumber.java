// ChatGPT Test 5 - With Menu Using Switch Case

import java.util.Scanner;

public class LargestNumber {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Classic input (3 prompts)");
        System.out.println("2. One-line input (e.g., 10 20 30)");
        System.out.print("Enter your choice (1 or 2): ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> classicInput();
            case 2 -> oneLineInput();
            default -> System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    static void classicInput() {
        Scanner sc = new Scanner(System.in);
        int num1, num2, num3;

        System.out.print("Enter 1 Num: ");
        num1 = sc.nextInt();

        System.out.print("Enter 2 Num: ");
        num2 = sc.nextInt();

        System.out.print("Enter 3 Num: ");
        num3 = sc.nextInt();

        if (num1 >= num2 && num1 >= num3) {
            System.out.println("Largest (classic input): " + num1);
        } else if (num2 >= num1 && num2 >= num3) {
            System.out.println("Largest (classic input): " + num2);
        } else {
            System.out.println("Largest (classic input): " + num3);
        }
    }

    static void oneLineInput() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 3 numbers (e.g., 10 20 30): ");
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();
        int num3 = sc.nextInt();

        if (num1 >= num2 && num1 >= num3) {
            System.out.println("Largest (one-line input): " + num1);
        } else if (num2 >= num1 && num2 >= num3) {
            System.out.println("Largest (one-line input): " + num2);
        } else {
            System.out.println("Largest (one-line input): " + num3);
        }
    }
}
