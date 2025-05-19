import java.util.Scanner;

public class Q14_Day9_Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        StringBuilder reversed = new StringBuilder(input).reverse();
        String reversedStr = reversed.toString();
        
        if (input.equals(reversedStr)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not palindrome");
        }
        
        scanner.close();
    }
}
