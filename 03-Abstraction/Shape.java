/**
 * Abstraction example:
 * Shape is abstract — defines what every shape must do,
 * without saying HOW.
 */
public abstract class Shape {

    public abstract double area();      // abstract method
    public abstract double perimeter(); // abstract method

    public void printInfo() {
        System.out.println("Area: " + area());
        System.out.println("Perimeter: " + perimeter());
    }
}