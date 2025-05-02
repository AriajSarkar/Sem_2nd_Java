// Parent Class

class Vehicle {

    void move() {
        System.out.println("Moving...");
    }
}

// Child Class
class Car extends Vehicle {

    void fuelType() {
        System.out.println("Diesel");
    }
}

// Main class
public class VehicleInfo {

    public static void main(String[] args) {
        Car car = new Car();

        car.move(); 
        car.fuelType();
    }
}
