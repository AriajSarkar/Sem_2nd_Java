import java.util.Scanner;

public class efficient {
    // Define pattern types
    private static final int REGULAR_FLOYD = 1;
    private static final int MIRROR_FLOYD = 2;
    private static final int REVERSE_FLOYD = 3;
    private static final int CENTERED_FLOYD = 4;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMenu();
                int choice = getMenuChoice(scanner);
                if (choice == 0) break;
                
                int rows = getValidInput(scanner);
                displayStatistics(rows);
                
                switch (choice) {
                    case REGULAR_FLOYD -> printFloydTriangle(rows, false, false);
                    case MIRROR_FLOYD -> printFloydTriangle(rows, true, false);
                    case REVERSE_FLOYD -> printFloydTriangle(rows, false, true);
                    case CENTERED_FLOYD -> printCenteredFloydTriangle(rows);
                }
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Floyd's Triangle Patterns ===");
        System.out.println("1. Regular Floyd's Triangle");
        System.out.println("2. Mirror Floyd's Triangle");
        System.out.println("3. Reverse Floyd's Triangle");
        System.out.println("4. Centered Floyd's Triangle");
        System.out.println("0. Exit");
        System.out.print("\nSelect pattern type: ");
    }

    private static int getMenuChoice(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 0 && choice <= 4) return choice;
            }
            System.out.println("Invalid choice. Please select 0-4.");
            scanner.nextLine();
        }
    }

    private static int getValidInput(Scanner scanner) {
        while (true) {
            System.out.print("Enter number of rows (1-100): ");
            if (scanner.hasNextInt()) {
                int rows = scanner.nextInt();
                if (rows > 0 && rows <= 100) {
                    return rows;
                }
                System.out.println("Please enter a number between 1 and 100.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static void displayStatistics(int rows) {
        // Calculate total numbers in the triangle using arithmetic progression
        long totalNumbers = (long) rows * (rows + 1) / 2;
        // Calculate the last number that will be printed
        long lastNumber = totalNumbers;
        
        System.out.println("\nFloyd's Triangle Statistics:");
        System.out.println("Number of rows: " + rows);
        System.out.println("Total numbers: " + totalNumbers);
        System.out.println("Last number: " + lastNumber);
        System.out.println("Memory usage: " + (totalNumbers * 4) + " bytes\n");
    }

    private static void printFloydTriangle(int rows, boolean mirror, boolean reverse) {
        int maxNumber = (rows * (rows + 1)) / 2;
        int maxWidth = String.valueOf(maxNumber).length();
        int number = reverse ? maxNumber : 1;
        StringBuilder sb = new StringBuilder();
        String numberFormat = "%" + maxWidth + "d "; // Right-align format
        
        for (int i = 1; i <= rows; i++) {
            // Calculate left padding for alignment
            if (mirror) {
                int leftPad = (rows - i) * (maxWidth + 1);
                sb.append(" ".repeat(leftPad));
            }
            
            // Add numbers for this row
            for (int j = 1; j <= i; j++) {
                sb.append(String.format(numberFormat, number));
                number = reverse ? number - 1 : number + 1;
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void printCenteredFloydTriangle(int rows) {
        int maxNumber = (rows * (rows + 1)) / 2;
        int maxWidth = String.valueOf(maxNumber).length();
        int number = 1;
        StringBuilder sb = new StringBuilder();
        
        // Calculate the width needed for the last row (widest row)
        int lastRowWidth = rows * (maxWidth + 1) - 1;
        String numberFormat = "%" + maxWidth + "d"; // Right-align format
        
        for (int i = 1; i <= rows; i++) {
            // Calculate proper padding for perfect centering
            int currentRowWidth = i * (maxWidth + 1) - 1;
            int padding = (lastRowWidth - currentRowWidth) / 2;
            sb.append(" ".repeat(padding));
            
            // Add numbers for this row with consistent spacing
            for (int j = 1; j <= i; j++) {
                sb.append(String.format(numberFormat, number)).append(" ");
                number++;
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
}
