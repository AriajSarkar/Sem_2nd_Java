// 2. To find the factorial of a given number 
import java.util.Scanner;

class FindFactorial {
    int number;

    FindFactorial(int n) {
        this.number = n;
    }

    int calculateFactorial() {
        if (number == 0 || number == 1) {
            return 1;
        } else {
            int fact = 1;
            for (int i = 2; i <= number; i++) {
                fact *= i;
            }
            return fact;
        }
    }
}

public class Factorial {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number to find its factorial: ");
        int n = sc.nextInt();

        FindFactorial factorial = new FindFactorial(n);
        int result = factorial.calculateFactorial();

        System.out.println("The factorial of " + n + " is: " + result);

    }
}