import java.util.Scanner;

public class Q6_PalindromeCheck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        StringBuilder sb = new StringBuilder(input).reverse();
        String reversedStr = sb.toString();

        if (input.equals(reversedStr)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not palindrome");
        }

    }
}
