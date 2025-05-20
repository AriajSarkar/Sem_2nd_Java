import java.util.Scanner;

class PrimeList {
    int limit;
    
    PrimeList(int n) {
        this.limit = n;
    }
    
    void displayPrimes() {
        for (int i = 2; i <= limit; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.print(i + " ");
            }
        }
    }
}

public class Q1_PrimeInRange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter upper limit: ");
        int n = scanner.nextInt();
        
        PrimeList primeList = new PrimeList(n);
        primeList.displayPrimes();
        
    }
}
