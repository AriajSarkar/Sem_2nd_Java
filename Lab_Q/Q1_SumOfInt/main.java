// WAP to find sum off all intergers
// Greater than 100
// Less than 200
// that are
// Divisible by 7

// Logic -
/*
    Logic Explanation:
    1. Initialize sum = 0.
    2. Iterate i from 100 to 200 inclusive.
    3. If i is divisible by 7 (i % 7 == 0), add i to sum.
    4. Print the final sum.
 */

// import java.util.Scanner
public class main {

    public static void main(String args[]) {
        // Scanner scanner = new Scanner(System.in)
        // System.out.println("Enter Lower Bound: ");
        // int lower = scanner.nextInt();
        // System.out.println("Enter Higher Bound: ");
        // int higher = scanner.nextInt();

        int sum = 0;
        for (int i = 100; i <= 200; i++) {
            if (i % 7 == 0) {
                sum += i;
            }
        }
        System.out.println("Sum of intergers Between 100 to 200 is: " + sum);
    }
}

// Output:
// Sum of integers between 100 and 200 that are divisible by 7: 2107
// Explanation:
// The numbers between 100 and 200 that are divisible by 7 are 105, 112, 119, 126, 133, 140, 147, 154, 161, 168, 175, 182, 189, and 196.
// The sum of these numbers is 2107.
// Hence, the output is 2107.
//
// Time Complexity: O(n) for iterating from 100 to 200, but since the range is fixed, it is effectively O(1).
// Space Complexity: O(1), using a constant amount of space.
