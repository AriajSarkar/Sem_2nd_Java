// ChatGPT Test 2

import java.util.Scanner;


public class Calculator {
    public static void main(String[] args) {

        double result = 0, num1, num2;
        char op;

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 1st num: ");
        num1 = sc.nextInt();

        System.out.print("Enter 2st num: ");
        num2 = sc.nextInt();

        System.out.print("Enter operator: ");
        op = sc.next().charAt(0);

        switch (op) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> result = num1 / num2;
            default -> throw new AssertionError();
        }
        
        System.out.println("Result: " + result);
    }
}