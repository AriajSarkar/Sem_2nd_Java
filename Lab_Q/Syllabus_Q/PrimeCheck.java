
import java.util.Scanner;

// 6. To check if a number is prime or not, by taking the number as input from the keyboard

class PrimeChecker {

    void checkPrime() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = sc.nextInt();

        boolean isPrime = true;
        if (number <= 1) {
            isPrime = false;
        } else {
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        
        if (isPrime) {
            System.out.println(number + " is a prime number.");
        } else {
            System.out.println(number + " is not a prime number.");
        }
    }
}

public class PrimeCheck {

    public static void main(String[] args) {
        PrimeChecker pc = new PrimeChecker();
        pc.checkPrime();
    }
}
