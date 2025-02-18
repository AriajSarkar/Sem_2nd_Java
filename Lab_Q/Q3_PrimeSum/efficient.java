import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class efficient {
    // Optimized prime checking using square root and early returns
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        // Check only up to square root, incrementing by 6
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    // Optimized digit sum using character arithmetic
    private static int sumOfDigits(int n) {
        int sum = 0;
        String numStr = String.valueOf(n);
        for (char digit : numStr.toCharArray()) {
            sum += digit - '0';
        }
        return sum;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter a number: ");
            String input = reader.readLine().trim();
            
            // Input validation
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a positive integer.");
                return;
            }

            int number = Integer.parseInt(input);
            StringBuilder result = new StringBuilder();

            if (isPrime(number)) {
                int sum = sumOfDigits(number);
                result.append("Sum of digits: ").append(sum);
            } else {
                result.append("The number is not prime.");
            }

            System.out.println(result);
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format.");
        }
    }
}
