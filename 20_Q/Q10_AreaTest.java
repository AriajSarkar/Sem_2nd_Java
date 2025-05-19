abstract class Shape {
    abstract double rectangleArea(double length, double breadth);
    abstract double squareArea(double side);
    abstract double circleArea(double radius);
}

class AreaCalc extends Shape {
    @Override
    double rectangleArea(double length, double breadth) {
        return length * breadth;
    }
    
    @Override
    double squareArea(double side) {
        return side * side;
    }
    
    @Override
    double circleArea(double radius) {
        return Math.PI * radius * radius;
    }
}

public class Q10_AreaTest {
    public static void main(String[] args) {
        AreaCalc calculator = new AreaCalc();
        
        double rectArea = calculator.rectangleArea(5, 4);
        double squareArea = calculator.squareArea(4);
        double circleArea = calculator.circleArea(3);
        
        System.out.println("Rectangle Area: " + rectArea);
        System.out.println("Square Area: " + squareArea);
        System.out.println("Circle Area: " + circleArea);
    }
}
