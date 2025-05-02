
// 1. Find the sum of any number of integers entered as command line arguments.

public class SumOFAnyNum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            sum += Integer.parseInt(arg);
        }
        System.out.println("The sum of the entered numbers is: " + sum);
    }
}