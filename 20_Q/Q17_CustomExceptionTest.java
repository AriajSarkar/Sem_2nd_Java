class MyException extends Exception {
    private String detail;
    
    public MyException(String message) {
        super(message);
        this.detail = message;
    }
    
    public void printInfo() {
        System.out.println("Custom Exception Detail: " + detail);
    }
}

public class Q17_CustomExceptionTest {
    public static void main(String[] args) {
        try {
            throw new MyException("This is a custom exception");
        } catch (MyException e) {
            System.out.println("Caught exception: " + e.getMessage());
            e.printInfo();
        }
    }
}
