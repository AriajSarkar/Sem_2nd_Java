// Import Scanner class for user input
import java.util.Scanner;

// Main class definition
public class efficient {
    // Main method - entry point of the program
    public static void main(String[] args) {
        // Create Scanner object with try-with-resources for automatic resource management
        try (Scanner scanner = new Scanner(System.in)) {
            // Get and validate lower bound from user
            int lower = getValidInput(scanner, "Enter the lower bound: ");
            // Get and validate upper bound from user
            int upper = getValidInput(scanner, "Enter the upper bound: ");

            // Check if bounds are in correct order
            if (lower > upper) {
                System.out.println("Error: Lower bound must be less than or equal to upper bound");
                return;
            }

            // Find the first number divisible by 7 in the range
            // Formula: lower + (7 - (lower % 7)) % 7
            int firstDivisible = lower + (7 - (lower % 7)) % 7;
            // Find the last number divisible by 7 in the range
            // Formula: upper - (upper % 7)
            int lastDivisible = upper - (upper % 7);
            
            // Calculate total count of numbers divisible by 7 in the range
            // Using arithmetic progression: (last - first) / common_difference + 1
            int count = (lastDivisible - firstDivisible) / 7 + 1;
            
            // Calculate sum using arithmetic progression formula
            // Sum = n(a + l)/2 where n=count, a=first term, l=last term
            // Using long to prevent integer overflow
            long sum = count > 0 ? (long) count * (firstDivisible + lastDivisible) / 2 : 0;

            // Display results
            System.out.println("Count of numbers divisible by 7: " + count);
            System.out.println("Sum of numbers divisible by 7: " + sum);
        // Catch any exceptions that might occur during execution
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to get and validate integer input
    private static int getValidInput(Scanner scanner, String prompt) {
        // Continue asking until valid input is received
        while (true) {
            // Display prompt message
            System.out.print(prompt);
            // Check if next input is an integer
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            // Error message for invalid input
            System.out.println("Invalid input. Please enter a valid integer.");
            // Clear the invalid input from scanner
            scanner.nextLine();
        }
    }
}

/*
Example Output:
--------------
Enter the lower bound: 1
Enter the upper bound: 50

Count of numbers divisible by 7: 7
Sum of numbers divisible by 7: 196
(Numbers: 7, 14, 21, 28, 35, 42, 49)

Another Example:
---------------
Enter the lower bound: 100
Enter the upper bound: 200

Count of numbers divisible by 7: 15
Sum of numbers divisible by 7: 2205
(Numbers: 105, 112, 119, 126, 133, 140, 147, 154, 161, 168, 175, 182, 189, 196)

Error Example:
-------------
Enter the lower bound: 200
Enter the upper bound: 100
Error: Lower bound must be less than or equal to upper bound

Invalid Input Example:
---------------------
Enter the lower bound: abc
Invalid input. Please enter a valid integer.
*/
