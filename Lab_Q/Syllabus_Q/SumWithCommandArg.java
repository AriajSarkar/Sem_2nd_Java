
// 1. Find the sum of any number of integers entered as command line arguments.

public class SumWithCommandArg {
    public static void main(String[] args) {
        double sum = 0;
        for (String arg : args) {
            sum += Double.parseDouble(arg);
        }
        System.out.println("The sum of the entered numbers is: " + sum);
    }
}