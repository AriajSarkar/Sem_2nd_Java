/*
 * Logic of Binary Floyd's Triangle:
 * 1. Print alternating 0s and 1s in triangle pattern
 * 2. Sum of row number and column number determines if 0 or 1 is printed
 * 3. If (row + col) is even, print 0; if odd, print 1
 * 4. Each row has number of elements equal to row number
 */
public class main {
    public static void main(String args[]) {
        // Initialize starting number
        int num = 1;
        
        // Outer loop - iterate through 5 rows
        for(int i = 1; i <= 5; i++) {
            
            // Inner loop - print elements in current row
            for(int j = 0; j < i; j++) {
                // Calculate binary value using modulus:
                // (i+j)%2 gives 0 or 1 based on whether sum is even or odd
                System.out.print((i+j)%2 + " ");
                num++;
            }
            
            // Move to next line after each row
            System.out.println();
        }
    }
}

/*
 * Output of the program:
 * 1 
 * 0 1 
 * 1 0 1 
 * 0 1 0 1 
 * 1 0 1 0 1 
 */
