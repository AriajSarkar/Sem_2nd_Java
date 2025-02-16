/*
Logic of Floyd's Triangle:
	1. Start with number 1
	2. Each row contains consecutive numbers
	3. Row 1 has 1 number, row 2 has 2 numbers, and so on
	4. Numbers are separated by spaces
	5. Each row starts on a new line
	6. Pattern continues until reaching specified number of rows (5)
*/

public class main {
    public static void main(String args[]) {
        // Initialize starting number
        int num = 1;
        
        // Outer loop - controls number of rows (1 to 5)
        for(int i = 1; i <= 5; i++) {
            
            // Inner loop - controls numbers in current row (1 to i)
            // i determines how many numbers to print in this row
            for(int j = 1; j <= i; j++) {
                // Print current number followed by space
                System.out.print(num + " ");
                // Increment number for next position
                num++;
            }
            
            // Move to next line after completing each row
            System.out.println();
        }
    }
}

/*
Output Explanation:
------------------
Row 1: 1              (1 number)
Row 2: 2 3            (2 numbers)
Row 3: 4 5 6          (3 numbers)
Row 4: 7 8 9 10       (4 numbers)
Row 5: 11 12 13 14 15 (5 numbers)

Pattern Analysis:
- Each row i contains i numbers
- Numbers are consecutive
- Total numbers printed = 15 (sum of 1+2+3+4+5)
- Each number is followed by exactly one space
- Each row ends with a newline

Example Output:
--------------
1 
2 3 
4 5 6 
7 8 9 10 
11 12 13 14 15
--------------
*/
