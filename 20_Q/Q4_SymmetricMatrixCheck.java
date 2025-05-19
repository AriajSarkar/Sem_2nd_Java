import java.util.Scanner;

class MatrixSymmetry {
    private int n;
    private int[][] matrix;
    
    void inputMatrix() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter matrix size (n): ");
        n = scanner.nextInt();
        
        matrix = new int[n][n];
        System.out.println("Enter matrix elements:");
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }
    
    void displayMatrix() {
        System.out.println("Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    boolean isSymmetric() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
}

public class Q4_SymmetricMatrixCheck {
    public static void main(String[] args) {
        MatrixSymmetry matrix = new MatrixSymmetry();
        matrix.inputMatrix();
        matrix.displayMatrix();
        
        if (matrix.isSymmetric()) {
            System.out.println("Matrix is symmetric");
        } else {
            System.out.println("Matrix is not symmetric");
        }
    }
}
