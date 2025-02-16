import java.util.Scanner;

public class efficient {
    // Define pattern types
    private static final int REGULAR_BINARY = 1;
    private static final int MIRROR_BINARY = 2;
    private static final int REVERSE_BINARY = 3;
    private static final int CENTERED_BINARY = 4;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMenu();
                int choice = getMenuChoice(scanner);
                if (choice == 0) break;
                
                int rows = getValidInput(scanner);
                displayStatistics(rows);
                
                switch (choice) {
                    case REGULAR_BINARY -> printBinaryTriangle(rows, false, false);
                    case MIRROR_BINARY -> printBinaryTriangle(rows, true, false);
                    case REVERSE_BINARY -> printBinaryTriangle(rows, false, true);
                    case CENTERED_BINARY -> printCenteredBinaryTriangle(rows);
                }
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Binary Floyd's Triangle Patterns ===");
        System.out.println("1. Regular Binary Triangle");
        System.out.println("2. Mirror Binary Triangle");
        System.out.println("3. Reverse Binary Triangle");
        System.out.println("4. Centered Binary Triangle");
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
            System.out.print("Enter number of rows (1-50): ");
            if (scanner.hasNextInt()) {
                int rows = scanner.nextInt();
                if (rows > 0 && rows <= 50) return rows;
                System.out.println("Please enter a number between 1 and 50.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static void displayStatistics(int rows) {
        long totalNumbers = (long) rows * (rows + 1) / 2;
        
        System.out.println("\nBinary Triangle Statistics:");
        System.out.println("Number of rows: " + rows);
        System.out.println("Total binary digits: " + totalNumbers);
        System.out.println("Memory usage: " + (totalNumbers) + " bits\n");
    }

    private static void printBinaryTriangle(int rows, boolean mirror, boolean reverse) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = reverse ? rows : 1; reverse ? i >= 1 : i <= rows; i += reverse ? -1 : 1) {
            // Add padding for mirror pattern
            if (mirror) {
                sb.append(" ".repeat(rows - i));
            }
            
            // Print binary values
            for (int j = 0; j < i; j++) {
                sb.append((i + j) % 2).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void printCenteredBinaryTriangle(int rows) {
        StringBuilder sb = new StringBuilder();
        int lastRowWidth = rows * 2 - 1;
        
        for (int i = 1; i <= rows; i++) {
            int currentRowWidth = i * 2 - 1;
            int padding = (lastRowWidth - currentRowWidth) / 2;
            sb.append(" ".repeat(padding));
            
            for (int j = 0; j < i; j++) {
                sb.append((i + j) % 2).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
}
