abstract class Bank {
    abstract double getBalance();
}

class BankA extends Bank {
    @Override
    double getBalance() {
        return 100;
    }
}

class BankB extends Bank {
    @Override
    double getBalance() {
        return 150;
    }
}

class BankC extends Bank {
    @Override
    double getBalance() {
        return 200;
    }
}

public class Q9_BankTest {
    public static void main(String[] args) {
        Bank bankA = new BankA();
        Bank bankB = new BankB();
        Bank bankC = new BankC();
        
        System.out.println("Balance in Bank A: $" + bankA.getBalance());
        System.out.println("Balance in Bank B: $" + bankB.getBalance());
        System.out.println("Balance in Bank C: $" + bankC.getBalance());
    }
}
