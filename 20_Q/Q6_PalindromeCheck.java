import java.util.Scanner;

public class Q6_PalindromeCheck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();k
        
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
