// 3. To learn use of single dimensional array by defining the array dynamically.
import java.util.Scanner;

class ArrayManager {

    int[] arr;

    void inputArray() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        arr = new int[n];

        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
    }

    void displayArray() {
        System.out.println("Array elements:");
        for (int i = 0; i < arr.length; i++) {
            System.out.println("Index " + i + ": " + arr[i]);
        }
    }
}

public class ArrayDynamicInput {
    public static void main(String[] args) {
        ArrayManager am = new ArrayManager();
        am.inputArray();
        am.displayArray();
    }
}
