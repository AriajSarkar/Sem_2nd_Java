/*
 * Q6. Write a program to print all perfect numbers between 1 and 1000.
 */
public class  main {

    public static void main(String[] args) {
        System.out.println("Perfect numbers under 1000:");

        for (int num = 1; num <= 1000; num++) {
            if (isPerfect(num)) {
                System.out.print(num + " ");
            }
        }
    }

    // Method to check if a number is perfect
    private static boolean isPerfect(int num) {
        int sum = 0;

        // Find divisors and add them
        for (int i = 1; i <= num / 2; i++) {
            if (num % i == 0) {
                sum += i;
            }
        }

        return sum == num;
    }
}
