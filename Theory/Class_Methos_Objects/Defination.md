# Java Classes and Objects

## Class Example from Lectures
Here's a simple example demonstrating class concepts:

```java
class A {
    // Class attributes
    int a, b;
    
    // Method to add numbers
    int add() {
        return (a + b);
    }
}

class TestA {
    public static void main(String args[]) {
        // Creating object and using it
        A ob = new A();
        ob.a = 100;
        ob.b = 200;
        System.out.println(ob.add());
    }
}
```
##### Output
```output
300
```

## What is a Class?
A class is a blueprint or template for creating objects. It defines the properties (attributes) and behaviors (methods) that objects of that type will have.

### How to Define a Class
```java
public class Car {
    // Properties (attributes)
    private String brand;
    private String model;
    private int year;

    // Constructor
    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
}
```

## What are Objects?
Objects are instances of a class. They represent real-world entities and contain both state (properties) and behavior (methods).

### How to Create Objects
```java
// Creating objects using the constructor
Car myCar = new Car("Toyota", "Camry", 2023);
Car anotherCar = new Car("Honda", "Civic", 2022);
```

## Adding Methods to a Class
Methods define the behavior of objects. They can access and modify object properties.

```java
public class Car {
    // ...existing properties and constructor...

    // Methods
    public void startEngine() {
        System.out.println("The " + brand + " " + model + " engine is starting...");
    }

    public String getCarInfo() {
        return year + " " + brand + " " + model;
    }

    // Getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
```

## Class Extension and Reuse
Java supports inheritance, allowing classes to extend and reuse functionality from other classes.

```java
// Parent class
public class Vehicle {
    protected String type;
    
    public Vehicle(String type) {
        this.type = type;
    }
    
    public void move() {
        System.out.println("Vehicle is moving");
    }
}

// Child class extending Vehicle
public class ElectricCar extends Vehicle {
    private int batteryCapacity;
    
    public ElectricCar(String type, int batteryCapacity) {
        super(type);
        this.batteryCapacity = batteryCapacity;
    }
    
    @Override
    public void move() {
        System.out.println("Electric car moving silently");
    }
}
```

### Key Points About Class Extension:
- Use the `extends` keyword to inherit from another class
- `super()` calls the parent class constructor
- `@Override` annotation indicates method overriding
- Child classes can add new properties and methods
- Child classes can access parent's protected members

### Example Usage:
```java
ElectricCar tesla = new ElectricCar("Sedan", 100);
tesla.move(); // Output: Electric car moving silently
```