
import java.util.Scanner;

public class StudentMarks {
    private final int[] marks = new int[5];

    void inputMarks() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the marks of 5 subjects:");
        for (int i = 0; i < marks.length; i++) {
            System.out.print("Mark " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }
    }

    void displayMarks() {

        System.out.println("\nDisplaying all marks:");

        for (int i = 0; i < marks.length; i++) {
            System.out.println("Subject " + (i + 1) + ": " + marks[i]);
        }
    }

    void getTotal() {
        int total = 0;
        for (int i = 0; i < marks.length; i++) {
            total += marks[i];
        }
        System.out.println("Total Marks: " + total);
    }

    double getAverage() {
        double total = 0;
        for (int i = 0; i < marks.length; i++) {
            total += marks[i];
        }
        return total / marks.length;
    }

    int getHighest() {
        int highest = marks[0];
        for (int i = 1; i < marks.length; i++) {
            if (marks[i] > highest) {
                highest = marks[i];
            }
        }
        return highest;
    }
}
