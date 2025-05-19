class TriangleException extends IllegalArgumentException {
    public TriangleException(String message) {
        super(message);
    }
}

public class Q16_TriangleCheck {
    static void triangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new TriangleException("All sides must be positive");
        }
        if (a + b <= c || b + c <= a || a + c <= b) {
            throw new TriangleException("Triangle inequality violated");
        }
        System.out.println("Valid triangle with sides: " + a + ", " + b + ", " + c);
    }
    
    public static void main(String[] args) {
        try {
            triangle(3, 4, 5);
            triangle(1, 2, 4); // This should throw an exception
        } catch (TriangleException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
