/*
 * Q7. Write a program to print alphabets in reverse order.
 */
public class main {

    public static void main(String[] args) {
        System.out.println("Alphabets in Reverse Order:");

        for (char ch = 'Z'; ch >= 'A'; ch--) {
            System.out.print(ch + " ");
        }

        System.out.println(); // New line

        for (char ch = 'z'; ch >= 'a'; ch--) {
            System.out.print(ch + " ");
        }
    }
}
