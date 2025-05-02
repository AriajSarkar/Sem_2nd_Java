abstract class Shape {
    abstract double  area();
}

class Circle extends Shape {
    double radius = 5.0;

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length = 10.0;
    double width = 4.0;

    @Override
    double area() {
        return length * width;
    }
}

public class ShapeTest {
    public static void main(String[] args) {
        Shape c = new Circle();
        Shape r = new Rectangle();
        
        System.out.println("Circle area: " + c.area());
        System.out.println("Rectangle area: " + r.area());
    }
}