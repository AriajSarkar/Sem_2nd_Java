import java.util.Scanner;

class ArrayFlipper {
    private int size;
    private int[] array1;
    private int[] array2;
    
    void inputArrays() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter size of arrays: ");
        size = scanner.nextInt();
        
        array1 = new int[size];
        array2 = new int[size];
        
        System.out.println("Enter elements for first array:");
        for (int i = 0; i < size; i++) {
            array1[i] = scanner.nextInt();
        }
        
        System.out.println("Enter elements for second array:");
        for (int i = 0; i < size; i++) {
            array2[i] = scanner.nextInt();
        }
    }
    
    void flipEvenIndices() {
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {  // Even index
                int temp = array1[i];
                array1[i] = array2[i];
                array2[i] = temp;
            }
        }
    }
    
    void displayArrays() {
        System.out.print("First array: ");
        for (int i = 0; i < size; i++) {
            System.out.print(array1[i] + " ");
        }
        
        System.out.print("\nSecond array: ");
        for (int i = 0; i < size; i++) {
            System.out.print(array2[i] + " ");
        }
        System.out.println();
    }
}

public class Q3_ArrayFlipEvenOdd {
    public static void main(String[] args) {
        ArrayFlipper flipper = new ArrayFlipper();
        flipper.inputArrays();
        System.out.println("Before flipping:");
        flipper.displayArrays();
        
        flipper.flipEvenIndices();
        System.out.println("After flipping elements at even indices:");
        flipper.displayArrays();
    }
}
