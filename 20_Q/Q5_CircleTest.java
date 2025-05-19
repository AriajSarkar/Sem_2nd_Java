class Circle {
    double radius;
    
    Circle() {
        radius = 1.0;
    }
    
    Circle(double r) {
        radius = r;
    }
    
    double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    double calculateCircumference() {
        return 2 * Math.PI * radius;
    }
    
    void displayDetails() {
        System.out.println("Circle with radius: " + radius);
    }
}

public class Q5_CircleTest {
    public static void main(String[] args) {
        Circle c1 = new Circle();
        Circle c2 = new Circle(5.0);
        
        System.out.println("Circle 1:");
        c1.displayDetails();
        System.out.println("Area: " + c1.calculateArea());
        System.out.println("Circumference: " + c1.calculateCircumference());
        
        System.out.println("\nCircle 2:");
        c2.displayDetails();
        System.out.println("Area: " + c2.calculateArea());
        System.out.println("Circumference: " + c2.calculateCircumference());
    }
}
