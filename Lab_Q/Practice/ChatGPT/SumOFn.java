//ChatGPT Test 3
import java.util.Scanner;


public class SumOFn {
    public static void main(String[] args) {
        int sum = 0;

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n'th num: ");
        int num = sc.nextInt();

        for (int i = 1; i <= num; i++) {
            sum += i;
        }
            System.out.println("Sum: " + sum);
    }
}