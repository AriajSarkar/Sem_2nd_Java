
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a decimal number: ");
        int decimal = scanner.nextInt();

        String binary = "";
        while (decimal > 0) {
            binary = (decimal % 2) + binary; // Get remainder and add to front
            decimal /= 2; // Divide by 2
        }

        System.out.println("Binary representation: " + binary);

        scanner.close();
    }
}
