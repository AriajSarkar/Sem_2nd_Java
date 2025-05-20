// 4. To learn use of ".length" in case of a two dimensional array

import java.util.Scanner;

class MatrixInfo {

    int[][] matrix;

    void inputMatrix() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();

        matrix = new int[rows][cols];

        System.out.println("Enter elements of the matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print("[" + i + "][" + j + "]: ");
                matrix[i][j] = sc.nextInt();
            }
        }
    }

    void displayLengthInfo() {
        System.out.println("Number of rows: " + matrix.length);
        System.out.println("Number of columns: " + matrix[0].length);
    }

    void displayMatrix() {
        System.out.println("Matrix elements:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class TwoDArrayLength {

    public static void main(String[] args) {
        MatrixInfo mi = new MatrixInfo();
        mi.inputMatrix();
        mi.displayLengthInfo();
        mi.displayMatrix();
    }
}
