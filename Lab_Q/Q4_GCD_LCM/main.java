/*
 * Q4. Write a program to find the GCD and LCM of two numbers.
 */
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input two numbers
        System.out.print("Enter first number: ");
        int num1 = scanner.nextInt();
        System.out.print("Enter second number: ");
        int num2 = scanner.nextInt();

        // Compute GCD
        int gcd = findGCD(num1, num2);

        // Compute LCM using formula
        int lcm = (num1 * num2) / gcd;

        // Display results
        System.out.println("GCD: " + gcd);
        System.out.println("LCM: " + lcm);

        scanner.close();
    }

    // Function to find GCD using Euclidean Algorithm
    private static int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
