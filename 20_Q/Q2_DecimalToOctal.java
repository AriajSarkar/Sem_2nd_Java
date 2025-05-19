import java.util.Scanner;

public class Q2_DecimalToOctal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter decimal number: ");
        int decimal = scanner.nextInt();
        
        String octal = Integer.toOctalString(decimal);
        System.out.println("Octal representation: " + octal);
        
        scanner.close();
    }
}
