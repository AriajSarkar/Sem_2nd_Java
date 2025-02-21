/*
 * Q3. WAP to input a number and check if it is prime. 
 * If it is prime, then compute the sum of digits of the number.
 */
import java.util.Scanner;

public class main {

    // Function to check if a number is prime
    public static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Function to compute the sum of digits of a number
    public static int sumOfDigits(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        if (isPrime(number)) {
            int sum = sumOfDigits(number);
            System.out.println("Sum of digits: " + sum);
        } else {
            System.out.println("The number is not prime.");
        }

        scanner.close();
    }
}
